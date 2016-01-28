#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <wchar.h>

#define min(A,B) (((A)>(B))?(B):(A))

#include "list.h"

namespace list {
    
    list_t global = create();
    list_t* current = &global;
    
    list_t create() {
        list_t l;
        l.head = l.tail = NULL;
        l.length = 0;
        return l;
    }
    
    bool push_back(list_t& l,const data_t& d) {
        node_t* n = new node_t;
        if(n == NULL) {
            return false;
        }
        n->index = l.length;
        n->data = copy(d);
        n->prev = l.tail;
        n->next = NULL;
        
        if(l.head == NULL) {
            l.head = n;
        }
        if(l.tail != NULL) {
            l.tail->next = n;
        }
        l.tail = n;
        l.length++;
        return true;
    }
    
    bool update(list_t& l,const int i,const data_t& d) {
        node_t* n = get(l,i);
        if(n == NULL) {
            return false;
        }
        n->data = copy(d);
        return true;
    }
    
    bool erase(list_t& l,const int i) {
        node_t* n = get(l,i);
        if(n == NULL) {
            return false;
        }
        if(n->prev == NULL) {
            l.head = n->next;
        } else {
            n->prev->next = n->next;
        }
        if(n->next == NULL) {
            l.tail = NULL;
        } else {
            n->next->prev = n->prev;
            node_t* tmp = n->next;
            while(tmp != NULL) {
                tmp->index--;
                tmp = tmp->next;
            }
        }
        l.length--;
        delete n;
        return true;
    }
    
    node_t* get(list_t& l,const int i) {
        if(l.length <= i) {
            return NULL;
        }
        int j = 0;
        node_t* ptr = l.head;
        while(ptr != NULL && j < i) {
            ptr = ptr->next;
            j++;
        }
        return ptr;
    }
    
    list_t* find(list_t& l,const data_t& d) {
        list_t* found = new list_t;
        *found = create();
        node_t* ptr = l.head;
        while(ptr != NULL) {
            if(compare(ptr->data,d)) {
                push_back(*found,ptr->data);
            }
            ptr = ptr->next;
        }
        return found;
    }
    
    void sort(list_t& l,bool (*f)(data_t&,data_t&),bool r) {
        if(l.head != NULL) {
            node_t* i = l.head;
            node_t* j;
            node_t* m;
            while(i->next != NULL) {
                m = i;
                j = i->next;
                while(j != NULL) {
                    bool cmp = f(m->data,j->data);
                    cmp = (r && !cmp) || (!r && cmp);
                    if(cmp) {
                        m = j;
                    }
                    j = j->next;
                }
                if(i != m) {
                    data_t tmp = i->data;
                    i->data = copy(m->data);
                    m->data = copy(tmp);
                }
                i = i->next;
            }
        }
    }
    
    bool compare_by_id(data_t& a,data_t& b) {
        return a.id > b.id;
    }
    
    bool compare_by_name(data_t& a,data_t& b) {
        return wcscmp(a.name,b.name) > 0;
    }
    
    bool compare_by_prof(data_t& a,data_t& b) {
        return wcscmp(a.prof,b.prof) > 0;
    }
    
    bool compare_by_salary(data_t& a,data_t& b) {
        return a.salary > b.salary;
    }
    
    bool read(list_t& l,const char* filename,bool binary) {
        FILE* f = fopen(filename,binary?"rb":"r");
        if(f == NULL) {
            return NULL;
        }
        data_t data;
        if(binary) {
            while(fread(&data,sizeof(data_t),1,f) > 0) {
                push_back(l,data);
            }
        } else {
            wchar_t trash[100];
            fgetws(trash,100,f); // skip line
            fgetws(trash,100,f); // 3
            fgetws(trash,100,f); // times
            
            while(fgetwc(f) != WEOF) {
                fwscanf(f,L"%d",&data.id);
                fgetwc(f);
                fgetws(data.name,DATA_NAME_LEN+1,f);
                erase_spaces(data.name);
                fgetwc(f);
                fwscanf(f,L"%d",&data.b_year);
                fgetwc(f);
                fgetwc(f); // 2 spaces 
                fgetwc(f); // 2 spaces
                wchar_t sex = fgetwc(f);
                data.sex = sex == L'М' ? L'М' : L'Ж';
                fgetwc(f);
                fgetws(data.prof,DATA_PROF_LEN+1,f);
                erase_spaces(data.prof);
                fgetwc(f);
                fwscanf(f,L"%d",&data.exp);
                fgetwc(f);
                fwscanf(f,L"%d",&data.rank);
                fgetwc(f);
                fwscanf(f,L"%d",&data.guild);
                fgetwc(f);
                fwscanf(f,L"%d",&data.sect);
                fgetwc(f);
                fwscanf(f,L"%f",&data.salary);
                fgetwc(f);
                fgetwc(f);
                fgetws(trash,100,f); // skip line
                push_back(l,data);
            }
        }
        fclose(f);
        return true;
    }
    
