#include <math.h>
#include <iostream>

#define abs(n) ((n<0)?-(n):(n)) // Макрос функции модуля

using namespace std;

int main(int argc, char** argv) {
    double a,b,x,z;
    cout << "Введите a: ";
    cin >> a;
    cout << "Введите b: ";
    cin >> b;
    cout << "Введите x: ";
    cin >> x;
    if(x <= a) { // Случай x <= a
        cout << "x <= a" << endl;
        if(x <= 0) { // Проверка ОДЗ
            cout << "По ОДЗ x > 0" << endl;
            return 0;
        }
        // Расчёт значения функции
        z = pow(exp((x-1)/atan(x)),1/3.)+pow(tan(x),2)+log(x)+6;
    } else if((a < x) && (x < b)) { // Случай a < x < b
        cout << "a < x < b" << endl;
        if((x < 0) || (x > 1)) { // Проверка ОДЗ
            cout << "По ОДЗ 0 <= x <= 1" << endl;
            return 0;
        }
        // Расчёт значения функции
        z = pow((asin(x)+sinh(x)),(cos(x)+pow(x,2)+exp(1)));
    } else { // Случай b <= x
        cout << "b <= x" << endl;
        if(x <= 0) { // Проверка ОДЗ
            cout << "По ОДЗ x > 0" << endl;
            return 0;
        }
        // Расчёт значения функции
        z = pow(abs(pow(x,(1.3/x))-log10(1+x)),(1.3+pow(x,2.0))) + pow(x,5) + x;
    }
    cout << "Ответ: z = " << z << endl;
    return 0;
}

