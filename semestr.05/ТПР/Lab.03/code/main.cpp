#include <iostream>
#include <fstream>
#include <iomanip>

#define EPS ((double) 1e-6)

using namespace std;

double abs(double a) {
    if(a < 0) {
        return -1*a;
    }
    return a;
}

bool eps_e(double a, double b) {
    return abs(a - b) <= EPS;
}

bool eps_g(double a, double b) {
    return (a - b) > EPS;
}

bool eps_l(double a, double b) {
    return (b - a) > EPS;
}

void recount(double **k, int n, int m) {
    for (int i = 0; i < n - 1; i++) {
        if (!k[i][0]) {
            continue;
        }
        for (int l = i + 1; l < n; l++) {
            if (!k[l][0]) {
                continue;
            }
            int cnt_e = 0;
            int cnt_g = 0;
            int cnt_l = 0;
            for (int j = 1; j <= m; j++) {
                if (eps_e(k[i][j], k[l][j])) {
                    cnt_e++;
                } else if (eps_g(k[i][j], k[l][j])) {
                    cnt_g++;
                } else {
                    cnt_l++;
                }
            }
            if (cnt_l == 0 && cnt_g != 0) {
                k[l][0] = 0;
            }
            if (cnt_g == 0 && cnt_l != 0) {
                k[i][0] = 0;
            }
        }
    }
}

void update(double **k, int n, int m, ifstream &is) {
    int l, a, b;
    double oa, ob;
    is >> l;
    for (int i = 0; i < l; i++) {
        is >> a >> oa >> b >> ob;
        if (oa < ob) {
            swap(a, b);
            swap(oa, ob);
        }
        double o = oa / (oa + ob);
        for (int j = 0; j < n; j++) {
            k[j][a] = o * k[j][b] + (1.0 - o) * k[j][a];
        }
    }
}

int main() {
    ifstream is("input.txt");
    int n, m;
    is >> n >> m;
    double **k = new double *[n];
    for (int i = 0; i < n; i++) {
        k[i] = new double[m + 1];
        k[i][0] = 1;
        for (int j = 1; j <= m; j++) {
            is >> k[i][j];
        }
    }

    recount(k, n, m);

    update(k, n, m, is);

    recount(k, n, m);

    for (int i = 0; i < n; i++) {
        for (int j = 1; j <= m; j++) {
            cout << setw(4) << setprecision(2) << k[i][j] << " ";
        }
        cout << endl;
    }
    cout << endl;
    
    for (int i = 0; i < n; i++) {
        if (k[i][0]) {
            cout << i + 1 << " ";
        }
    }
    cout << endl;

    return 0;
}