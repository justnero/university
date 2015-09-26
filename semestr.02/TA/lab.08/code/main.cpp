#include <vector>
#include <iostream>

using namespace std;

const int INF = 1000000000;
bool used[100] = {0};
int g[100][100] = {0};
int n;


void solve() {
	int i, j, v, to;
	vector <int> min_e(n,INF),sel_e(n,-1);
	min_e[0] = 0;
	for(i=0;i<n;i++) {
		v = -1;
		for (j=0;j<n;j++)
			if(!used[j] && (v == -1 || min_e[j] < min_e[v]))
				v = j;
		if (min_e[v] == INF) {
			cout << "No MST!";
			return;
		}
		used[v] = true;
		if(sel_e[v] != -1) {
			if(v < sel_e[v])
				cout << v << " " << sel_e[v] << endl;
			else
				cout << sel_e[v] << " " << v << endl;
		}
		for(to=0;to<n;to++)
			if (g[v][to] < min_e[to]) {
				min_e[to] = g[v][to];
				sel_e[to] = v;
			}
	}
	return;
}

int main(int argc,char **argv) {
	cout << "Input vertex count: ";
	cin >> n;
	cout << "Input martix " << n << "x" << n << ":" << endl;
	for(int i=0;i<n;i++) {
		for(int j=0;j<n;j++) {
			cin >> g[i][j];
			if(g[i][j] == 0) {
				g[i][j] = INF;
			}
		}
	}
	cout << "Edges to keep:" << endl;
	solve();
	return 0;
}