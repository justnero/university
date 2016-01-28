#include <string.h>
#include <wchar.h>

#include "display.h"
#include "list.h"
#include "form.h"

namespace form {
    
    form_t* full_form = NULL;
    
    int current_el = 0;
    
    void init_forms() {
        field_t** field = new field_t*[10];
        field[0] = new_field(L"ID",3,2,30,&validator_number);
        field[1] = new_field(L"Имя",20,4,30,&validator_string);
        field[2] = new_field(L"Год",4,6,30,&validator_number);
        field[3] = new_field(L"Пол",1,8,30,&validator_sex);
        field[4] = new_field(L"Профессия",10,10,30,&validator_string);
        field[5] = new_field(L"Опыт",4,12,30,&validator_number);
        field[6] = new_field(L"Разряд",6,14,30,&validator_number);
        field[7] = new_field(L"Цех",3,16,30,&validator_number);
        field[8] = new_field(L"Участок",7,18,30,&validator_number);
        field[9] = new_field(L"Зарплата",9,20,30,&validator_money);
        full_form = new_form(field,10,NULL);
    }
    
    field_t* new_field(const wchar_t* name,int len,int y,int x,bool (*validator)(const wchar_t*)) {
        field_t* r = new field_t;
        r->name = new wchar_t[len+1];
        wcscpy(r->name,name);
        r->data = new wchar_t[len+1];
        r->data[0] = L'\0';
        r->index = 0;
        r->length = len;
        r->height = 2;
        r->width = len;
        r->y = y;
        r->x = x;
        r->validator = validator;
        return r;
    }
    
    form_t* new_form(field_t** field,int length,bool (*submit)(const form_t*)) {
        form_t* r = new form_t;
        r->field = field;
        r->length = length;
        r->current_field = 0;
        r->submit = submit;
        return r;
    }
    
    void clear(form_t* f) {
        for(int i=0;i<f->length;i++) {
            f->field[i]->data[0] = L'\0';
            f->field[i]->index = 0;
        }
        f->current_field = 0;
    }
    
    bool key(form_t* f,int c) {
        switch(c) {
            case KEY_UP:
                if(f->current_field == 0) {
                    f->current_field = f->length-1;
                } else {
                    f->current_field--;
                }
                break;
            case KEY_DOWN:
                if(f->current_field == f->length-1) {
                    f->current_field = 0;
                } else {
                    f->current_field++;
                }
                break;
            case KEY_BACKSPACE:
            case 127:
                if(f->field[f->current_field]->index != 0) {
                    f->field[f->current_field]->data[--f->field[f->current_field]->index] = L'\0';
                }
                break;
                break;
            case KEY_ENTER:
            case L'\n':
                f->submit(f);
                break;
            default:
                if(c && f->field[f->current_field]->index < f->field[f->current_field]->length) {
                    wchar_t* tmp = new wchar_t[f->field[f->current_field]->length+1];
                    wcscpy(tmp,f->field[f->current_field]->data);
                    f->field[f->current_field]->data[f->field[f->current_field]->index++] = c;
                    f->field[f->current_field]->data[f->field[f->current_field]->index] = L'\0';
                    if(!f->field[f->current_field]->validator(f->field[f->current_field]->data)) {
                        wcscpy(f->field[f->current_field]->data,tmp);
                        f->field[f->current_field]->index--;
                    }
                    delete tmp;
                }
                break;
        }
        return true;
    }
    
    bool add_submit(const form_t* f) {
        for(int i=0;i<f->length;i++) {
            if(!f->field[i]->index) {
                return false;
            }
        }
        list::data_t d;
        d.id = list::wtoi(f->field[0]->data);
        wcscpy(d.name,f->field[1]->data);
        d.b_year = list::wtoi(f->field[2]->data);
        d.sex = f->field[3]->data[0];
        wcscpy(d.prof,f->field[4]->data);
        d.exp = list::wtoi(f->field[5]->data);
        d.rank = list::wtoi(f->field[6]->data);
        d.guild = list::wtoi(f->field[7]->data);
        d.sect = list::wtoi(f->field[8]->data);
        d.salary = list::wtof(f->field[9]->data);
        if(!list::push_back(list::global,d)) {
            display::show_message(display::message_nomemory);
        }
        display::go_home();
        display::toggle_menu();
        return true;
    }
    
