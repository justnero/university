#include <iostream>
#include <mpi.h>
#include <cmath>

#define ARRAY_SIZE 100
#define ITERATIONS_COUNT 100
#define DEBUG false

#define OP_CLOSE 0
#define OP_INIT 1
#define OP_SCATTER 2
#define OP_GATHER 3

#define VALUE_TYPE MPI_INTEGER

using namespace std;
using namespace chrono;

typedef int val_t;

int mpi_rank;
int mpi_size;
int *buf;
int *block;
int *block_sizes;
int block_size;
MPI_Comm hyperCube;
int hyperCubeDims;
int hyperCubeSize;

const int *list(int i, int dims);

void free_mem(void *ptr) {
    if (ptr != nullptr) {
        free(ptr);
    }
}

void *vmem(const size_t size) {
    return malloc(size);
}

int *imem(const int size) {
    return (int *) vmem(sizeof(int) * size);
}

int **ipmem(const int size) {
    return (int **) vmem(sizeof(int *) * size);
}

val_t *tmem(const int size) {
    return (val_t *) vmem(sizeof(val_t) * size);
}

long long
timer(void (*before)(void **), void (*timed_function)(void **), void (*after)(void **), void **arg, int iterations,
      long long *time_full) {
    long long time_only = 0;
    if (time_full != NULL) {
        (*time_full) = 0;
    }
    for (int i = 0; i < iterations; i++) {
        auto before_time = high_resolution_clock::now();
        before(arg);
        auto start_time = high_resolution_clock::now();
        timed_function(arg);
        auto end_time = high_resolution_clock::now();
        after(arg);
        auto after_time = high_resolution_clock::now();

        time_only += duration_cast<nanoseconds>(end_time - start_time).count();
#if DEBUG
        cout << i << ": " << duration_cast<milliseconds>(end_time - start_time).count() << "ms" << endl;
#endif
        if (time_full != NULL) {
            (*time_full) += duration_cast<nanoseconds>(after_time - before_time).count();
        }
    }

    return time_only;
}

void shellSequential(void **arg) {
    val_t *array = *((val_t **) arg);
    val_t t;
    for (unsigned k = ARRAY_SIZE / 2; k > 0; k /= 2) {
        for (unsigned i = k; i < ARRAY_SIZE; i += 1) {
            t = array[i];
            unsigned j;
            for (j = i; j >= k; j -= k) {
                if (t < array[j - k]) {
                    array[j] = array[j - k];
                } else {
                    break;
                }
            }
            array[j] = t;
        }
    }
}

void bubbleSequential(void **arg) {
    val_t *array = *((val_t **) arg);
    for (int i = 0; i < ARRAY_SIZE; i++) {
        for (int j = i + 1; j < ARRAY_SIZE; j++) {
            if (array[i] > array[j]) {
                swap(array[i], array[j]);
            }
        }
    }
}

void preGenerateArray(void **arg) {
    val_t *array = *((val_t **) arg);
    for (int i = 0; i < ARRAY_SIZE; i++) {
        array[i] = rand();
    }
}

void post(void **arg) {}

long long testSequentialShell(long long *full_time) {
    val_t *array = tmem(ARRAY_SIZE);
    long long time = timer(preGenerateArray, shellSequential, post, (void **) &array, ITERATIONS_COUNT, full_time);
    free(array);
    return time;
}

long long testSequentialBubble(long long *full_time) {
    val_t *array = tmem(ARRAY_SIZE);
    long long time = timer(preGenerateArray, bubbleSequential, post, (void **) &array, ITERATIONS_COUNT, full_time);
    free(array);
    return time;
}

long long testParallelShell(long long *full_time) {
    val_t *array = tmem(ARRAY_SIZE);
    long long time = timer(preGenerateArray, shellSequential, post, (void **) &array, ITERATIONS_COUNT, full_time);
    free(array);
    return time;
}

void op(int id) {
    MPI_Barrier(hyperCube);
    MPI_Bcast(&id, 1, MPI_INTEGER, 0, hyperCube);
}

void init() {
    cout << "INIT " << mpi_rank << endl;
    MPI_Cart_create(MPI_COMM_WORLD, hyperCubeDims, list(2, hyperCubeDims), list(0, hyperCubeDims), 0,
                    &hyperCube);

    cout << "Cart " << mpi_rank << endl;
    block_sizes = imem(hyperCubeSize);
    for (int i = 0, l = ARRAY_SIZE, step = (int) ceil(ARRAY_SIZE / hyperCubeSize);
         i < hyperCubeSize; l -= block_sizes[i++]) {
        block_sizes[i] = l >= step ? step : l;
    }
    block_size = block_sizes[mpi_rank];
    block = imem(block_size);
    cout << "INITED " << mpi_rank << endl;
}

int runMaster() {
    cout << "runMaster" << endl;
    long long ns;
    srand((unsigned int) time(0));

    ns = testSequentialShell(NULL);
    cout << ns << "ns = " << ns / 1000 << "mcs = " << ns / 1000 / 1000 << "ms" << endl;
    ns = testParallelShell(NULL);
    cout << ns << "ns = " << ns / 1000 << "mcs = " << ns / 1000 / 1000 << "ms" << endl;
    ns = testSequentialBubble(NULL);
    cout << ns << "ns = " << ns / 1000 << "mcs = " << ns / 1000 / 1000 << "ms" << endl;


    op(OP_SCATTER);
    buf = tmem(ARRAY_SIZE);
    preGenerateArray((void **) &buf);
    int tmp = ARRAY_SIZE;
    MPI_Scatterv(buf, &tmp, block_sizes, VALUE_TYPE, block, block_size, VALUE_TYPE, 0, hyperCube);
    cout << "Master =\t";
    for (int i = 0; i < block_size; i++) {
        cout << block[i] << "\t";
    }
    cout << endl;

    op(OP_CLOSE);
    return 0;
}

int runSlave() {
    cout << "runSlave" << endl;
    int op;
    MPI_Barrier(hyperCube);
    MPI_Bcast(&op, 1, MPI_INTEGER, 0, hyperCube);
    switch (op) {
        case OP_INIT:
            break;
        case OP_SCATTER:
            MPI_Scatterv(nullptr, 0, block_sizes, VALUE_TYPE, block, block_size, VALUE_TYPE, 0, hyperCube);
            cout << "Slave " << mpi_rank << " =\t";
            for (int i = 0; i < block_size; i++) {
                cout << block[i] << "\t";
            }
            cout << endl;
            break;
        case OP_CLOSE:
            return 0;
        default:
            return op;
    }
    return -1;
}

int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &mpi_size);
    MPI_Comm_rank(MPI_COMM_WORLD, &mpi_rank);

    int code = 0;
    hyperCubeDims = (int) floor(log2(mpi_size));
    hyperCubeSize = (int) pow(2, hyperCubeDims);
    if (mpi_rank < hyperCubeSize) {
        init();
        code = mpi_rank ? runSlave() : runMaster();
    }

    MPI_Finalize();
    return code;
}

const int *list(int i, int dims) {
    int *res = imem(dims);
    for (int k = 0; k < dims; res[k++] = i);
    return res;
}