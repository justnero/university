#include "stdafx.h"

using namespace std;

const int T = 6;

int main(int argc, char *argv[]) {
	int i;
	char cmd[128],
		 window[64];
	DWORD flags;
	int pid = GetCurrentProcessId();
	cout << "Master: Starting PID: " << pid << endl;
	cout.flush();
	STARTUPINFO info;
	memset(&info, 0, sizeof(info));
	info.cb = sizeof(info);
	HANDLE mtx = CreateMutex(NULL,0,"lab.03.mtx");
	for(i=1;i<=T;i++) {
		sprintf(cmd,"%s %d","Lab.03.Sort_mtx.exe",i);
		sprintf(window,"Process: %d",i);
		flags = NORMAL_PRIORITY_CLASS;
		info.lpTitle = window;
		PROCESS_INFORMATION pinfo;
		CreateProcess(NULL,cmd,NULL,NULL,TRUE,flags,NULL,NULL,&info,&pinfo);
	}
	CloseHandle(mtx);
	Sleep(1000);
	system("pause");
	cout << "Master: Exiting\n";
	return 0;
}
