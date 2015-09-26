#include <iostream>
#include <math.h>

using namespace std;

typedef long long LL;

class mint {
	protected:
		LL value;
	public:
		mint() {
			value = 0;
		}

		mint(long long v) {
			value = v;
		}

		mint(const mint& mi) {
			value = mi.value;
		}

		bool operator ! () {
			return (value % 2) == 0;
		}

		mint operator + (const mint& mi) const {
			return (value + mi.value);
		}

		mint operator + (const int& i) const {
			return (value + i);
		}

		friend mint& operator -- (mint& mi);

		friend bool operator == (const mint& mi1,const mint& mi2);

		friend istream& operator >> (istream& stream,mint& mi);

		friend ostream& operator << (ostream& stream,const mint& mi);
};

mint& operator -- (mint& mi) {
	mi.value -= 3;
	return mi;
}

bool operator == (const mint& mi1,const mint& mi2) {
	return mi1.value == mi2.value;
}

istream& operator >> (istream& stream,mint& mi) {
	stream >> mi.value;
	return stream;
}

ostream& operator << (ostream& stream,const mint& mi) {
	stream << mi.value;
	return stream;
}

int main(int argc,char** argv) {
    cout << "SevGU semestr.03 OOP lab.04" << endl;

    cout << "Demonstration start" << endl;
    
    mint m1;
    mint m2;

//    cout << "Scanning m1" << endl;
    cout << "> cin >> m1;" << endl;
    cin >> m1;

//    cout << "Scanning m2" << endl;
    cout << "> cin >> m2;" << endl;
    cin >> m2;
    
//    cout << "Using prefix decrement operator to make -3" << endl;
    cout << "> --m1;" << endl;
    --m1;
    
//    cout << "Printing m1" << endl;
    cout << "> cout << m1 << endl;" << endl;
    cout << m1 << endl;
    
//    cout << "Checking is m2 even" << endl;
    cout << "> cout << ((!m2) ? \"true\" : \"false\") << endl;" << endl;
    cout << ((!m2) ? "true" : "false") << endl;

//    cout << "Checking is m1 equals m2" << endl;
    cout << "> cout << ((!m1 == m2) ? \"true\" : \"false\") << endl;" << endl;
    cout << ((m1 == m2) ? "true" : "false") << endl;

//    cout << "Adding 5 to m1" << endl;
    cout << "> m1 = m1 + 5;" << endl;
    m1 = m1 + 5;

//    cout << "Adding m1 to m2" << endl;
    cout << "> m2 = m1 + m2;" << endl;
    m2 = m1 + m2;
    
//    cout << "Printing m1" << endl;
    cout << "> cout << m1 << endl;" << endl;
    cout << m1 << endl;
    
//    cout << "Printing m2" << endl;
    cout << "> cout << m2 << endl;" << endl;
    cout << m2 << endl;
    
    cout << "Demonstration end" << endl;

	return 0;
}