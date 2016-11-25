#include <mpi.h>
#include <iostream>
#include <fstream>

#define MESSAGE_LEN 1001

using namespace std;

MPI_Status status;
char *message;
int *fullMatrix;
int *skeletonMatrix;

int index(int i, int j, int n) {
    return i * n + j;
}

void init(int n) {
    ifstream f_in("full.txt");
    ifstream s_in("skeleton.txt");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            f_in >> fullMatrix[index(i, j, n)];
            s_in >> skeletonMatrix[index(i, j, n)];
        }
    }
    f_in.close();
    s_in.close();
}

void print(int *matrix, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cout << matrix[index(i, j, n)] << " ";
        }
        cout << endl;
    }
}

void master(int size, int rank) {
    init(size);

    MPI_Barrier(MPI_COMM_WORLD);
    MPI_Bcast(fullMatrix, size * size, MPI_INT, 0, MPI_COMM_WORLD);

    MPI_Barrier(MPI_COMM_WORLD);
    MPI_Bcast(skeletonMatrix, size * size, MPI_INT, 0, MPI_COMM_WORLD);

    cout << "Please input message: (max " << (MESSAGE_LEN - 1) / 2 << ")" << endl;
    message = new char[MESSAGE_LEN];
    cin.getline(message, MESSAGE_LEN);
    cout << endl;

    int sendCount = 0;
    for (int i = 0; i < size; i++) {
        if (skeletonMatrix[index(rank, i, size)] == 1) {
            MPI_Send(message, MESSAGE_LEN, MPI_CHAR, i, 0, MPI_COMM_WORLD);
            sendCount++;
        }
    }

    while (sendCount) {
        MPI_Recv(NULL, 0, MPI_INT, MPI_ANY_SOURCE, 1, MPI_COMM_WORLD, &status);
        sendCount--;
    }

    cout << endl << "Full matrix: " << endl;
    print(fullMatrix, size);
    cout << endl << "Skeleton matrix: " << endl;
    print(skeletonMatrix, size);
}

void slave(int size, int rank) {
    MPI_Barrier(MPI_COMM_WORLD);
    MPI_Bcast(fullMatrix, size * size, MPI_INT, 0, MPI_COMM_WORLD);

    MPI_Barrier(MPI_COMM_WORLD);
    MPI_Bcast(skeletonMatrix, size * size, MPI_INT, 0, MPI_COMM_WORLD);

    message = new char[MESSAGE_LEN];
    MPI_Recv(message, MESSAGE_LEN, MPI_CHAR, MPI_ANY_SOURCE, 0, MPI_COMM_WORLD, &status);
    cout << "r[" << status.MPI_SOURCE << "] -> r[" << rank << "]: '" << message << "'" << endl;

    int countSends = 0;
    for (int i = 0; i < size; i++) {
        if (skeletonMatrix[index(rank, i, size)] == 1) {
            MPI_Send(message, MESSAGE_LEN, MPI_CHAR, i, 0, MPI_COMM_WORLD);
            countSends++;
        }
    }

    for (; countSends; countSends--) {
        MPI_Recv(NULL, 0, MPI_INT, MPI_ANY_SOURCE, 1, MPI_COMM_WORLD, NULL);
    }
    MPI_Send(NULL, 0, MPI_INT, status.MPI_SOURCE, 1, MPI_COMM_WORLD);
}

int main(int argc, char **argv) {
    int size, rank;
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    fullMatrix = new int[size * size];
    skeletonMatrix = new int[size * size];
    !rank ? master(size, rank) : slave(size, rank);

    MPI_Finalize();
    return 0;
}