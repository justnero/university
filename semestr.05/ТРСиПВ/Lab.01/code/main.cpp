#include <iostream>
#include <fstream>
#include <mpi.h>

using namespace std;

MPI_Status status;

void master_send(int **m_a, int **m_b, int n, int cor, int i, int j, int comm) {
    int buf[100];
    buf[0] = n;
    buf[1] = cor;
    for (int l = 0; l < n; l++) {
        buf[2 + l] = m_a[i][l];
    }
    for (int l = 0; l < n; l++) {
        buf[2 + n + l] = m_b[l][j];
    }
    MPI_Send(buf, 2 * n + 2, MPI_INT, comm, 1, MPI_COMM_WORLD);
}

void master() {
    int size;
    MPI_Comm_size(MPI_COMM_WORLD, &size);
    if (size == 1) {
        cout << "Can`t run without slaves! Just buy some..." << endl;
        return;
    }
    ifstream is("input.txt");

    int n, m, k;
    is >> n >> m >> k;

    int **m_a = new int *[n];
    int **m_b = new int *[m];
    for (int i = 0; i < n; i++) {
        m_a[i] = new int[m];
        for (int j = 0; j < m; j++) {
            is >> m_a[i][j];
        }
    }
    for (int i = 0; i < m; i++) {
        m_b[i] = new int[k];
        for (int j = 0; j < k; j++) {
            is >> m_b[i][j];
        }
    }
    is.close();


    bool used[size];
    memset(used, 0, sizeof(used));
    used[0] = true;
    long long m_c[n][k];
    int len = n * k;
    int j = 0;
    int online = 0;
    for (int i = 1; i < size && j < len; i++) {
        master_send(m_a, m_b, m, j, j / k, j % k, i);
        used[i] = true;
        online++;
        j++;
    }

    long message[2];
    while (j < len || online > 0) {
        MPI_Recv(message, 2, MPI_LONG_LONG, MPI_ANY_SOURCE, 1, MPI_COMM_WORLD, &status);
        m_c[message[0] / k][message[0] % k] = message[1];
        used[status.MPI_SOURCE] = false;
        online--;

        if (j < len) {
            master_send(m_a, m_b, m, j, j / k, j % k, status.MPI_SOURCE);
            used[status.MPI_SOURCE] = true;
            online++;
            j++;
        }
    }

    for (int i = 1; i < size; i++) {
        int buf[1];
        MPI_Send(buf, 0, MPI_LONG_LONG, i, 2, MPI_COMM_WORLD);
    }

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < k; j++) {
            cout << m_c[i][j] << " ";
        }
        cout << endl;
    }
}

void slave() {
    int message[100];
    bool running = true;
    while (running) {
        MPI_Recv(message, 100, MPI_INT, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
        if (status.MPI_TAG == 2) {
            running = false;
        } else {
            int n = message[0];
            long long result[2] = {message[1], 0};
            for (int i = 0; i < n; i++) {
                result[1] += message[2 + i] * message[2 + n + i];
            }
            MPI_Send(result, 2, MPI_LONG_LONG, 0, 1, MPI_COMM_WORLD);
        }
    }
}

int main(int argc, char **argv) {
    int rank;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    rank ? slave() : master();

    MPI_Barrier(MPI_COMM_WORLD);
    MPI_Finalize();
    return 0;
}