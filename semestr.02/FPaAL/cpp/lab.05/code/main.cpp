#include <fstream>
#include <iostream>
#include <cstring>

#define WORD_SIZE 16

using namespace std;

int main(int argc, char** argv) {
	char *needle   = new char[WORD_SIZE+1];
	char *filename = new char[WORD_SIZE+1];
	cout << "Word to search: ";
	cin >> needle;
	cout << "Filename: ";
	cin >> filename;

	ifstream inp(filename);
	delete filename;
	inp.seekg(0,ios::end);
	long len = inp.tellg();
	char *buf = new char[len+1];
	inp.seekg(0,ios::beg);
	inp.read(buf,len);
	buf[len] = '\0';
	inp.close();

	cout << endl << '\t' << "Sentances:" << endl;
	char *sentance = new char[len+1];
	char *ptr;
	long k = 0;
	bool found = false;
	for(int i=0;i<len;i++) {
		if(!k && (buf[i] == ' ' || buf[i] == '\t' || buf[i] == '\n')) {
			continue;
		}
		sentance[k++] = buf[i];
		if(buf[i] == '.' || buf[i] == '?' || buf[i] == '!') {
			sentance[k] = '\0';
			ptr = strstr(sentance,needle);
			if(ptr != NULL) {
				if(found) {
					cout << "================" << endl;
				} else {
					found = true;
				}
				cout << sentance << endl;
			}
			k = 0;
		}
	}
	if(!found) {
		cout << "No such sentances" << endl;
	}
	delete needle;
	delete ptr;
	delete sentance;
	delete buf;
	return 0;
}