    bool edit_submit(const form_t* f) {
        for(int i=0;i<f->length;i++) {
            if(!f->field[i]->index) {
                return false;
            }
        }
        list::data_t d;
        d.id = list::wtoi(f->field[0]->data);
        wcscpy(d.name,f->field[1]->data);
        d.b_year = list::wtoi(f->field[2]->data);
        d.sex = f->field[3]->data[0];
        wcscpy(d.prof,f->field[4]->data);
        d.exp = list::wtoi(f->field[5]->data);
        d.rank = list::wtoi(f->field[6]->data);
        d.guild = list::wtoi(f->field[7]->data);
        d.sect = list::wtoi(f->field[8]->data);
        d.salary = list::wtof(f->field[9]->data);
        if(!list::update(list::global,form::current_el,d)) {
            display::show_message(display::message_noelement);
        }
        display::go_home();
        display::toggle_menu();
        return true;
    }
    
    bool validator_number(const wchar_t* s) {
        if(s[0] == L'0') {
            return false;
        }
        int l = wcslen(s);
        for(int i=0;i<l;i++) {
            if(!(s[i] >= L'0' && s[i] <= L'9')) {
                return false;
            }
        }
        return true;
    }
    
    bool validator_word(const wchar_t* s) {
        int l = wcslen(s);
        for(int i=0;i<l;i++) {
            if(!((s[i] >= L'0' && s[i] <= L'9') ||
                 (s[i] >= L'a' && s[i] <= L'z') ||
                 (s[i] >= L'A' && s[i] <= L'Z') ||
                 (s[i] >= L'а' && s[i] <= L'я') ||
                 (s[i] >= L'А' && s[i] <= L'Я') ||
                 (s[i] == L'ё' || s[i] == L'Ё') ||
                 (s[i] == L'-' || s[i] == L'_')
                )) {
                return false;
            }
        }
        return true;
    }
    
    bool validator_string(const wchar_t* s) {
        int l = wcslen(s);
        for(int i=0;i<l;i++) {
            if(!((s[i] >= L'0' && s[i] <= L'9') ||
                 (s[i] >= L'a' && s[i] <= L'z') ||
                 (s[i] >= L'A' && s[i] <= L'Z') ||
                 (s[i] >= L'а' && s[i] <= L'я') ||
                 (s[i] >= L'А' && s[i] <= L'Я') ||
                 (s[i] == L'ё' || s[i] == L'Ё') ||
                 (s[i] == L' ' || s[i] == L'-' || s[i] == L'_')
                )) {
                return false;
            }
        }
        return true;
    }
    
    bool validator_money(const wchar_t* s) {
        if(s[0] == L'0') {
            return false;
        }
        bool dot = false;
        int l = wcslen(s);
        for(int i=0;i<l;i++) {
            if((s[i] >= L'0' && s[i] <= L'9') || s[i] == L'.' || s[i] == L',') {
                    if(s[i] == L'.' || s[i] == L',') {
                        if(!dot) {
                            if(i == 0) {
                                return false;
                            } else {
                                dot = true;
                            }
                        } else {
                            return false;
                        }
                    }
            } else {
                return false;
            }
        }
        return true;
    }
    
    bool validator_sex(const wchar_t* s) {
        int l = wcslen(s);
        for(int i=0;i<l;i++) {
            if(!(s[i] == L'М' || s[i] == L'Ж')) {
                return false;
            }
        }
        return true;
    }
    
}