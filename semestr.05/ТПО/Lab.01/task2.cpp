#include <iostream>
#include <fstream>
#include <algorithm>
#include <string.h>

using namespace std;

int main(int argc, char **argv) {
	if(argc != 2) {
		cout << "Invalid usage" << endl;
		return -1;
	}
	ifstream is(argv[1]);

	long count = 0l;
	char c;
	while(is >> c) {
		if(c == 'a') {
			count++;
		}
	}

	cout << count << endl;


	return 0;
}