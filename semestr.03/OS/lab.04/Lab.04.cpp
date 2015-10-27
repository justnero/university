#include "stdafx.h"

using namespace std;

const int N	   = 310;
const int Nbuf = 10;
const int d    = 2;

int buf[Nbuf];

long maxWriter = 0;
long maxReader = 0;

HANDLE writerSemaphore;
HANDLE readerSemaphore;

DWORD WINAPI writerThread(const LPVOID lpParam) {
	for(int i=0;i<N;i++) {
		WaitForSingleObject(writerSemaphore,INFINITE);
		buf[i%Nbuf] = (i+1)*d;
		long prev;
		ReleaseSemaphore(readerSemaphore,1,&prev);
		if(prev+1 > maxReader) {
			maxReader = prev+1;
		}
	}
	return 0;
}

DWORD WINAPI readerThread(const LPVOID lpParam) {
	for(int i=0;i<N;i++) {
		WaitForSingleObject(readerSemaphore,INFINITE);
		cout << buf[i%Nbuf] << " ";
		long prev;
		ReleaseSemaphore(writerSemaphore,1,&prev);
		if(prev+1 > maxWriter) {
			maxWriter = prev+1;
		}
	}
	return 0;
}

int main(int argc, char* argv[]) {
	int pid = GetCurrentProcessId();
	cout << "\tMaster" << endl;
	cout << "PID: " << pid << endl;
	cout << endl;
	cout.flush();
	
	writerSemaphore = CreateSemaphore(NULL,Nbuf,Nbuf,"writerSemaphore");
	readerSemaphore = CreateSemaphore(NULL,0,Nbuf,"readerSemaphore");
	
	HANDLE hWriterThread = CreateThread(NULL,0,&writerThread,NULL,CREATE_SUSPENDED,NULL);
	HANDLE hReaderThread = CreateThread(NULL,0,&readerThread,NULL,CREATE_SUSPENDED,NULL);

	SetThreadPriority(hWriterThread,THREAD_PRIORITY_IDLE);
	SetThreadPriority(hReaderThread,THREAD_PRIORITY_HIGHEST);
	
	ResumeThread(hWriterThread);
	ResumeThread(hReaderThread);

	WaitForSingleObject(hReaderThread,INFINITE);
	CloseHandle(hWriterThread);
	CloseHandle(hReaderThread);

	cout << endl;
	cout << "Semaphores max count:" << endl;
	cout << "Writer: " << maxWriter << "; Reader: " << maxReader << ";" << endl;
	cout << "Pause" << endl;
	system("pause");

	return 0;
}

