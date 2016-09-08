#include <iostream>
#include <fstream>
#include <algorithm>

using namespace std;

int main(int argc, char **argv) {
	if(argc != 2) {
		cout << "Invalid usage" << endl;
		return -1;
	}
	ifstream is(argv[1]);
	int n, m;
	is >> n >> m;
	int len = n*m;

	int arr[len];
	for(int i=0;i<len;i++) {
		is >> arr[i];
	}
	sort(arr, arr+len);

	int i = len -1;
	while(i > 0 && arr[i] == arr[i-1]) {
		int j = arr[i];
		while(arr[i] == j) {
			i--;
		}
	}

	if((i == 0 && (len == 1 || arr[i] != arr[i+1])) || i > 0) {
		cout << arr[i] << endl;
	} else {
		cout << "No such element" << endl;
	}


	return 0;
}