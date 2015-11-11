#include <Windows.h>
#include <iostream>
#include <conio.h>

using namespace std;

const int PAGE_SIZE=4096;
const int N_PAGES=4;

void sort(int* a,int N) {
	long i = 0, j = N-1;
	int temp, p = a[N/2];

	do {
		while(a[i] < p) {
			i++;
		}
		while(a[j] > p) {
			j--;
		}
		if(i <= j) {
			temp = a[i]; 
			a[i] = a[j]; 
			a[j] = temp;
			i++; 
			j--;
		}
	} while(i<=j);

	if(j > 0) {
		sort(a,j);
	}
	if(N > i) {
		sort(a+i,N-i);
	}
}

int main() {
	HANDLE hMapFile;
	HANDLE hSem;
	int *pBuf;
	int nEl;

	hSem = OpenSemaphore(SEMAPHORE_MODIFY_STATE,FALSE,"sem");
	if(hSem) {
		printf("[MemSort] Opened semaphore\n");
	} else {
		fprintf(stderr, "[MemSort] Error opening semaphore %d\n", GetLastError());
		_getch();
		exit(-1);
	}
	WaitForSingleObject(hSem, INFINITE);
	
	hMapFile = OpenFileMapping(FILE_MAP_ALL_ACCESS,FALSE,"file");
	if(hMapFile) {
		printf("[MemSort] Opened File mapping\n");
	} else {
		fprintf(stderr, "[MemSort] Error opening file mapping %d\n", GetLastError());
		_getch();
		exit(-1);
	}

	pBuf = (int*)MapViewOfFile(hMapFile, FILE_MAP_ALL_ACCESS, 0, 0, PAGE_SIZE*N_PAGES);
	if(pBuf) {
		printf("[MemSort] Mapped file\n");
	} else {
		fprintf(stderr, "[MemSort] Error mapping file %d\n", GetLastError());
		_getch();
		exit(-1);
	}

	nEl = (PAGE_SIZE*N_PAGES)/sizeof(int);
	sort(pBuf,nEl);

	

	printf("[MemSort] Sorted the array\n");

	UnmapViewOfFile((PVOID)pBuf);
	CloseHandle(hMapFile);
	ReleaseSemaphore(hSem,1,NULL);
	printf("[MemSort] Released semaphore\n");
	CloseHandle(hSem);

	return 0;
}
