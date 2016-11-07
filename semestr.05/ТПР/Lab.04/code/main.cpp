#include <iostream>
#include <fstream>
#include <cmath>
#include <iomanip>

using namespace std;

double* mult(double **matrixA, double *matrixB, int n) {
    double *result = new double[n];
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            result[i] += matrixA[i][j] * matrixB[j];
        }
    }
    return result;
}

double* vectorW(int n, double **m, double &l, double &IS) {
    double *w = new double[n];
    double sum = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            w[i] += m[j][i];
        }
        w[i] = 1 / w[i];
        sum += w[i];
    }
    for (int i = 0; i < n; i++) {
        w[i] = w[i] / sum;
    }
    double *w1 = mult(m, w, n);
    double *w11 = new double[n];
    l = 0;
    for (int i = 0; i < n; i++) {
        w11[i] = w1[i] / w[i];
        l += w11[i] / n;
    }
    IS = (l - n) / (n - 1);
    return w;
}

void generate(int n) {
    double l = 0, IS = 1;
    double *w;
    double **a;
    IS = 1;
    while (IS > 0.1) {
        a = new double *[n];
        for (int i = 0; i < n; i++) {
            a[i] = new double[n];
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    a[i][j] = 1;
                } else if (j >= i) {
                    a[i][j] = (rand() % 100) / 10.0;
                } else {
                    a[i][j] = round((1.0 / a[j][i]) * 10) / 10.0;
                }
            }
        }
        w = vectorW(n, a, l, IS);
        if(IS != IS) {
            IS = 1;
        }
    }
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            cout << a[i][j] << " ";
        }
        cout << endl;
    }
    cout << "w = {";
    for (int i = 0; i < n; i++) {
        cout << w[i] << " ";
    }
    cout << "};" << endl;
    cout << l << " " << IS << endl;
    cout << endl;
}

double** read_matrix(ifstream &is, int n) {
    double **r = new double*[n];
    for(int i=0;i<n;i++) {
        r[i] = new double[n];
        for(int j=0;j<n;j++) {
            is >> r[i][j];
        }
    }
    return r;
}

void print_matrix(int n, double **m) {
    for(int i=0;i<n;i++) {
        for(int j=0;j<n;j++) {
            cout << setw(3) << m[i][j];
            if(j < n-1) {
                cout << " ";
            }
        }
        cout << endl;
    }
}

double* calc(int n, double **m) {
    print_matrix(n, m);
    double l, IS;
    double* w = vectorW(n, m, l, IS);
    cout << "\tВектор W:" << endl;
    for(int i=0;i<n;i++) {
        cout << setw(4) << setprecision(2) << w[i] << " ";
    }
    cout << endl;
    cout << "Lmax = " << setw(4) << setprecision(2) << l << endl;
    cout << "IS   = " << setw(4) << setprecision(2) << IS << endl;
    cout << endl;
    return w;
}

int main() {
    int n,m;
    ifstream is("input.txt");
    is >> n >> m;
    double **A1 = read_matrix(is, n);
    double **A21 = read_matrix(is, m);
    double **A22 = read_matrix(is, m);
    double **A23 = read_matrix(is, m);
    double **A24 = read_matrix(is, m);

    cout << "\tМатрица парных сравнений A1" << endl;
    double *w1 = calc(n, A1);

    double **w2 = new double*[4];
    cout << "\tМатрица парных сравнений A21" << endl;
    w2[0] = calc(m, A21);
    cout << "\tМатрица парных сравнений A22" << endl;
    w2[1] = calc(m, A22);
    cout << "\tМатрица парных сравнений A23" << endl;
    w2[2] = calc(m, A23);
    cout << "\tМатрица парных сравнений A24" << endl;
    w2[3] = calc(m, A24);

    double D_max = 0;
    int D_index = 0;
    for(int i=0;i<n;i++) {
        double D = 0;
        for(int j=0;j<4;j++) {
            D += w2[j][i] * w1[j];
        }
        if(D > D_max) {
            D_max = D;
            D_index = i + 1;
        }
        cout << "D" << (i + 1) << " = " << D << endl;
    }
    cout << "Оптимальное: " << D_index << endl;

    return 0;
}