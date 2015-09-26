#include <iostream>
#include <fstream>
#include <iomanip>
#include <stdexcept>
#include <math.h>

using namespace std;

class marray {
    private:
        int length;
        char* array;
    public:
        marray(const int len) {
            length = len;
            array = new char[len];
        }

        void put(int i,char c) {
            if(i < 0 || i >= length) {
                throw out_of_range("MArray put index");
            }
            array[i] = c;
        }

        char get(int i) {
            if(i < 0 || i >= length) {
                throw out_of_range("MArray get index");
            }
            return array[i];
        }
};

int main(int argc,char** argv) {
	cout << "SevGU semestr.03 OOP lab.05" << endl;

	double m,n;
    int len;
	
    cout << "Input m and n:" << endl;
	cin >> m >> n;

    cout << "Input char array length:" << endl;
    cin >> len;
    marray ma(len);

    cout << "Input " << len << " char array elements:" << endl;
    char c;
    for(int i=0;i<len;i++) {
        cin >> c;
        ma.put(i,c);
    }


    cout << "Demonstration start" << endl;
	
//    cout << "Creating fout object" << endl;
	cout << "> ofstream fout(\"result.txt\",ios::append);" << endl;
	ofstream fout("result.txt",ios::app);
	
//    cout << "Printing the m^n in fout" << endl;
	cout << "> fout << setfill('@') << setw(15) << setprecision(3) << pow(m,n) << endl;" << endl;
	fout << setfill('@') << setw(15) << setprecision(3) << pow(m,n) << endl;
	
	
//    cout << "Closing fout object" << endl;
	cout << "> fout.close();" << endl;
	fout.close();

//    cout << "Printing last char array element" << endl;
    cout << "> cout << ma.get(len-1) << endl;" << endl;
    cout << ma.get(len-1) << endl;

//    cout << "Printing non-existing last+1 char array element" << endl;
    cout << "> cout << ma.get(len) << endl;" << endl;
    try {
        cout << ma.get(len) << endl;
    } catch(out_of_range& e) {
        cout << "Out of range error: " << e.what() << endl;
    }

//    cout << "Putting char to non-existing last+1 char array element" << endl;
    cout << "> ma.put(len,'x');" << endl;
    try {
        ma.put(len,'x');
    } catch(out_of_range& e) {
        cout << "Out of range error: " << e.what() << endl;
    }
	
    cout << "Demonstration end" << endl;

	return 0;
}