    bool save(list_t& l,const char* filename,bool binary) {
        FILE* f = fopen(filename,binary?"wb":"w");
        if(f == NULL) {
            return false;
        }
        if(!binary) {
            fwprintf(f,L"┌───┬────────────────────┬────┬───┬──────────┬────┬──────┬───┬───────┬─────────┐\n");
            fwprintf(f,L"│%3S│%20S│%4S│%3S│%10S│%4S│%6S│%3S│%7S│%9S│\n",
                    L"ID",
                    L"Имя",
                    L"Год",
                    L"Пол",
                    L"Профессия",
                    L"Опыт",
                    L"Разряд",
                    L"Цех",
                    L"Участок",
                    L"Зарплата");
        }
        node_t* ptr = l.head;
        while(ptr != NULL) {
            if(binary) {
                fwrite(&(ptr->data),sizeof(data_t),1,f);
            } else {
                fwprintf(f,L"├───┼────────────────────┼────┼───┼──────────┼────┼──────┼───┼───────┼─────────┤\n");
                fwprintf(f,L"│%3d│%20S│%4d│%3C│%10S│%4d│%6d│%3d│%7d│%9.2f│\n",
                        ptr->data.id,
                        ptr->data.name,
                        ptr->data.b_year,
                        ptr->data.sex,
                        ptr->data.prof,
                        ptr->data.exp,
                        ptr->data.rank,
                        ptr->data.guild,
                        ptr->data.sect,
                        ptr->data.salary);
            }
            ptr = ptr->next;
        }
        if(!binary) {
            fwprintf(f,L"└───┴────────────────────┴────┴───┴──────────┴────┴──────┴───┴───────┴─────────┘\n");
        }
        fclose(f);
        return true;
    }
    
    bool save(result_t* r,const int n,const char* filename) {
        FILE* f = fopen(filename,"w");
        if(f == NULL) {
            return false;
        }
        
        fwprintf(f,L"┌───┬─────────────────────────────────────────────────────────────────┬───────┐\n");
        fwprintf(f,L"│   │                 Количество рабочих по стажу лет                 │       │\n");
        fwprintf(f,L"│Цех├──────────┬──────────┬──────────┬──────────┬──────────┬──────────┤ Всего │\n");
        fwprintf(f,L"│   │   До 6   │   6-10   │   11-15  │   16-20  │   21-25  │ Свыше 26 │       │\n");
        
        for(int i=0;i<n;i++) {
            if(r[i].total) {
                fwprintf(f,L"├───┼──────────┼──────────┼──────────┼──────────┼──────────┼──────────┼───────┤\n");
                fwprintf(f,L"│%3d│%10d│%10d│%10d│%10d│%10d│%10d│%7d│\n",
                        r[i].guild,
                        r[i].cat1,
                        r[i].cat2,
                        r[i].cat3,
                        r[i].cat4,
                        r[i].cat5,
                        r[i].cat6,
                        r[i].total);
            }
        }        
        
        fwprintf(f,L"└───┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴───────┘\n");
        fclose(f);
        return true;
    }
    
    data_t copy(const data_t& d) {
        data_t n;
        n = d;
        wcscpy(n.name,d.name);
        wcscpy(n.prof,d.prof);
        return n;
    }
    
    bool compare(const data_t& f,const data_t& s) {
        if(s.id != -1 && f.id != s.id) {
            return false;
        }
        if(wcslen(s.name) != 0 && wcsstr(f.name,s.name) == NULL) {
            return false;
        }
        if(s.b_year != -1 && f.b_year != s.b_year) {
            return false;
        }
        if(s.sex != L'\0' && f.sex != s.sex) {
            return false;
        }
        if(wcslen(s.prof) != 0 && wcsstr(f.prof,s.prof) == NULL) {
            return false;
        }
        if(s.exp != -1 && f.exp != s.exp) {
            return false;
        }
        if(s.rank != -1 && f.rank != s.rank) {
            return false;
        }
        if(s.guild != -1 && f.guild != s.guild) {
            return false;
        }
        if(s.sect != -1 && f.sect != s.sect) {
            return false;
        }
        if(s.salary != -1 && f.salary != s.salary) {
            return false;
        }
        
        return true;
    }
    
    void free(list_t& l) {
        node_t* ptr = l.head;
        node_t* tmp;
        while(ptr != NULL) {
            tmp = ptr->next;
            delete ptr;
            ptr = tmp;
        }
        l.head = l.tail = NULL;
        l.length = 0;
    }
    
