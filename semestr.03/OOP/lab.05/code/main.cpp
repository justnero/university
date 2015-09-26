#include <iostream>
#include <math.h>

using namespace std;

template <class T>
T** transpose(T** m,const int h,const int w) {
	T** r = new T*[w];
	for(int i=0;i<w;i++) {
		r[i] = new T[h];
	}
	for(int i=0;i<h;i++) {
		for(int j=0;j<w;j++) {
			r[j][i] = m[i][j];
		}
	}
	return r;
}

int main(int argc,char** argv) {
	cout << "SevGU semestr.03 OOP lab.05" << endl;

    int n_int,n_char,n_double;
    int m_int,m_char,m_double;
    
    cout << "Input int matrix size NxM:" << endl;
    cin >> n_int >> m_int;
    cout << "Input int matrix " << n_int << "x" << m_int << endl;
   	int** ma_int = new int*[n_int];
    for(int i=0;i<n_int;i++) {
    	ma_int[i] = new int[m_int];
    	for(int j=0;j<m_int;j++) {
    		cin >> ma_int[i][j];
    	}
    }
    
    cout << "Input char matrix size NxM:" << endl;
    cin >> n_char >> m_char;
    cout << "Input char matrix " << n_char << "x" << m_char << endl;
   	char** ma_char = new char*[n_char];
    for(int i=0;i<n_char;i++) {
    	ma_char[i] = new char[m_int];
    	for(int j=0;j<m_char;j++) {
    		cin >> ma_char[i][j];
    	}
    }
    
    cout << "Input double matrix size NxM:" << endl;
    cin >> n_double >> m_double;
    cout << "Input double matrix " << n_double << "x" << m_double << endl;
   	double** ma_double = new double*[n_double];
    for(int i=0;i<n_double;i++) {
    	ma_double[i] = new double[m_double];
    	for(int j=0;j<m_double;j++) {
    		cin >> ma_double[i][j];
    	}
    }
	
    cout << "Demonstration start" << endl;

//    cout << "Transposing int matrix ma_int" << endl;
    cout << "> a_int = transpose(ma_int,n_int,m_int);" << endl;
    ma_int = transpose(ma_int,n_int,m_int);
    for(int i=0;i<m_int;i++) {
    	for(int j=0;j<n_int;j++) {
    		cout << ma_int[i][j] << " ";
    	}
    	cout << endl;
    }
    
//    cout << "Transposing int matrix ma_int" << endl;
    cout << "> ma_char = transpose(ma_char,n_char,m_char);" << endl;
    ma_char = transpose(ma_char,n_char,m_char);
    for(int i=0;i<m_char;i++) {
    	for(int j=0;j<n_char;j++) {
    		cout << ma_char[i][j] << " ";
    	}
    	cout << endl;
    }

//    cout << "Transposing int matrix ma_int" << endl;
    cout << "> ma_double = transpose(ma_double,n_double,m_double);" << endl;
    ma_double = transpose(ma_double,n_double,m_double);
    for(int i=0;i<m_double;i++) {
    	for(int j=0;j<n_double;j++) {
    		cout << ma_double[i][j] << " ";
    	}
    	cout << endl;
    }
	
    cout << "Demonstration end" << endl;

	return 0;
}