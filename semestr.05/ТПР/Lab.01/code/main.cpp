#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

ifstream is("input.txt");
ofstream os("output.txt");
int k = 0;

void dot(bool **m, int n) {
    char *str = new char[64];
    sprintf(str, "graph-%d.dot", k);
    ofstream dotos(str);
    dotos << "digraph \"Ярус " << k << "\" {" << endl;
    bool isEmpty = true;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (m[i][j] && i != j) {
                dotos << "\t\"x" << i << "\"" << " -> " << "\"x" << j << "\"" << endl;
                isEmpty = false;
            }
        }
    }
    dotos << "}" << endl;
    dotos.close();

    if (!isEmpty) {
        sprintf(str, "dot graph-%d.dot -Tpng -O", k);
        system(str);
    }
    sprintf(str, "rm -f graph-%d.dot", k);
    system(str);
    k++;
}

void draw(bool **m, int n) {
    dot(m, n);
    os << "== MATRIX ==" << endl;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if ((i == j && m[i][j]) || (m[i][i] && m[j][j])) {
                os << "╋";
            } else if (m[i][i]) {
                os << "━";
            } else if (m[j][j]) {
                os << "┃";
            } else {
                if (m[i][j]) {
                    os << 1;
                } else {
                    os << 0;
                }
            }
        }
        os << endl;
    }
    os << "============" << endl;
}

void run_level_up(vector<int> &level, bool **m, int n) {
    level.clear();
    for (int i = 1; i <= n; i++) {
        if (m[i][i]) {
            continue;
        }
        bool isEmpty = true;
        for (int j = 1; j <= n && isEmpty; j++) {
            if (m[j][i]) {
                isEmpty = false;
            }
        }
        if (isEmpty) {
            level.push_back(i);
        }
    }
}

void run_level_down(vector<int> &level, bool **m, int n) {
    vector<int> tmp;
    vector<bool> isDests;
    for (int i = 1; i <= n; i++) {
        if (m[i][i]) {
            continue;
        }
        bool isEmpty = true;
        for (int j = 1; j <= n && isEmpty; j++) {
            if (m[i][j] && !m[i][i] && !m[j][j]) {
                isEmpty = false;
            }
        }
        if (isEmpty) {
            bool isDest = false;
            for (int j = 1; j <= n && !isDest; j++) {
                if (m[j][i]) {
                    isDest = true;
                }
            }
            isDests.push_back(isDest);
            tmp.push_back(i);
        }
    }
    bool onlyFalse = true;
    for(int i=0;i<isDests.size() && onlyFalse;i++) {
        if(isDests[i]) {
            onlyFalse = false;
        }
    }
    level.clear();
    for(int i=0;i<tmp.size();i++) {
        if(onlyFalse || isDests[i]) {
            level.push_back(tmp[i]);
        }
    }
}

int main() {

    char dir;
    int n, k;
    is >> n >> k >> dir;

    bool **m = new bool *[n + 1];
    for (int i = 0; i <= n; i++) {
        m[i] = new bool[n + 1];
    }
    memset(m, 0, sizeof(m));

    int a, b;
    for (int i = 0; i < k; i++) {
        is >> a >> b;
        m[a][b] = true;
    }
    os << "Initial" << endl;
    draw(m, n);

    void (*run_level)(vector<int> &level, bool **m, int n) = dir == 'u' ? &run_level_up : &run_level_down;

    bool isEnded = false;
    vector<int> level;
    int lvl = 1;
    while (!isEnded) {
        run_level(level, m, n);
        if (level.size()) {
            os << "Level " << lvl++ << ": ";
            for (vector<int>::iterator it = level.begin(); it != level.end(); it++) {
                for (int i = 1; i <= n; i++) {
                    m[*it][i] = false;
                }
                m[*it][*it] = true;
                os << *it << " ";
            }
            os << endl;
            draw(m, n);
        } else {
            isEnded = true;
        }
    }

    return 0;
}