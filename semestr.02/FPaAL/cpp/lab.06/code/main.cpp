#include <stdio.h>
#include <iostream>
#include <iomanip>

#define NAME_SIZE 32
#define GROUP_SIZE 16
#define MARKS_SIZE 5

using namespace std;

struct student {
	char name[NAME_SIZE];
	char group[GROUP_SIZE];
	int marks[MARKS_SIZE];
};

const int size_s = sizeof(student);

void insert(FILE *f,student stud,double avarage) {
	fseek(f,0,SEEK_END);
	long len = ftell(f)/size_s;
	rewind(f);
	if(len == 0) {
		fwrite(&stud,size_s,1,f);
	} else {
		student tmp;
		double tmp_avarage;
		long cnt = 0;
		do {
			fread(&tmp,size_s,1,f);
			tmp_avarage = 0.0;
			for(int i=0;i<MARKS_SIZE;i++) {
				tmp_avarage += tmp.marks[i];
			}
			tmp_avarage /= MARKS_SIZE;
		} while((avarage > tmp_avarage) && (++cnt < len));
		if(tmp_avarage > avarage) {
			fseek(f,-size_s,SEEK_CUR);
		}
		fwrite(&stud,size_s,1,f);
	}
}

void add_student(FILE *f) {
	char c;
	do {
		double avarage = 0.0;
		student stud;
		cout << "Student name:" << endl;
		cin.getline(stud.name,NAME_SIZE);
		cout << "Student group:" << endl;
		cin.getline(stud.group,GROUP_SIZE);
		cout << "Student marks:" << endl;
		for(int i=0;i<MARKS_SIZE;i++) {
			cin >> stud.marks[i];
			avarage += stud.marks[i];
		}
		avarage /= MARKS_SIZE;
		insert(f,stud,avarage);
		cout << "Continue addition? (y/n): ";
		cin >> c;
		cin.get();
	} while(c == 'y');
}

void show_all(FILE *f) {
	fseek(f,0,SEEK_END);
	long len = ftell(f)/size_s;
	rewind(f);
	if(len == 0) {
		cout << "No students" << endl;
		cout << "Press enter to continue..." << endl;
		cin.get();
		return;
	} else {
		cout << "+--------------------------------+----------------+---------+" << endl;
		cout << "|              Name              |      Group     | Avarage |" << endl;
		student stud;
		double avarage;
		for(int i=0;i<len;i++) {
			fread(&stud,size_s,1,f);
			avarage = 0.0;
			for(int j=0;j<MARKS_SIZE;j++) {
				avarage += stud.marks[j];
			}
			avarage /= MARKS_SIZE;
			cout << "|--------------------------------+----------------+---------|" << endl;
			cout.precision(2);
			cout << "|" << setw(32) << stud.name << "|" << setw(16) << stud.group << "|" << setw(9) << avarage << "|" << endl;
		}
		cout << "+--------------------------------+----------------+---------+" << endl;
	}
	cout << "Press enter to continue..." << endl;
	cin.get();
}

void show_best(FILE *f) {
	fseek(f,0,SEEK_END);
	long len = ftell(f)/size_s;
	rewind(f);
	if(len == 0) {
		cout << "No students" << endl;
		cout << "Press enter to continue..." << endl;
		cin.get();
		return;
	} else {
		bool found = false;
		student stud;
		double avarage;
		for(int i=0;i<len;i++) {
			fread(&stud,size_s,1,f);
			avarage = 0.0;
			bool best = false;
			for(int j=0;j<MARKS_SIZE;j++) {
				if(stud.marks[i] >= 4) {
					best = true;
				}
				avarage += stud.marks[j];
			}
			avarage /= MARKS_SIZE;
			if(best) {
				if(!found) {
					cout << "+--------------------------------+----------------+---------+" << endl;
					cout << "|              Name              |      Group     | Avarage |" << endl;
				}
				found = true;
				cout << "|--------------------------------+----------------+---------|" << endl;
				cout.precision(2);
				cout << "|" << setw(32) << stud.name << "|" << setw(16) << stud.group << "|" << setw(9) << avarage << "|" << endl;
			}
		}
		if(found) {
			cout << "+--------------------------------+----------------+---------+" << endl;
		} else {
			cout << "No such students" << endl;
		}
	}
	cout << "Press enter to continue..." << endl;
	cin.get();
}

int main(int argc, char** argv) {
	FILE *f;
	f = fopen("data.bin","r+b");
	if(!f) {
		f = fopen("data.bin","w+b");
		if(!f) {
			cout << "Can`t open (create) file" << endl;
			return 1;
		}
	}
	char c;
	while(1) {
		cout << "1 - Add student" << endl;
		cout << "2 - Show all students" << endl;
		cout << "3 - Show best students" << endl;
		cout << "4 - Exit" << endl;
		cout << "Choose your destiny: ";
		cin >> c;
		cin.get();
		switch(c) {
			case '1':
				add_student(f);
				break;
			case '2':
				show_all(f);
				break;
			case '3': 
				show_best(f);
				break;
			case '4':
				fclose(f);
				return 0;
		}
	}
	fclose(f);
	return 0;
}