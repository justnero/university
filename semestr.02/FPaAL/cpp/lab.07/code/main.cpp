#include <stdio.h>
#include <iostream>
#include <iomanip>

#define STR_SIZE 12
#define TIME_SIZE 11

using namespace std;

struct data_t {
	int id;
	char from[STR_SIZE];
	char to[STR_SIZE];
	char dep[TIME_SIZE];
	char ar[TIME_SIZE];
	float price;
};

struct list_t {
	data_t data;
	list_t *next;
};

const int size_d = sizeof(data_t);
list_t *head,*tail;
// Чтение файла
void read_file(FILE *f) {
	fseek(f,0,SEEK_END);
	long len = ftell(f)/size_d;
	rewind(f);
	if(len == 0) {
		head = tail = NULL;
	} else {
		list_t *tmp;
		tmp = new list_t;
		fread(&tmp->data,size_d,1,f);
		tmp->next = NULL;
		head = tail = tmp;
		for(int i=1;i<len;i++) {
			tmp = new list_t;
			fread(&tmp->data,size_d,1,f);
			tmp->next = NULL;
			tail->next = tmp;
			tail = tmp;
		}
	}
}
// Запись файла
FILE* write_file(FILE *f) {
	fclose(f);
	f = fopen("data.bin","w+b");
	list_t *tmp = head;
	while(tmp != NULL) {
		fwrite(&tmp->data,size_d,1,f);
		tmp = tmp->next;
	}
	return f;
}
// Ввод данных с клавиатуры
void input(data_t *data) {
	cout << "Train number:" << endl;
	cin >> data->id;
	cin.get();
	cout << "Departure point:" << endl;
	cin.getline(data->from,STR_SIZE);
	cout << "Arrival point:" << endl;
	cin.getline(data->to,STR_SIZE);
	cout << "Departure time:" << endl;
	cin.getline(data->dep,TIME_SIZE);
	cout << "Arrival time:" << endl;
	cin.getline(data->ar,TIME_SIZE);
	cout << "Ticket price:" << endl;
	cin >> data->price;
	cin.get();
}
// Добавить элемент в хвост
void add_tail() {
	list_t *tmp = new list_t;
	input(&tmp->data);
	tmp->next = NULL;
	if(tail == NULL) {
		head = tail = tmp;
	} else {
		tail->next = tmp;
		tail = tmp;
	}
}
// Добавить элемент в голову
void add_head() {
	list_t *tmp = new list_t;
	input(&tmp->data);
	tmp->next = head;
	if(head == NULL) {
		head = tail = tmp;
	} else {
		head = tmp;
	}
}
// Организация очереди
void organize() {
	char c;
	do {
		add_tail();
		cout << "Continue oranization? (y/n): ";
		cin >> c;
		cin.get();
	} while(c == 'y');
}
// Удаление с головы
void pull() {
	if(head == NULL) {
		cout << "Queue is empty" << endl;
	} else {
		list_t *tmp = head;
		head = head->next;
		if(head == NULL) {
			tail = NULL;
		}
		delete tmp;
		cout << "Train removed" << endl;
	}
	cout << "Press enter to continue..." << endl;
	cin.get();
}
// Печать очереди
void show() {
	if(head == NULL) {
		cout << "Queue is empty" << endl;
	} else {
		cout << "+---+------------+------------+-----------+-----------+---------+" << endl;
		cout << "| # |    From    |     To     | Departure |  Arrival  |  Price  |" << endl;
		cout.precision(2);
		list_t *tmp = head;
		while(tmp != NULL) {
			cout << "+---+------------+------------+-----------+-----------+---------+" << endl;
			cout << "|" << setw(3) << tmp->data.id << "|" << setw(12) << tmp->data.from << "|" << setw(12) << tmp->data.to << "|" << setw(11) << tmp->data.dep << "|" << setw(11) << tmp->data.ar << "|" << setw(9) << tmp->data.price << "|" << endl;
			tmp = tmp->next;
		}
		cout << "+---+------------+------------+-----------+-----------+---------+" << endl;
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
	read_file(f);
	char c;
	while(1) {
		cout << "1 - Organize" << endl;
		cout << "2 - Add in tail" << endl;
		cout << "3 - Add in head" << endl;
		cout << "4 - Pull" << endl;
		cout << "5 - Show" << endl;
		cout << "6 - Exit" << endl;
		cout << "Choose your destiny: ";
		cin >> c;
		cin.get();
		switch(c) {
			case '1':
				organize();
				f = write_file(f);
				break;
			case '2':
				add_tail();
				f = write_file(f);
				break;
			case '3':
				add_head();
				f = write_file(f);
				break;
			case '4': 
				pull();
				f = write_file(f);
				break;
			case '5': 
				show();
				break;
			case '6':
				fclose(f);
				return 0;
		}
	}
	fclose(f);
	return 0;
}