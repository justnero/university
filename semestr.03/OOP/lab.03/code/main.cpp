#include <iostream>
#include <stdio.h>
#include <string.h>
#include <math.h>

using namespace std;

const int STR_SIZE = 128;

class student;
class sheet;
bool is_good(student*);

class man {
    private:
        char name[STR_SIZE];
        int birth_year;
        int age;
    public:
        man() {
            strcpy(name,"empty");
            birth_year = 1970;
            age = 1;
        }
        
        man(char n[],int by,int a) {
            strcpy(name,n);
            birth_year = by;
            age = a;
        }
        
        virtual void scan() {
            cout << "Input name:" << endl;
            cin.getline(name,STR_SIZE);
            cout << "Input birth year:" << endl;
            cin >> birth_year;
            cout << "Input age:" << endl;
            cin >> age;
        }
        
        virtual void print() {
            cout << "Name: " << name << endl;
            cout << "Birth year: " << birth_year << endl;
            cout << "Age: " << age << endl;
        }
        
        void grow() {
            age++;
        }
        
        friend class sheet;
        friend bool is_good(student*);
};

class learner {
    private:
        int id;
        int course;
        double avarage;
    public:
        learner() {
            id = 1;
            course = 1;
            avarage = 5.0;
        }
        
        learner(int i,int c,double a) {
            id = i;
            course = c;
            avarage = a;
        }
        
        virtual void scan() {
            cout << "Input id:" << endl;
            cin >> id;
            cout << "Input course:" << endl;
            cin >> course;
            cout << "Input avarage:" << endl;
            cin >> avarage;
        }
        
        virtual void print() {
            cout << "ID: " << id << endl;
            cout << "Course: " << course << endl;
            cout << "Avarage: " << avarage << endl;
        }
        
        void pass() {
            course++;
        }
        
        friend class sheet;
        friend bool is_good(student*);
};

class student: public man, public learner {
    private:
        int dormitory;
    public:
        student():man(),learner() {
            dormitory = 1;
        }
        
        student(char n[],int by,int a,int i,int c,double av,int d):man(n,by,a),learner(i,c,av) {
            dormitory = d;
        }
        
        virtual void scan() {
            man::scan();
            learner::scan();
            cout << "Input dormitory:" << endl;
            cin >> dormitory;
        }
        
        virtual void print() {
            man::print();
            learner::print();
            cout << "Dormitory: " << dormitory << endl;
        }
        
        void resettle(int d) {
            dormitory = d;
        }
        
        friend class sheet;
        friend bool is_good(student*);
};

class sheet {
    private:
        int id;
        student* stud;
    public:
        sheet(student* s) {
            stud = s;
        }
        
        sheet(student* s,int i) {
            stud = s;
            id = i;
        }
        
        void scan() {
            cout << "Input sheet id:" << endl;
            cin >> id;
        }
        
        void print() {
            cout << "\tSheet #" << id << endl;
            cout << "#" << stud->id << " " << stud->name << (is_good(stud) ? " good" : " not good") << " avarage " << stud->avarage << endl;
        }
        
};

bool is_good(student* stud) {
    return (stud->avarage > 4.0);
}

int main() {
    cout << "SevGU semestr.03 OOP lab.03" << endl;

    cout << "Demonstration start" << endl;
    
    student* stud;
    sheet* sh;
    
//    cout << "Creating student and assigning it to stud" << endl;
    cout << "> stud = new student();" << endl;
    stud = new student();
    
//    cout << "Scanning student" << endl;
    cout << "> stud->scan();" << endl;
    stud->scan();
    
//    cout << "Growing student" << endl;
    cout << "> stud->grow();" << endl;
    stud->grow();
//    cout << "Passing student" << endl;
    cout << "> stud->pass();" << endl;
    stud->pass();
//    cout << "Resettling student in 4th dorm" << endl;
    cout << "> stud->resettle(4);" << endl;
    stud->resettle(4);
    
//    cout << "Printing student" << endl;
    cout << "> stud->print();" << endl;
    stud->print();
    
//    cout << "Creating sheet for student and assigning it to sh" << endl;
    cout << "> sh = new sheet(stud);" << endl;
    sh = new sheet(stud);
    
//    cout << "Scanning sheet" << endl;
    cout << "> sh->scan();" << endl;
    sh->scan();
//    cout << "Printing sheet" << endl;
    cout << "> sh->print();" << endl;
    sh->print();
    
    cout << "Demonstration end" << endl;

    return 0;
}