#include <iostream>

#define ARRAY_SIZE 10000
#define ITERATIONS_COUNT 100
#define DEBUG false

using namespace std;
using namespace chrono;

typedef int val_t;

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

void preSequential(void **arg) {
    val_t *array = *((val_t **) arg);
    for (int i = 0; i < ARRAY_SIZE; i++) {
        array[i] = rand();
    }
}

void postSequential(void **arg) {}

long long testSequential(long long *full_time) {
    val_t *array = (val_t *) malloc(sizeof(val_t) * ARRAY_SIZE);
    return timer(preSequential, shellSequential, postSequential, (void **) &array, ITERATIONS_COUNT, full_time);
}

long long testBubble(long long *full_time) {
    val_t *array = (val_t *) malloc(sizeof(val_t) * ARRAY_SIZE);
    return timer(preSequential, bubbleSequential, postSequential, (void **) &array, ITERATIONS_COUNT, full_time);
}

int main() {
    srand((unsigned int) time(0));
    long long ns = testSequential(NULL);
    cout << ns << "ns = " << ns / 1000 << "mcs = " << ns / 1000 / 1000 << "ms" << endl;
    ns = testBubble(NULL);
    cout << ns << "ns = " << ns / 1000 << "mcs = " << ns / 1000 / 1000 << "ms" << endl;

    return 0;
}