#ifndef FORM_H
#define	FORM_H

namespace form {
    
    /* Поле */
    typedef struct field_t {
        /* Имя */
        wchar_t* name;
        /* Содержимое */
        wchar_t* data;
        /* Индекс */
        int index;
        /* Длина */
        int length;
        /* Высота */
        int height;
        /* Ширина */
        int width;
        /* Y */
        int y;
        /* X */
        int x;
        /* Валидатор */
        bool (*validator)(const wchar_t*);
    } field_t;
    
    /* Форма */
    typedef struct form_t {
        /* Поля */
        field_t** field;
        /* Длина */
        int length;
        /* Текущее поле*/
        int current_field;
        /* Обработчик */
        bool (*submit)(const form_t*);
    } form_t;
    
    /* Полная форма */
    extern form_t* full_form;
    
    /* Редактируемый элемент */
    extern int current_el;
    
    /* Инициализация форм */
    void init_forms();
    
    /* Создание нового поля */
    field_t* new_field(const wchar_t*,int,int,int,bool (*)(const wchar_t*));
    
    /* Создание новой формы */
    form_t* new_form(field_t**,int,bool(*)(const form_t*));
    
    /* Очистка полей формы */
    void clear(form_t*);
    
    /* Обработчик клавиш */
    bool key(form_t*,int c);
    
    /* Обработчик формы добавления */
    bool add_submit(const form_t*);
    
    /* Обработчик формы редактирования */
    bool edit_submit(const form_t*);
    
    
    /* Валидатор целого числа*/
    bool validator_number(const wchar_t*);
    
    /* Валидатор слова */
    bool validator_word(const wchar_t*);
    
    /* Валидатор строки */
    bool validator_string(const wchar_t*);
    
    /* Валидатор денег*/
    bool validator_money(const wchar_t*);
    
    /* Валидатор пола */
    bool validator_sex(const wchar_t*);
}

#endif	/* FORM_H */

