#include <Windows.h>
#include <iostream>
#include <conio.h>
#include <ctime>

using namespace std;

const int PAGE_SIZE=4096;
const int N_PAGES=4;

int main() {
	HANDLE hSem;
	HANDLE hMapFile;
	SECURITY_ATTRIBUTES sAttr;

	STARTUPINFO stInfo;
	PROCESS_INFORMATION pInfo;
	char strCmd[] = "MemSort.exe";
	
	int *pVirtAlloc;
	int *pBuf;
	int nEl;
	int i;

	sAttr.bInheritHandle = TRUE;
	sAttr.lpSecurityDescriptor = NULL;
	sAttr.nLength = sizeof(SECURITY_ATTRIBUTES);
	hSem = CreateSemaphore(&sAttr,0,1,"sem");
	if(hSem) {
		printf("[MemShare] Successfully created semaphore\n");
	} else {
		fprintf(stderr, "[MemShare] Failed to create semaphore %d\n",GetLastError());
		_getch();
		exit(-1);
	}

	ZeroMemory(&stInfo,sizeof(STARTUPINFO));
	if(CreateProcess(NULL,strCmd,NULL,NULL,FALSE,NORMAL_PRIORITY_CLASS,NULL,NULL,&stInfo,&pInfo)) {
		printf("[MemShare] Successfully created process\n");
	} else {
		fprintf(stderr,"[MemShare] Failed to create process %d\n",GetLastError());
		_getch();
		exit(-1);
	}

	pVirtAlloc = (int*) VirtualAlloc(NULL,PAGE_SIZE*N_PAGES,MEM_COMMIT,PAGE_READWRITE);
	if(pVirtAlloc) {
		printf("[MemShare] Successfully allocated memory\n");
	} else {
		fprintf(stderr,"[MemShare] Failed to allocate memory %d\n",GetLastError());
		_getch();
		exit(-1);
	}

	nEl = (PAGE_SIZE*N_PAGES)/sizeof(int);

	srand(time(0));
	for(i=0; i<nEl; ++i) {
		pVirtAlloc[i] = rand() % 30000;
	}
	printf("[MemShare] Successfully filled array\n");
	
	VirtualProtect(pVirtAlloc,PAGE_SIZE*N_PAGES,PAGE_READONLY,NULL);

	hMapFile = CreateFileMapping(INVALID_HANDLE_VALUE,NULL,PAGE_READWRITE,0,PAGE_SIZE*N_PAGES,"file");
	if(hMapFile) {
		printf("[MemShare] Successfully created file mapping\n");
	} else {
		fprintf(stderr,"[MemShare] Failed to create file mapping %d\n",GetLastError());
		_getch();
		exit(-1);
	}

	pBuf = (int*) MapViewOfFile(hMapFile,FILE_MAP_ALL_ACCESS,0,0,PAGE_SIZE*N_PAGES);
	if(pBuf) {
		printf("[MemShare] Successfully mapped file to memory\n");
	} else {
		fprintf(stderr,"[MemShare] Failed to map file to memory %d\n",GetLastError());
		_getch();
		exit(-1);
	}

	CopyMemory(pBuf,pVirtAlloc,PAGE_SIZE*N_PAGES);

	ReleaseSemaphore(hSem, 1, NULL);
	printf("[MemShare] Released semaphore\n");
	Sleep(50);
	WaitForSingleObject(hSem, INFINITE);
	printf("[MemShare] Got semaphore\n");

	VirtualProtect(pVirtAlloc,PAGE_SIZE*N_PAGES,PAGE_READWRITE,NULL);

	CopyMemory(pVirtAlloc,pBuf,PAGE_SIZE*N_PAGES);

	printf("[MemShare] Resulted array is:\n");
	for(i=0; i<nEl; ++i) {
		printf("%5d ", pVirtAlloc[i]);
	}
	printf("\n");
	
	UnmapViewOfFile(pVirtAlloc);
	VirtualFree(pVirtAlloc,PAGE_SIZE*N_PAGES,MEM_RELEASE);

	CloseHandle(hSem);
	CloseHandle(hMapFile);
	CloseHandle(pInfo.hThread);
	CloseHandle(pInfo.hProcess);

	printf("[MemShare] Exiting!\n");
	_getch();
	return 0;
}
