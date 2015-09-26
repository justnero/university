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

struct node_t {
	data_t data;
	node_t *left,*right;
};


const int size_d = sizeof(data_t);
node_t *root;
// Добавить узел
void add_node(data_t data) {
	node_t *node = new node_t;
	node->data = data;
	node->left = node->right = NULL;
	if(root == NULL) {
		root = node;
	} else {
		node_t *tmp = root;
		while(tmp != NULL) {
			if(node->data.id > tmp->data.id) {
				if(tmp->right == NULL) {
					tmp->right = node;
					tmp = NULL;
				} else {
					tmp = tmp->right;
				}
			} else {
				if(tmp->left == NULL) {
					tmp->left = node;
					tmp = NULL;
				} else {
					tmp = tmp->left;
				}
			}
		}
	}
}
// Чтение файла
void read_file(FILE *f) {
	fseek(f,0,SEEK_END);
	long len = ftell(f)/size_d;
	rewind(f);
	root = NULL;
	data_t data;
	for(int i=0;i<len;i++) {
		fread(&data,size_d,1,f);
		add_node(data);
	}
}
// Запись узла в файл
void write_data(FILE *f,node_t *node) {
	if(node == NULL) {
		return;
	}
	fwrite(&node->data,size_d,1,f);
	write_data(f,node->left);
	write_data(f,node->right);
}
// Запись файла
FILE* write_file(FILE *f) {
	fclose(f);
	f = fopen("data.bin","w+b");
	write_data(f,root);
	return f;
}
// Ввод данных с клавиатуры
data_t input() {
	data_t data;
	cout << "Train number:" << endl;
	cin >> data.id;
	cin.get();
	cout << "Departure point:" << endl;
	cin.getline(data.from,STR_SIZE);
	cout << "Arrival point:" << endl;
	cin.getline(data.to,STR_SIZE);
	cout << "Departure time:" << endl;
	cin.getline(data.dep,TIME_SIZE);
	cout << "Arrival time:" << endl;
	cin.getline(data.ar,TIME_SIZE);
	cout << "Ticket price:" << endl;
	cin >> data.price;
	cin.get();
	return data;
}
// Организация очереди
void organize() {
	char c;
	do {
		add_node(input());
		cout << "Continue oranization? (y/n): ";
		cin >> c;
		cin.get();
	} while(c == 'y');
}
// Печать дерева
void show_all(node_t *node) {
	if(root == NULL) {
		cout << "Tree is empty" << endl;
		cout << "Press enter to continue..." << endl;
		cin.get();
		return;
	}
	if(node == NULL) {
		return;
	}
	if(node == root) {
		cout << "+---+------------+------------+-----------+-----------+---------+" << endl;
		cout << "| # |    From    |     To     | Departure |  Arrival  |  Price  |" << endl;
	}
	cout.precision(2);
	cout << "+---+------------+------------+-----------+-----------+---------+" << endl;
	cout << "|" << setw(3) << node->data.id << "|" << setw(12) << node->data.from << "|" << setw(12) << node->data.to << "|" << setw(11) << node->data.dep << "|" << setw(11) << node->data.ar << "|" << setw(9) << node->data.price << "|" << endl;
	show_all(node->left);
	show_all(node->right);
	if(node == root) {
		cout << "+---+------------+------------+-----------+-----------+---------+" << endl;
		cout << "Press enter to continue..." << endl;
		cin.get();
	}
}
// Печать структуры дерева
void show_map(node_t *node,int tab) {
	if(tab == 0) {
		if(node == NULL) {
			cout << "Tree is empty" << endl;
		} else {
			cout << "Tree structure:" << endl;
		}
	}
	if(node != NULL) {
		show_map(node->right,tab+3);
		cout << setw(tab) << " " << node->data.id << endl;
		show_map(node->left,tab+3);
	}
	if(tab == 0) {
		cout << "Press enter to continue..." << endl;
		cin.get();
	}
}
// Печать дерева
void show_least() {
	if(root == NULL) {
		cout << "Tree is empty" << endl;
		cout << "Press enter to continue..." << endl;
		cin.get();
		return;
	}
	node_t *node = root;
	while(node->left != NULL) {
		node = node->left;
	}
	cout << "+---+------------+------------+-----------+-----------+---------+" << endl;
	cout << "| # |    From    |     To     | Departure |  Arrival  |  Price  |" << endl;
	cout << "+---+------------+------------+-----------+-----------+---------+" << endl;
	cout.precision(2);
	cout << "|" << setw(3) << node->data.id << "|" << setw(12) << node->data.from << "|" << setw(12) << node->data.to << "|" << setw(11) << node->data.dep << "|" << setw(11) << node->data.ar << "|" << setw(9) << node->data.price << "|" << endl;
	cout << "+---+------------+------------+-----------+-----------+---------+" << endl;
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
		cout << "2 - Add node" << endl;
		cout << "3 - Show elements" << endl;
		cout << "4 - Show structure" << endl;
		cout << "5 - Show the least element" << endl;
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
				add_node(input());
				f = write_file(f);
				break;
			case '3':
				show_all(root);
				break;
			case '4': 
				show_map(root,0);
				break;
			case '5': 
				show_least();
				break;
			case '6':
				fclose(f);
				return 0;
		}
	}
	fclose(f);
	return 0;
}