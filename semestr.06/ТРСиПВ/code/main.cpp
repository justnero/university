#include <iostream>
#include <mpi.h>
#include <cmath>

#define ARRAY_SIZE 10000
#define ITERATIONS_COUNT 100
#define DEBUG false

#define OP_CLOSE 0
#define OP_SCATTER 1

using namespace std;
using namespace chrono;

typedef int val_t;

int rank;
int size;
int* buf;
MPI_Comm hyperCube;

const int *list(int i, int dims);

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
    val_t *array = (val_t *) malloc(sizeof(val_t) * ARRAY_SIZE);
    return timer(preGenerateArray, shellSequential, post, (void **) &array, ITERATIONS_COUNT, full_time);
}

long long testSequentialBubble(long long *full_time) {
    val_t *array = (val_t *) malloc(sizeof(val_t) * ARRAY_SIZE);
    return timer(preGenerateArray, bubbleSequential, post, (void **) &array, ITERATIONS_COUNT, full_time);
}

long long testParallelShell(long long *full_time) {
    val_t *array = (val_t *) malloc(sizeof(val_t) * ARRAY_SIZE);
    return timer(preGenerateArray, shellSequential, post, (void **) &array, ITERATIONS_COUNT, full_time);
}

int runMaster() {
    int op;
    long long ns;
    srand((unsigned int) time(0));

    ns = testSequentialShell(NULL);
    cout << ns << "ns = " << ns / 1000 << "mcs = " << ns / 1000 / 1000 << "ms" << endl;
    ns = testParallelShell(NULL);
    cout << ns << "ns = " << ns / 1000 << "mcs = " << ns / 1000 / 1000 << "ms" << endl;
    ns = testSequentialBubble(NULL);
    cout << ns << "ns = " << ns / 1000 << "mcs = " << ns / 1000 / 1000 << "ms" << endl;

    op = OP_CLOSE;
    MPI_Barrier(hyperCube);
    MPI_Bcast(&op, 1, MPI_INTEGER, 0, hyperCube);
    return 0;
}

int runSlave() {
    int op, tmp;
    MPI_Barrier(hyperCube);
    MPI_Bcast(&op, 1, MPI_INTEGER, 0, hyperCube);
    switch (op) {
        case OP_SCATTER:
            MPI_Bcast(&tmp, 1, MPI_INTEGER, 0, hyperCube);
            MPI_Scatterv()
            MPI_Scatter(nullptr, 0, MPI_INTEGER, buf, tmp, )
        case OP_CLOSE:
            return 0;
        default:
            return op;
    }
}

int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    int code = 0;
    int hyperCubeDims = (int) floor(log2(size));
    int hyperCubeSize = (int) pow(2, hyperCubeDims);
    if (rank < hyperCubeSize) {
        MPI_Cart_create(MPI_COMM_WORLD, hyperCubeDims, list(2, hyperCubeDims), list(0, hyperCubeDims), 0,
                        &hyperCube);
        code = rank ? runSlave() : runMaster();
    }

    MPI_Finalize();
    return code;
}

const int *list(int i, int dims) {
    int *res = (int *) malloc(sizeof(int) * dims);
    for (int k = 0; k < dims; res[k++] = i);
    return res;
}