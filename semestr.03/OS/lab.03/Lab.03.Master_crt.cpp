#include "stdafx.h"

using namespace std;

const int T = 6;
const int N = 45;

CRITICAL_SECTION crt;

void fill(int* a,int n) {
	for(int i=0;i<n;i++) {
		a[i] = (rand()+58461)%10000;
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

DWORD WINAPI test(const LPVOID lpParam) {
	int* a = new int[N+5];
	fill(a,N);
	sort(a,N);
	EnterCriticalSection(&crt);
	print(a,N,*(int*)lpParam);
	LeaveCriticalSection(&crt);
	return 0;
}

int main(int argc, char *argv[]) {
	int i;
	char window[64];
	InitializeCriticalSection(&crt);
	int pid = GetCurrentProcessId();
	cout << "Master: Starting PID: " << pid << endl;
	cout.flush();
	STARTUPINFO info;
	memset(&info, 0, sizeof(info));
	info.cb = sizeof(info);
	for(i=1;i<=T;i++) {
		int* z = new int(i);
		CreateThread(NULL,0,&test,z,0,NULL);
	}
	Sleep(5000);
	system("pause");
	cout << "Master: Exiting\n";
	return 0;
}
