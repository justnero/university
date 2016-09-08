#include <iostream>
#include <fstream>
#include <algorithm>
#include <string.h>
#include <stack>

using namespace std;

typedef pair<int,int> pii;

int main(int argc, char **argv) {
	if(argc != 2) {
		cout << "Invalid usage" << endl;
		return -1;
	}
	ifstream is(argv[1]);

	stack<pii> parts;
	char c;
	int i = 0;
	int start = 0;
	bool startSearch = false;
	is >> noskipws;
	while(is >> c) {
		if(startSearch) {
			if(c != ' ' && c != '\t' && c != '\n') {
				start = i;
				startSearch = false;
			}
		} else {
			if(c == '.' || c == '!' || c == '?') {
				parts.push(make_pair(start, i));
				startSearch = true;
			}
		}
		i++;
	}
	if(!startSearch) {
		parts.push(make_pair(start,i-1));
	}

	pii el;
	while(parts.size()) {
		el = parts.top();
		parts.pop();
		is.clear();
		is.seekg(el.first);
		for(int i=el.first;i <= el.second; i++) {
			is >> c;
			cout << c;
		}
		cout << " ";
	}
	cout << endl;

	return 0;
}