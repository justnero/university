#include <iomanip>
#include <iostream>

#define SIZE_W 101
#define SIZE_H 100
#define BORDER 100

using namespace std;

// Ввод размеров матрицы
void input_size(int* n,int* m) {
	cout << "Row count: ";
	cin >> *n;
	cout << "Col count: ";
	cin >> *m;
}

// Ввод матрицы
int** input_matrix(int** matrix,int n,int m) {
	cout << "Elements :" << endl;
	matrix = new int*[SIZE_H];
	for(int i=0;i<n;i++) {
		matrix[i] = new int[SIZE_W];
		matrix[i][BORDER] = 0;
		for(int j=0;j<m;j++) {
			cin >> matrix[i][j];
			if((matrix[i][j] > 0) && (matrix[i][j] % 2 == 0)) {
				matrix[i][BORDER] += matrix[i][j];
			}
		}
	}
	return matrix;
}

// Расчёт кол-ва нулей в столбцах
int count_zero(int** matrix,int n,int m) {
	int ret_val = m;
	for(int j=0;j<m;j++) {
		for(int i=0;i<n;i++) {
			if(matrix[i][j] == 0) {
				ret_val--;
				break;
			}
		}
	}
	return ret_val;
}

// Сортировка матрицы
int** sort(int** matrix,int n,int m) {
	int min,tmp;
	for(int i=0;i<n-1;i++) {
		min = i;
		for(int j=i+1;j<n;j++) {
			if(matrix[min][BORDER] > matrix[j][BORDER]) {
				min = j;
			}
		}
		for(int j=0;j<m;j++) {
			tmp = matrix[min][j];
			matrix[min][j] = matrix[i][j];
			matrix[i][j] = tmp;
		}
	}
	return matrix;
}

// Вывод кол-ва столбцов без нулей и отсортированной матрицы
void output(int cnt,int** matrix,int n,int m) {
	cout << "Cols without zero: " << cnt << endl;
	cout << "Sorted matrix:" << endl;
	for(int i=0;i<n;i++) {
		for(int j=0;j<m;j++) {
			cout << setw(3) << matrix[i][j] << " ";
		}
		cout << endl;
	}
}

int main(int argc, char** argv) {
	int n,m;
	int** matrix;

	input_size(&n,&m);
	matrix = input_matrix(matrix,n,m);
	matrix = sort(matrix,n,m);
	output(count_zero(matrix,n,m),matrix,n,m);

	delete matrix;

	return 0;
}