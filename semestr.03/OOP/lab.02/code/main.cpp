#include <iostream>
#include <stdio.h>
#include <string.h>
#include <math.h>

using namespace std;

class figure {
    public:
        virtual void print() = 0;
        virtual void scan() = 0;
        virtual double area() = 0;
};

class rectangle: public figure {
    private:
        double width;
        double height;
    public:
        rectangle() {}
        
        rectangle(double w, double h) {
            width = w;
            height = h;
        }
        
        ~rectangle() {
            width = 0;
            height = 0;
        }
        
        void print() {
            cout << "Rectangle width = " << width << "; height = " << height << "; area = " << area() << endl;
        }
        
        void scan() {
            cout << "Input rectangle width:" << endl;
            cin >> width;
            cout << "Input rectangle height:" << endl;
            cin >> height;
        }
        
        double area() {
            return width*height;
        }
};

class circle: public figure {
    private:
        double radius;
    public:
        circle() {}
        
        circle(double r) {
            radius = r;
        }
        
        ~circle() {
            radius = 0;
        }
        
        void print() {
            cout << "Circle radius = " << radius << "; area = " << area() << endl;
        }
        
        void scan() {
            cout << "Input circle radius:" << endl;
            cin >> radius;
        }
        
        double area() {
            return M_PI*pow(radius,2);
        }
};

class rtriangle: public figure {
    private:
        double base;
        double side;
    public:
        rtriangle() {}
        
        rtriangle(double b, double s) {
            base = b;
            side = s;
        }
        
        ~rtriangle() {
            base = 0;
            side = 0;
        }
        
        void print() {
            cout << "Right triangle base = " << base << "; side = " << side << "; area = " << area() << endl;
        }
        
        void scan() {
            cout << "Input right triangle base:" << endl;
            cin >> base;
            cout << "Input right triangle side:" << endl;
            cin >> side;
        }
        
        double area() {
            return base/2.0 * sqrt(pow(side,2) - base/2.0);
        }
};

int main() {
    cout << "SevGU semestr.03 OOP lab.02" << endl;

    cout << "Demonstration start" << endl;
    
    figure* f;
    
//    cout << "Creating rectangle and assigning it to f" << endl;
    cout << "> f = new rectangle();" << endl;
    f = new rectangle();
//    cout << "Scaning rectangle" << endl;
    cout << "> f->scan();" << endl;
    f->scan();
//    cout << "Printing rectangle" << endl;
    cout << "> f->print();" << endl;
    f->print();
    
//    cout << "Creating circle and assigning it to f" << endl;
    cout << "> f = new circle();" << endl;
    f = new circle();
//    cout << "Scaning circle" << endl;
    cout << "> f->scan();" << endl;
    f->scan();
//    cout << "Printing circle" << endl;
    cout << "> f->print();" << endl;
    f->print();
    
//    cout << "Creating rtriangle and assigning it to f" << endl;
    cout << "> f = new rtriangle();" << endl;
    f = new rtriangle();
//    cout << "Scaning rtriangle" << endl;
    cout << "> f->scan();" << endl;
    f->scan();
//    cout << "Printing circle" << endl;
    cout << "> f->print();" << endl;
    f->print();

    cout << "Demonstration end" << endl;

    return 0;
}