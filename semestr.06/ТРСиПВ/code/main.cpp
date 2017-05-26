#include <iostream>
#include <mpi.h>
#include <cmath>
#include <iomanip>

#define ITERATIONS_COUNT 1
#define DEBUG false

#define OP_CLOSE 0
#define OP_SHELL 1

#define ROOT 0

#define VALUE_TYPE MPI_INTEGER
typedef int val_t;

using namespace std;
using namespace chrono;

unsigned int array_size;

int mpi_rank;
int mpi_size;
val_t *block;
int block_size;

MPI_Comm hyperComm;
MPI_Comm hyperCube;
int hyperCubeDims;
int hyperCubeSize;

void op(int id);

void free_mem(void *ptr);

void *vmem(const size_t size);

int *imem(const int size);

val_t *tmem(const int size);

long long
timer(void (*before)(void **), void (*timed_function)(void **), void (*after)(void **), void **arg, int iterations,
      long long *time_full);

val_t *merge(int *v1, int n1, int *v2, int n2);

const int *list(int i, int dims);

const int *ilist(int dims);


void shellSort(val_t *array, const int size) {
    val_t t;
    for (unsigned k = (unsigned int) (size / 2); k > 0; k /= 2) {
        for (unsigned i = k; i < size; i += 1) {
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

void shellSequential(void **arg) {
    val_t *array = *((val_t **) arg);
    shellSort(array, array_size);
}

void shellParallelCommon() {
    MPI_Status status;
    int step = 1;
    val_t *other;
    while (step <= hyperCubeDims) {
        if (mpi_rank % (step * 2) == 0) {
            if (mpi_rank + step < hyperCubeSize) {
                int second_block_size;
                MPI_Recv(&second_block_size, 1, MPI_INT, mpi_rank + step, 0, hyperCube, &status);
                other = tmem(second_block_size);
                MPI_Recv(other, second_block_size, MPI_INT, mpi_rank + step, 0, hyperCube, &status);
                block = merge(block, block_size, other, second_block_size);
                block_size += second_block_size;
            }
        } else {
            int near = mpi_rank - step;
            MPI_Send(&block_size, 1, MPI_INT, near, 0, hyperCube);
            MPI_Send(block, block_size, MPI_INT, near, 0, hyperCube);
            break;
        }
        step *= 2;
    }
}

void shellParallelMaster(void **arg) {
    val_t *array = *((val_t **) arg);

    op(OP_SHELL);
    block_size = array_size / hyperCubeSize;

    MPI_Bcast(&block_size, 1, MPI_INT, ROOT, hyperCube);
    block = tmem(block_size);
    MPI_Scatter(array, block_size, VALUE_TYPE, block, block_size, VALUE_TYPE, ROOT, hyperCube);
    shellSort(block, block_size);

    shellParallelCommon();
}

void shellParallelSlave() {
    MPI_Bcast(&block_size, 1, MPI_INT, ROOT, hyperCube);
    block = tmem(block_size);
    MPI_Scatter(NULL, 0, VALUE_TYPE, block, block_size, VALUE_TYPE, ROOT, hyperCube);
    shellSort(block, block_size);

    shellParallelCommon();
}

void preGenerateArray(void **arg) {
    val_t *array = *((val_t **) arg);
    for (int i = 0; i < array_size; i++) {
        array[i] = rand();
    }
}

void post(void **arg) {
}

long long test(void (*timed_function)(void **), long long *full_time) {
    val_t *array = tmem(array_size);
    long long time = timer(preGenerateArray, timed_function, post, (void **) &array, ITERATIONS_COUNT, full_time);
    free_mem(array);
    return time;
}

void init() {
    MPI_Cart_create(hyperComm, hyperCubeDims, list(2, hyperCubeDims), list(0, hyperCubeDims), 0, &hyperCube);
}

int master() {
    long long sequential_ns, parallel_ns, sequential_full, parallel_full;
    srand((unsigned int) time(0));

    for (array_size = (unsigned int) 10000;
         array_size <= 1000000; array_size += 10000) {
        sequential_ns = test(shellSequential, &sequential_full);
        parallel_ns = test(shellParallelMaster, &parallel_full);
#if DEBUG
        cout << "Sequential" << "\t" << sequential_ns << "ns = " << sequential_ns / 1000 << "mcs = "
             << sequential_ns / 1000 / 1000 << "ms" << endl;
        cout << "Parallel  " << "\t" << parallel_ns << "ns = " << parallel_ns / 1000 << "mcs = "
             << parallel_ns / 1000 / 1000 << "ms" << endl;
#else
        cout << setw(6) << array_size << "\t" << setw(10) << sequential_ns << "\t" << setw(10) << parallel_ns
             << "\t" << setw(10) << ((parallel_ns - sequential_ns) / (sequential_ns * 1.0)) * 100 << "%"
             //             << "\t" << setw(10) << ((parallel_full - sequential_ns) / (sequential_ns * 1.0)) * 100 << "%"
             << endl;
#endif
    }
    op(OP_CLOSE);
    return 0;
}

int slave() {
    int op;
    while (true) {
        MPI_Barrier(hyperCube);
        MPI_Bcast(&op, 1, MPI_INTEGER, 0, hyperCube);
        switch (op) {
            case OP_SHELL:
                shellParallelSlave();
                break;
            case OP_CLOSE:
                return 0;
            default:
                return op;
        }
    }
}

int main(int argc, char **argv) {
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &mpi_size);
    MPI_Comm_rank(MPI_COMM_WORLD, &mpi_rank);

    array_size = (unsigned int) (mpi_size * 8);

    hyperCubeDims = (int) floor(log2(mpi_size));
    hyperCubeSize = (int) pow(2, hyperCubeDims);

    MPI_Group worldGroup, hyperGroup;
    MPI_Comm_group(MPI_COMM_WORLD, &worldGroup);
    MPI_Group_incl(worldGroup, hyperCubeSize, ilist(hyperCubeSize), &hyperGroup);
    MPI_Comm_create(MPI_COMM_WORLD, hyperGroup, &hyperComm);

    int code = 0;
    if (mpi_rank < hyperCubeSize) {
        init();
        code = mpi_rank ? slave() : master();
    }

    MPI_Finalize();
    return code;
}

void op(int id) {
    MPI_Barrier(hyperCube);
    MPI_Bcast(&id, 1, MPI_INTEGER, ROOT, hyperCube);
}

void free_mem(void *ptr) {
    if (ptr != NULL) {
        try {
            free(ptr);
        } catch (...) {

        }
    }
}

void *vmem(const size_t size) {
    return malloc(size);
}

int *imem(const int size) {
    return (int *) vmem(sizeof(int) * size);
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
        auto before_time = chrono::high_resolution_clock::now();
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

val_t *merge(int *v1, int n1, int *v2, int n2) {
    int i = 0, j = 0, k = 0,
            *result;

    result = tmem(n1 + n2);

    while (i < n1 && j < n2)
        if (v1[i] < v2[j]) {
            result[k++] = v1[i++];
        } else {
            result[k++] = v2[j++];
        }
    if (i == n1) {
        while (j < n2) {
            result[k++] = v2[j++];
        }
    } else {
        while (i < n1) {
            result[k++] = v1[i++];
        }
    }
    return result;
}

const int *list(int i, int dims) {
    int *res = imem(dims);
    for (int k = 0; k < dims; res[k++] = i);
    return res;
}

const int *ilist(int dims) {
    int *res = imem(dims);
    for (int k = 0; k < dims; res[k] = k, k++);
    return res;
}