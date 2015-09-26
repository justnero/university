#include <math.h>
#include <iomanip>
#include <iostream>

#define abs(n) (((n)<0)?-(n):(n)) // Макрос функции модуля

using namespace std;

int main(int argc, char** argv) {
    double a,b,xstart,xfinish,dx,x,z;
    cout << "Введите a:           ";
    cin >> a;
    cout << "Введите b:           ";
    cin >> b;
    cout << "Введите x начальное: ";
    cin >> xstart;
    cout << "Введите x конечное:  ";
    cin >> xfinish;
    cout << "Введите шаг dx:      ";
    cin >> dx;
    x = xstart-dx; // Установление начального x
    cout << "╔═════════╦═════════╗" << endl;
    cout << "║    x    ║    z    ║" << endl;
    cout.precision(3);
    cout.setf(ios::showpoint);
    cout.setf(ios::right,ios::adjustfield);
    cout.setf(ios::fixed,ios::floatfield);
    while(x+dx <= xfinish) { 
    	x+=dx; // Вычисление текущего x
    	cout << "╠═════════╬═════════╣" << endl;
    	cout << "║" << setw(9) << x << "║";
	    if(x <= a) { // Случай x <= a
	        if(x <= 0) { // Проверка ОДЗ
    			cout << "   ОДЗ   ║" << endl;
	            continue;
	        }
	        // Расчёт значения функции
	        z = pow(exp((x-1)/atan(x)),1/3.)+pow(tan(x),2)+log(x)+6;
	    } else if((a < x) && (x < b)) { // Случай a < x < b
	        if((x < 0) || (x > 1)) { // Проверка ОДЗ
    			cout << "   ОДЗ   ║" << endl;
	            continue;
	        }
	        // Расчёт значения функции
	        z = pow((asin(x)+sinh(x)),(cos(x)+pow(x,2)+exp(1)));
	    } else { // Случай b <= x
	        if(x <= 0) { // Проверка ОДЗ
    			cout << "   ОДЗ   ║" << endl;
	            continue;
	        }
	        // Расчёт значения функции
	        z = pow(abs(pow(x,(1.3/x))-log10(1+x)),(1.3+pow(x,2.0))) + pow(x,5) + x;
	    }
    	cout << setw(9) << z << "║" << endl;
	}
	cout << "╚═════════╩═════════╝" << endl;
    return 0;
}

