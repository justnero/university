#ifndef DISPLAY_H
#define	DISPLAY_H

#define NCURSES_WIDECHAR 1

#include <curses.h>
#include <panel.h>

namespace display {
    
    /* Блок меню */
    typedef struct menu_t {
        /* Пункты */
        const wchar_t** options;
        /* Длина */
        int length;
        /* Обработчик */
        bool (*key)(int);
    } menu_t;
    
    /* Блок данных */
    typedef struct data_t {
        /* Отрисовщик */
        void (*draw)();
        /* Обработчик */
        bool (*key)(int);
    } data_t;
    
    /* Сообщение */
    typedef struct message_t {
        /* Заголовок */
        const wchar_t* title;
        /* Текст */
        const wchar_t* text;
    } message_t;
    
    /* Переменные сообщений */
    extern const message_t message_nofile;
    extern const message_t message_cantwrite;
    extern const message_t message_nomemory;
    extern const message_t message_noelement;
    extern const message_t message_nothingfound;
    extern const message_t message_author;
    
    
    void init_display();
    
    void close_display();
    
    void go_home();
    
    
    void draw();
    
    void draw_menu();
    
    void draw_data();
    
    
    void toggle_menu();
    
    void switch_menu(menu_t,int=0);
    
    void switch_data(data_t);
    
    void show_message(message_t);
    
    
    bool key();
    
    
    bool key_menu(int);
    
    bool key_menu_index(int);
    
    bool key_menu_list(int);
    
    bool key_menu_find(int);
    
    bool key_menu_sort(int);
    
    bool key_menu_save(int);
    
    bool key_menu_read(int);
    
    bool key_menu_process(int);
    
    bool key_menu_home(int);
    
    
    bool key_data(int);
    
    bool key_data_list(int);
    
    bool key_data_process(int);
    
    bool key_data_find_guild(int);
    
    bool key_data_find_name(int);
    
    bool key_data_form(int);
    
    
    void draw_data_list();
    
    void draw_data_process();
    
    void draw_data_find_guild();
    
    void draw_data_find_name();
    
    void draw_data_form();
    
    
    void draw_message();
    
}

#endif	/* DISPLAY_H */

