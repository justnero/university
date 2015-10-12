#include "stdafx.h"

using namespace std;

const int N = 45;

void fill(int* a,int n) {
	for(int i=0;i<n;i++) {
		a[i] = rand()%10000;
	}
}

void sort(int* a, int n) {
int i,j,k;
	int t;
	for(k = n/2; k > 0; k /=2)
        		for(i = k; i < n; i++) {
			t = a[i];
            		for(j = i; j>=k; j-=k) {
			if(t < a[j-k])
                    			a[j] = a[j-k];
                		else
                    			break;
		}
		a[j] = t;
	}
}

void print(int* a,int n,int p) {
	for(int i=0;i<n;i++) {
		cout << p << ":" << a[i] << " ";
	}
	cout << endl;
}

int main(int argc, char *argv[]) {
	if(argc != 2) {
		return 1;
	}
	int pn = atoi(argv[1]);
	
	int* a = new int[N+5];

	fill(a,N);
	sort(a,N);

	print(a,N,pn);

	return 0;
}
