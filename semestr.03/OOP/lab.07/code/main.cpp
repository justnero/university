#include <iostream>
#include <sstream>
#include <fstream>
#include <iomanip>
#include <string.h>
#include <algorithm>
#include <list>
#include <stdio.h>
#include <stdlib.h>
#include <math.h> 

using namespace std;

struct datetime {
    int year;
    int month;
    int day;
    int hour;
    int minute;
    int second;
};

struct node_s {
    char name[255];
    datetime created_at;
    unsigned long long hits;
};

class node {
private:
    char name[255];
    datetime created_at;
    unsigned long long hits;
public:
    node() {
        cout << "Name:" << endl;
        cin >> name;
        cout << "Date & time (dd mm yyyy hh mm ss):" << endl;
        cin >> created_at.day >> created_at.month >> created_at.year >> created_at.hour >> created_at.minute >> created_at.second;
        cout << "Views:" << endl;
        cin >> hits;
    }
    
    node(const node_s& s) {
        strcpy(name,s.name);
        created_at = s.created_at;
        hits = s.hits;
    }
    
    node(const char* n,const datetime c,const unsigned long long h = 0) {
        strcpy(name,n);
        created_at = c;
        hits = h;
    }
    
    const char* get_name() const {
        return name;
    }
    
    datetime get_created_at() const {
        return created_at;
    }
    
    unsigned long long get_hits() const {
        return hits;
    }
    
    node_s to_struct() {
        node_s t;
        strcpy(t.name,name);
        t.created_at = created_at;
        t.hits = hits;
        return t;
    }
    
    void hit() {
        hits++;
    }
};

class directory {
private:
    list<node> contents;
    char fname[255];
    
    const char* format_time(datetime t) {
        stringstream ss;
        ss << setfill('0') << setw(2) << t.day << "." << setw(2) << t.month << "." << setw(4) << t.year << " " << setw(2) << t.hour << ":" << setw(2) << t.minute << ":" << setw(2) << t.second;
        return ss.str().c_str();
    }
    
    int compare_time(datetime a,datetime b) {
        if(a.year != b.year) {
            return a.year - b.year;
        }
        if(a.month != b.month) {
            return a.month - b.month;
        }
        if(a.day != b.day) {
            return a.day - b.day;
        }
        if(a.hour != b.hour) {
            return a.hour - b.hour;
        }
        if(a.minute != b.minute) {
            return a.minute - b.minute;
        }
        if(a.second != b.second) {
            return a.second - b.second;
        }
        return 0;
    }
public:
    directory(const char* f) {
        strcpy(fname,f);
    }
    
    void print() {
        if(contents.empty()) {
            cout << "Directory is empty" << endl;
            return;
        }
        list<node>::iterator it;
        for(it = contents.begin();it!=contents.end();++it) {
            it->hit();
            cout << format_time(it->get_created_at()) << ": " << it->get_name() << "; Views: " << it->get_hits() << endl;
        }
    }
    
    void print_views() {
        if(contents.empty()) {
            cout << "Directory is empty" << endl;
            return;
        }
        list<node>::iterator it;
        unsigned long long views = 0;
        for(it = contents.begin();it!=contents.end();++it) {
            if(it->get_hits() > views) {
                views = it->get_hits();
            }
        }
        for(it = contents.begin();it!=contents.end();++it) {
            if(it->get_hits() == views) {
                views = it->get_hits();
                cout << format_time(it->get_created_at()) << ": " << it->get_name() << "; Views: " << it->get_hits() << endl;
            }
        }
    }
    
    void add(const node& f) {
        list<node>::iterator it;
        for(it = contents.begin();it!=contents.end();++it) {
            if(strcmp(it->get_name(),f.get_name()) == 0) {
                contents.erase(it);
                break;
            }
        }
        contents.push_back(f);
    }
    
    void remove(const char* n) {
        if(contents.empty()) {
            cout << "Directory is empty" << endl;
            return;
        }
        list<node>::iterator it;
        bool found = false;
        for(it = contents.begin();it!=contents.end();++it) {
            if(strcmp(it->get_name(),n) == 0) {
                contents.erase(it);
                found = true;
                break;
            }
        }
        if(!found) {
            cout << "No such file" << endl;
        }
    }
    
    void remove(datetime t) {
        if(contents.empty()) {
            cout << "Directory is empty" << endl;
            return;
        }
        list<node>::iterator it;
        bool found = false;
        for(it = contents.begin();it!=contents.end();++it) {
            if(compare_time(it->get_created_at(),t) <= 0) {
                contents.erase(it);
                found = true;
                break;
            }
        }
        if(!found) {
            cout << "No such file" << endl;
        }
    }
    
    void read() {
        contents.erase(contents.begin(),contents.end());
        ifstream fin(fname,ios::binary);
        node_s st;
        while(!fin.eof()) {
            fin.read((char*) &st,sizeof(node_s));
            add(node(st));
        }
        fin.close();
    }
    
    void save() {
        ofstream fout(fname,ios::binary);
        list<node>::iterator it;
        for(it = contents.begin();it!=contents.end();++it) {
            node_s st = it->to_struct();
            fout.write((char*)&st,sizeof(node_s));
        }
        fout.close();
    }
};

int main() {
    cout << "SevGU semestr.03 OOP lab.07" << endl;
    
    char fname[255];
    cout << "Savefile name:" << endl;
    cin >> fname;
    
    directory dir(fname);
    
    char name[255];
    datetime from;
    
    char key;
    do {
        system("clear");
        cout << "SevGU semestr.03 OOP lab.07" << endl;
        cout << "Menu" << endl;
        cout << "\t1.View directory contents" << endl;
        cout << "\t2.View most viewed" << endl;
        cout << "\t3.Add file to directory" << endl;
        cout << "\t4.Delete file from directory (by name)" << endl;;
        cout << "\t5.Delete all files created before" << endl;
        cout << "\t6.Read from file" << endl;
        cout << "\t7.Save to file" << endl;
        cout << endl;
        cout << "\t0.Quit" << endl;
        
        cin >> key;
        
        system("clear");
        switch(key) {
            case '1':
                dir.print();
                cout << "Press Enter to continue..." << endl;
                cin.get();
                cin.get();
                break;
            case '2':
                dir.print_views();
                cout << "Press Enter to continue..." << endl;
                cin.get();
                cin.get();
                break;
            case '3':
                dir.add(node());
                break;
            case '4':
                cout << "Name:" << endl;
                cin >> name;
                dir.remove(name);
                break;
            case '5':
                cout << "Date & time (dd mm yyyy hh mm ss):" << endl;
                cin >> from.day >> from.month >> from.year >> from.hour >> from.minute >> from.second;
                dir.remove(from);
                break;
            case '6':
                dir.read();
                break;
            case '7':
                dir.save();
                break;
        }
    } while(key != '0');
    
    return 0;
}