    result_t* process(list_t& l,int& n) {
        result_t* r = new result_t[l.length];
        n = 0;
        int k;
        node_t* tmp = l.head;
        while(tmp != NULL) {
            k = n;
            for(int i=0;i<n;i++) {
                if(r[i].guild == tmp->data.guild) {
                    k = i;
                    break;
                }
            }
            if(k == n) {
                r[k].guild = tmp->data.guild;
                r[k].cat1 = 0;
                r[k].cat2 = 0;
                r[k].cat3 = 0;
                r[k].cat4 = 0;
                r[k].cat5 = 0;
                r[k].cat6 = 0;
                r[k].total = 0;
                n++;
            }
            r[k].total++;
            if(tmp->data.exp < 6) {
                r[k].cat1++;
            } else if(tmp->data.exp >= 6 && tmp->data.exp <= 10) {
                r[k].cat2++;
            } else if(tmp->data.exp >= 11 && tmp->data.exp <= 15) {
                r[k].cat3++;
            } else if(tmp->data.exp >= 16 && tmp->data.exp <= 20) {
                r[k].cat4++;
            } else if(tmp->data.exp >= 21 && tmp->data.exp <= 25) {
                r[k].cat5++;
            } else if(tmp->data.exp >= 26) {
                r[k].cat6++;
            }
            tmp = tmp->next;
        }
        int m;
        result_t t;
        for(int i=0;i<n-1;i++) {
            m = i;
            for(int j=i+1;j<n;j++) {
                if(r[j].guild < r[m].guild) {
                    m = j;
                }
            }
            if(m != i) {
                t = r[m];
                r[m] = r[i];
                r[i] = t;
            }
        }
        
        /*
        fwprintf(f,L"┌───┬─────────────────────────────────────────────────────────────────┬───────┐\n");
        fwprintf(f,L"│   │                 Количество рабочих по стажу лет                 │       │\n");
        fwprintf(f,L"│Цех├──────────┬──────────┬──────────┬──────────┬──────────┬──────────┤ Всего │\n");
        fwprintf(f,L"│   │   До 6   │   6-10   │   11-15  │   16-20  │   21-25  │ Свыше 26 │       │\n");
        
        for(int i=0;i<1000;i++) {
            if(r[i].total) {
                fwprintf(f,L"├───┼──────────┼──────────┼──────────┼──────────┼──────────┼──────────┼───────┤\n");
                fwprintf(f,L"│%3d│%10d│%10d│%10d│%10d│%10d│%10d│%7d│\n",
                        i,
                        r[i].cat1,
                        r[i].cat2,
                        r[i].cat3,
                        r[i].cat4,
                        r[i].cat5,
                        r[i].cat6,
                        r[i].total);
            }
        }        
        
        fwprintf(f,L"└───┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴───────┘\n");
        fclose(f);
        */
        return r;
    }

    list_t generate(const char* filename) {
        static const wchar_t* profs[] = {
            L"Инженер",
            L"Плотник",
            L"Слесарь",
            L"Машинист",
            L"Бухгалтер"
        };
        
        list_t l = create();

        FILE* f = fopen(filename,"r");
        if(f == NULL) {
            wprintf(L"Файл не существует\n");
            return l;
        }
        wchar_t sex;
        wchar_t name[DATA_NAME_LEN+1];
        int i = 0;
        while(fwscanf(f,L" %C",&sex) != EOF) {
            fgetws(name,DATA_NAME_LEN+1,f);
            name[wcslen(name)-2] = '\0';
            data_t data;
            data.id = ++i;
            wcscpy(data.name,name);
            data.b_year = 1950 + rand()%40;
            data.sex = sex == L'М';
            wcscpy(data.prof,profs[rand()%5]);
            data.exp = 1+rand()%35;
            data.rank = 1+rand()%3;
            data.guild = 1+rand()%10;
            data.sect = 1+rand()%3;
            data.salary = (150000+(rand()%1000000))/100.0;

            push_back(l,data);
        }
        fclose(f);
        return l;
    }
    
    void erase_spaces(wchar_t* str) {
        int i;
        int len = wcslen(str);
        for(i=0;i<len && str[i] == L' ';i++);
        int k=0;
        for(int j=i;j<len;j++) {
            str[k++] = str[j];
        }
        str[k] = L'\0';
    }
    
    int wtoi(const wchar_t* s) {
        int len = wcslen(s);
        int ret = 0;
        for(int i=0;i<len;i++) {
            ret = ret*10 + (s[i]-L'0');
        }
        return ret;
    }
    
    wchar_t* itow(const int n,const int len) {
        wchar_t* s = new wchar_t[len];
        swprintf(s,len,L"%d",n);
        return s;
    }
    
    float wtof(const wchar_t* s) {
        int len = wcslen(s);
        float ret = 0;
        float exp = 0;
        for(int i=0;i<len;i++) {
            if(s[i] == L'.' || s[i] == L',') {
                exp = 1;
                continue;
            }
            exp /= 10.0;
            ret = ret*10 + (s[i]-L'0');
        }
        return ret*exp;
    }
    
    wchar_t* ftow(const wchar_t* format,const float n,const int len) {
        wchar_t* s = new wchar_t[len];
        swprintf(s,len,format,n);
        return s;
    }
    
}
