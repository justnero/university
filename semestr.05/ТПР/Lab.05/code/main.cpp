#include <iostream>
#include <fstream>
#include <cmath>
#include <iomanip>

using namespace std;

int **read(int &n, int &m) {
    ifstream is("input.txt");
    is >> n >> m;
    int **matrix = new int *[n];
    for (int i = 0; i < n; i++) {
        matrix[i] = new int[m + 1];
        for (int j = 0; j < m; j++) {
            is >> matrix[i][j];
        }
    }
    return matrix;
}

void pareto(int n, int m, int **matrix) {
    int gt, lt, et;
    for (int i = 0; i < n; i++) {
        if (matrix[i][m] == 0) {
            continue;
        }
        for (int j = i + 1; j < n; j++) {
            if (matrix[j][m] == 0) {
                continue;
            }
            et = gt = lt = 0;
            for (int k = 0; k < m; k++) {
                if (matrix[i][k] == matrix[j][k]) {
                    et++;
                } else if (matrix[i][k] > matrix[j][k]) {
                    gt++;
                } else {
                    lt++;
                }
            }
            if (gt > 0 && lt == 0) {
                matrix[j][m] = 0;
            }
            if (gt == 0 && lt > 0) {
                matrix[i][m] = 0;
            }
        }
    }
}

int *utopia(int n, int m, int **matrix) {
    int *result = new int[m];
    int max;
    for (int i = 0; i < m; i++) {
        max = matrix[0][i];
        for (int j = 1; j < n; j++) {
            if (matrix[j][i] > max) {
                max = matrix[j][i];
            }
        }
        result[i] = max;
    }
    return result;

}

double *distance(int n, int m, int **matrix, int *utopia_point) {
    double *rx = new double[n];
    for (int i = 0; i < n; i++) {
        rx[i] = 0;
        if (matrix[i][m] == 0) {
            continue;
        }
        for (int j = 0; j < m; j++) {
            rx[i] += pow((utopia_point[j] - matrix[i][j]), 2);
        }
        rx[i] = sqrt(rx[i]);
    }
    return rx;
}

int effective(int n, int m, double *rx) {
    int result = 0;
    double min;
    min = 0;
    bool was_set = false;
    for (int i = 0; i < n; i++) {
        if (rx[i] == 0) {
            continue;
        }
        if (!was_set || min > rx[i]) {
            was_set = true;
            min = rx[i];
            result = i + 1;
        }
    }
    return result;
}

char *concat(char c, int d) {
    char *result = new char[4];
    sprintf(result, "%c%d", c, d);
    return result;
}

void print(int n, int m, int **matrix) {
    cout << setw(4) << "";
    for (int i = 0; i < m; i++) {
        cout << setw(3) << concat('f', i + 1) << "|";
    }
    cout << endl;
    for (int i = 0; i < n; i++) {
        cout << setw(3) << concat('x', i + 1) << ":";
        for (int j = 0; j < m; j++) {
            cout << setw(3) << matrix[i][j] << "|";
        }
        cout << endl;
    }
}

int main() {
    int n, m;
    int **matrix = read(n, m);
    cout << " Входные данные:" << endl;
    print(n, m, matrix);
    cout << endl;
    int cout_width = (int) ceil(log10(n)) + 1;

    pareto(n, m, matrix);
    cout << " Решения, находящиеся на Парето-границе" << endl;
    bool was_printed = false;
    for (int i = 0; i < n; i++) {
        if (matrix[i][m]) {
            if (was_printed) {
                cout << ", ";
            }
            was_printed = true;
            cout << setw(cout_width + 1) << concat('x', i + 1);
        }
    }
    if (!was_printed) {
        cout << "Отсутствуют" << endl;
        return 0;
    }
    cout << endl << endl;

    int *utopia_point = utopia(n, m, matrix);
    cout << " Точка утопии" << endl;
    cout << "{";
    for (int i = 0; i < m; i++) {
        cout << setw(cout_width) << utopia_point[i];
        if (i != m - 1) {
            cout << ", ";
        }
    }
    cout << "}" << endl;

    double *rx = distance(n, m, matrix, utopia_point);
    cout << " Расстояния до точки утопии" << endl;
    for (int i = 0; i < n; i++) {
        if (matrix[i][m]) {
            cout << "r[" << setw(cout_width + 1) << concat('x', i + 1) << "] = "
                 << setw(16) << setprecision(14) << rx[i] << endl;
        }
    }
    cout << endl;

    int effective_solution = effective(n, m, rx);
    cout << " Эффективное решение" << endl;
    cout << concat('x', effective_solution) << endl;

    return 0;
}