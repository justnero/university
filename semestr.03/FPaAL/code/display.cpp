#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "display.h"
#include "list.h"
#include "form.h"

namespace display {
    
    /* Размеры окна меню (высота, ширина) */
    const int menu_height = 25;
    const int menu_width = 17;
    
    /* Размеры окна данных (высота, ширина) */
    const int data_height = 25;
    const int data_width = 80;
    
    /* Размеры окна сообщения (высота, ширина) */
    const int message_height = 7;
    const int message_width = 50;
    
    /* Позиция курсора на экране */
    int cursor_x = 79;
    int cursor_y = 24;
    
    /* Состояние меню (показывается/скрыто) */
    bool menu_hidden = false;
    /* Состояние сообщения (показывается/скрыто) */
    bool message_hidden = true;
    /* Кол-во запросов на перерисовку окна данных */
    int redraw_data = 2;
    
    /* Выбранный элемент основного списка на экране */
    int data_list_selected = 0;
    /* Отступ с которым отображается основной список на экране */
    int data_list_offset = 0;
    
    /* Выбранный элемент списка результатов на экране */
    int data_process_selected = 0;
    /* Отступ с которым отображается список результатов на экране */
    int data_process_offset = 0;
    
    /* Поле для поиска по цеху */
    wchar_t data_find_guild_field[16];
    int data_find_guild_index = 0;
    
    /* Поле для поиска по имени */
    wchar_t data_find_name_field[16];
    int data_find_name_index = 0;
    
    /* Окна меню, данных и сообщений */
    WINDOW* menu_win;
    WINDOW* data_win;
    WINDOW* message_win;
    
    /* Панели меню и сообщений */
    PANEL* manu_pan;
    PANEL* message_pan;
    
    /* Строковые значения пунктов меню */
    const wchar_t* menu_index_options[] = { 
        L"Добавление",
        L"Сортировка",
        L"Поиск",
        L"Изменение",
        L"Удаление",
        L"Обработка",
        L"Очистка",
        L"Сохранение",
        L"Загрузка",
        L"Выход",
    };
    const wchar_t* menu_list_options[] = {
        L"← Назад",
        L"Изменение",
        L"Удаление",
    };
    const wchar_t* menu_find_options[] = { 
        L"← Назад",
        L"По имени",
        L"По номеру цеха",
    };
    const wchar_t* menu_sort_options[] = { 
        L"← Назад",
        L"По > ID",
        L"По < ID",
        L"По > имени",
        L"По < имени",
        L"По > профессии",
        L"По < профессии",
        L"По > зарплате",
        L"По < зарплате",
    };
    const wchar_t* menu_save_options[] = { 
        L"← Назад",
        L"В текстовый",
        L"В бинарный",
    };
    const wchar_t* menu_read_options[] = { 
        L"← Назад",
        L"Из текстового",
        L"Из бинарного",
    };
    const wchar_t* menu_process_options[] = { 
        L"← Назад",
        L"Сохранить",
    };
    const wchar_t* menu_home_options[] = { 
        L"← Назад",
    };
    /* Содержимое меню: главное окно */
    const menu_t menu_index = {
        .options = menu_index_options,
        .length  = 10,
        .key     = key_menu_index
    };
    /* Содержимое меню: частного списка */
    const menu_t menu_list = {
        .options = menu_list_options,
        .length  = 3,
        .key     = key_menu_list
    };
    /* Содержимое меню: поиска */
    const menu_t menu_find = {
        .options = menu_find_options,
        .length  = 3,
        .key     = key_menu_find
    };
    /* Содержимое меню: сортировки */
    const menu_t menu_sort = {
        .options = menu_sort_options,
        .length  = 9,
        .key     = key_menu_sort
    };
    /* Содержимое меню: сохранения */
    const menu_t menu_save = {
        .options = menu_save_options,
        .length = 3,
        .key = key_menu_save
    };
    /* Содержимое меню: загрузки */
    const menu_t menu_read = {
        .options = menu_read_options,
        .length  = 3,
        .key     = key_menu_read
    };
    /* Содержимое меню: обработка */
    const menu_t menu_process = {
        .options = menu_process_options,
        .length  = 2,
        .key     = key_menu_process
    };
    /* Содержимое меню: возврата */
    const menu_t menu_home = {
        .options = menu_home_options,
        .length  = 1,
        .key     = key_menu_home
    };
    /* Текущие содержимое меню */
    menu_t menu_current = menu_index;
    /* Текущий выбранный пункт в меню */
    int menu_selected = 0;
    
    /* Содержимое окна данных: список элементов */
    const data_t data_list = {
        .draw = draw_data_list,
        .key  = key_data_list
    };
    /* Содержимое окна данных: результирующая таблица */
    const data_t data_process = {
        .draw = draw_data_process,
        .key  = key_data_process
    };
    /* Содержимое окна данных: форма поиска по цеху */
    const data_t data_find_guild = {
        .draw = draw_data_find_guild,
        .key = key_data_find_guild
    };
    /* Содержимое окна данных: форма поиска по имени */
    const data_t data_find_name = {
        .draw = draw_data_find_name,
        .key  = key_data_find_name
    };
    /* Содержимое окна данных: форма ввода элемента */
    const data_t data_form = {
        .draw = draw_data_form,
        .key  = key_data_form
    };
    /* Текущие содержимое окна данных */
    data_t data_current = data_list;
    
    /* Содержимое сообщения: файл не найден */
    const message_t message_nofile = {
        .title = L"Ошибка",
        .text  = L"Файл не найден"
    };
    /* Содержимое сообщения: запись не возможна */
    const message_t message_cantwrite = {
        .title = L"Ошибка",
        .text  = L"Данные не были сохранены"
    };
    /* Содержимое сообщения: недостаточно памяти */
    const message_t message_nomemory = {
        .title = L"Ошибка",
        .text  = L"Недостаточно памяти"
    };
    /* Содержимое сообщения: искомый элемент не найтен */
    const message_t message_noelement = {
        .title = L"Ошибка",
        .text  = L"Элемент не найден"
    };
    /* Содержимое сообщения: не найдено ни одного элемента по этому запросу */
    const message_t message_nothingfound = {
        .title = L"Ошибка",
        .text  = L"Ничего не найдено"
    };
    /* Содержимое сообщения: автор программы */
    const message_t message_author = {
        .title = L"Курсовой проект по ОПиАЯ",
        .text  = L"Автор: Куркчи Ариф ИСб-21"
    };
    /* Текущие содержимое сообщения */
    message_t message_current;
    
    /* Текущая форма ввода */
    form::form_t* form_current;
    
    /* Текущий результирующий список */
    list::result_t* data_process_data = NULL;
    /* Текущий размер результирующего списка */
    int data_process_length = 0;
    
    /* Настройка основных параметров экрана */
    void init_display() {
        initscr();
        clear();
        noecho();
        cbreak();
        keypad(stdscr,true);
        start_color();
        init_pair(1,COLOR_GREEN,COLOR_BLACK);
        init_pair(2,COLOR_BLACK,COLOR_WHITE);
        
        /* Создание окон */
        menu_win    = newwin(menu_height,   menu_width,   0,                      0);
        data_win    = newwin(data_height,   data_width,   0,                      0);
        message_win = newwin(message_height,message_width,(25-message_height)/2-1,(80-message_width)/2-1);
        
        /* Создание панелей */
        manu_pan    = new_panel(menu_win);
        message_pan = new_panel(message_win);
        
        /* Скрыть изначально панель сообщений */
        hide_panel(message_pan);
        
        wcscpy(data_find_guild_field,L"");
        wcscpy(data_find_name_field, L"");
        
        form_current = form::full_form;
    }
    
    /* Очистка и выключение экрана */
    void close_display() {
        clear();
        refresh();
        endwin();
    }
    
    /* Возврат к главному экрану */
    void go_home() {
        switch_menu(menu_index,0);
        switch_data(data_list);
    }
    
    /* Отрисовка экрана */
    void draw() {
        if(!message_hidden) {
            draw_message();
        } else {
            /* Проверка необходимости перерисовки данных */
            if(menu_hidden || redraw_data != 0) {
                if(redraw_data < 0) {
                    wclear(data_win);
                    redraw_data++;
                } else {
                    redraw_data--;
                }
                draw_data();
            }
            if(!menu_hidden) {
                draw_menu();
            }
        }
        
        /* Передвижение курсора в нужное положение */
        move(cursor_y,cursor_x);
        
        refresh();
    }
    
    /* Отрисовка меню */
    void draw_menu() {
        wbkgd(menu_win,COLOR_PAIR(1));
        
        /* Отрисовка верхней линии */
        wattron(menu_win,COLOR_PAIR(1));
        for(int i=0;i<=menu_width-2;i++) {
            mvwadd_wch(menu_win,0,i,WACS_HLINE);
        }
        /* Отрисовка боковой линии */
        mvwadd_wch(menu_win,0,menu_width-1,WACS_URCORNER);
        for(int i=1;i<=menu_height-2;i++) {
            mvwadd_wch(menu_win,i,menu_width-1,WACS_VLINE);
        }
        /* Отрисовка нижней линии */
        mvwadd_wch(menu_win,menu_height-1,menu_width-1,WACS_LRCORNER);
        for(int i=0;i<=menu_width-2;i++) {
            mvwadd_wch(menu_win,menu_height-1,i,WACS_HLINE);
        }
        
        int y = 1;
        wchar_t tmp[17];
        /* Отрисовка пунктов меню */
        for(int i=0;i<menu_current.length;++i) {
            swprintf(tmp,17,L" %-14S ",menu_current.options[i]);
            if(menu_selected == i) {	
                wattron(menu_win,A_REVERSE);
                mvwprintw(menu_win,y,0,"%S",tmp);
                wattroff(menu_win,A_REVERSE);
            } else {
                mvwprintw(menu_win,y,0,"%S",tmp);
            }
            y += 2;
        }
        wattroff(menu_win,COLOR_PAIR(1));
        
        /* Отрисовка вспомогательной информации */
        mvwprintw(menu_win,22,2,"%S",L"Вкл/Выкл меню");
        wattron(menu_win,COLOR_PAIR(2));
        mvwprintw(menu_win,23,6,"%S",L"TAB");
        wattroff(menu_win,COLOR_PAIR(2));
        
        wrefresh(menu_win);
    }
    
    /* Отрисовка данных */
    void draw_data() {
        /* Делегация отрисовки меню */
        data_current.draw();
        
        /* Отрисовка указателя на раскрытие меню */
        if(menu_hidden) {
            wattron(data_win,COLOR_PAIR(2));
            mvwprintw(data_win,1,0,"%S",L"→");
            mvwprintw(data_win,2,0,"%S",L"T");
            mvwprintw(data_win,3,0,"%S",L"A");
            mvwprintw(data_win,4,0,"%S",L"B");
            wattroff(data_win,COLOR_PAIR(2));
        }
        
        wrefresh(data_win);
    }
    
    /* Показать/скрыть меню */
    void toggle_menu() {
        if(menu_hidden) {
            show_panel(manu_pan);
        } else {
            hide_panel(manu_pan);
        }
        menu_hidden = !menu_hidden;
        update_panels();
        doupdate();
    }
    
    /* Переключение отображаемое меню */
    void switch_menu(menu_t menu,int selection) {
        wclear(menu_win);
        menu_selected = selection;
        menu_current = menu;
        
        redraw_data = 1;
    }
    
    /* Переключение отображаемых данных */
    void switch_data(data_t data) {
        wclear(data_win);
        data_current = data;
        
        redraw_data = 1;
    }
    
    /* Отображение сообщения */
    void show_message(message_t m) {
        message_current = m;
        message_hidden = false;
        show_panel(message_pan);
    }
    
    /* Обработка нажатий клавиш */
    bool key() {
        cursor_x = 79;
        cursor_y = 24;
        wint_t wc;
        wget_wch(stdscr,&wc);
        /* Проверка нажатия любой клавиши в сообщении */
        if(!message_hidden) {
            message_hidden = true;
            hide_panel(message_pan);
            
            draw_data();
            draw_menu();
            refresh();
            return true;
        }
        
        switch(wc) {
            case KEY_HELP:
            case KEY_F(1): // Помощь
                show_message(message_author);
                redraw_data++;
                break;
            case L'\t': // Показать/скрыть меню
                toggle_menu();
                break;
            default: // Делегация обработки клавиши обработчикам
                if(!menu_hidden) {
                    return key_menu(wc);
                } else {
                    return key_data(wc);
                }
                break;
        }
        return true;
    }
    
    /* Обработка клавиши в меню */
    bool key_menu(int c) {
        switch(c) {
            case KEY_UP: // Вверх
                if(menu_selected == 0) {
                    menu_selected = menu_current.length-1;
                } else { 
                    menu_selected--;
                }
                break;
            case KEY_DOWN: // Вниз
                if(menu_selected == menu_current.length-1) {
                    menu_selected = 0;
                } else { 
                    menu_selected++;
                }
                break;
            case KEY_ENTER: // Делегация обработки пункта
            case '\n':
                return menu_current.key(menu_selected);
                break;
        }
        return true;
    }
    
    /* Обработка пункта в главном меню */
    bool key_menu_index(int c) {
        list::node_t* tmp;
        switch(c) {
            case 0: // Добавление
                switch_menu(menu_home,0);
                switch_data(data_form);
                form::clear(form_current);
                form_current->submit = form::add_submit;
                toggle_menu();
                break;
            case 1: // Соритровки
                switch_menu(menu_sort,1);
                break;
            case 2: // Поиск
                if(!list::global.length) {
                    show_message(message_noelement);
                    break;
                }
                switch_menu(menu_find,1);
                break;
            case 3: // Изменение
                tmp = list::get(*list::current,data_list_selected);
                if(tmp == NULL) {
                    show_message(message_noelement);
                    break;
                }
                switch_menu(menu_home,0);
                switch_data(data_form);
                /* Заполнение формы данными */
                form::clear(form_current);
                form_current->field[0]->data = list::itow(tmp->data.id,form_current->field[0]->length);
                wcscpy(form_current->field[1]->data,tmp->data.name);
                form_current->field[2]->data = list::itow(tmp->data.b_year,form_current->field[2]->length);
                swprintf(form_current->field[3]->data,form_current->field[3]->length+1,L"%C",tmp->data.sex);
                wcscpy(form_current->field[4]->data,tmp->data.prof);
                form_current->field[5]->data = list::itow(tmp->data.exp,form_current->field[5]->length);
                form_current->field[6]->data = list::itow(tmp->data.rank,form_current->field[6]->length);
                form_current->field[7]->data = list::itow(tmp->data.guild,form_current->field[7]->length);
                form_current->field[8]->data = list::itow(tmp->data.sect,form_current->field[8]->length);
                form_current->field[9]->data = list::ftow(L"%.2f",tmp->data.salary,form_current->field[9]->length);
                
                form_current->field[0]->index = wcslen(form_current->field[0]->data);
                form_current->field[1]->index = wcslen(form_current->field[1]->data);
                form_current->field[2]->index = wcslen(form_current->field[2]->data);
                form_current->field[3]->index = wcslen(form_current->field[3]->data);
                form_current->field[4]->index = wcslen(form_current->field[4]->data);
                form_current->field[5]->index = wcslen(form_current->field[5]->data);
                form_current->field[6]->index = wcslen(form_current->field[6]->data);
                form_current->field[7]->index = wcslen(form_current->field[7]->data);
                form_current->field[8]->index = wcslen(form_current->field[8]->data);
                form_current->field[9]->index = wcslen(form_current->field[9]->data);
                form_current->submit = form::edit_submit;
                form::current_el = tmp->index;
                toggle_menu();
                break;
            case 4: // Удаление
                tmp = list::get(list::global,data_list_selected);
                if(tmp == NULL) {
                    show_message(message_noelement);
                    break;
                }
                if(!list::erase(list::global,tmp->index)) {
                    show_message(message_noelement);
                }
                if(data_list_selected >= list::global.length) {
                    data_list_selected--;
                    if(data_list_selected < data_list_offset) {
                        data_list_offset--;
                    }
                }
                redraw_data = -1;
                break;
            case 5: // Обработка
                if(data_process_data != NULL) {
                    delete [] data_process_data;
                }
                data_process_data = list::process(list::global,data_process_length);
                data_process_offset = 0;
                data_process_selected = 0;
                switch_menu(menu_process,1);
                switch_data(data_process);
                toggle_menu();
                break;
            case 6: // Очистка
                list::free(list::global);
                redraw_data = -1;
                break;
            case 7: // Сохранение
                switch_menu(menu_save,1);
                break;
            case 8: // Загрузка
                switch_menu(menu_read,1);
                break;
            case 9: // Выход
                return false;
                break;
        }
        return true;
    }
    
    /* Обработка пункта в отображении списка */
    bool key_menu_list(int c) {
        list::node_t* tmp;
        switch(c) {
            case 1: // Изменить
                tmp = list::get(*list::current,data_list_selected);
                if(tmp == NULL) {
                    show_message(message_noelement);
                    break;
                }
                switch_menu(menu_home,0);
                switch_data(data_form);
                /* Заполнение формы данными */
                form::clear(form_current);
                form_current->field[0]->data = list::itow(tmp->data.id,form_current->field[0]->length);
                wcscpy(form_current->field[1]->data,tmp->data.name);
                form_current->field[2]->data = list::itow(tmp->data.b_year,form_current->field[2]->length);
                swprintf(form_current->field[3]->data,form_current->field[3]->length+1,L"%C",tmp->data.sex);
                wcscpy(form_current->field[4]->data,tmp->data.prof);
                form_current->field[5]->data = list::itow(tmp->data.exp,form_current->field[5]->length);
                form_current->field[6]->data = list::itow(tmp->data.rank,form_current->field[6]->length);
                form_current->field[7]->data = list::itow(tmp->data.guild,form_current->field[7]->length);
                form_current->field[8]->data = list::itow(tmp->data.sect,form_current->field[8]->length);
                form_current->field[9]->data = list::ftow(L"%.2f",tmp->data.salary,form_current->field[9]->length);
                
                form_current->field[0]->index = wcslen(form_current->field[0]->data);
                form_current->field[1]->index = wcslen(form_current->field[1]->data);
                form_current->field[2]->index = wcslen(form_current->field[2]->data);
                form_current->field[3]->index = wcslen(form_current->field[3]->data);
                form_current->field[4]->index = wcslen(form_current->field[4]->data);
                form_current->field[5]->index = wcslen(form_current->field[5]->data);
                form_current->field[6]->index = wcslen(form_current->field[6]->data);
                form_current->field[7]->index = wcslen(form_current->field[7]->data);
                form_current->field[8]->index = wcslen(form_current->field[8]->data);
                form_current->field[9]->index = wcslen(form_current->field[9]->data);
                form_current->submit = form::edit_submit;
                form::current_el = tmp->index;
                toggle_menu();
                break;
            case 2: // Удалить
                list::node_t* tmp = list::get(*list::current,data_list_selected);
                if(tmp == NULL) {
                    show_message(message_noelement);
                    break;
                }
                if(!list::erase(list::global,tmp->index)) {
                    show_message(message_noelement);
                }
                redraw_data = -1;
                break;
        }
        list::free(*list::current);
        list::current = &list::global;
        switch_menu(menu_index,0);
        return true;
    }
    
    /* Обработка пункта в меню поиска */
    bool key_menu_find(int c) {
        switch(c) {
            case 1: // По названию
                switch_data(data_find_name);
                switch_menu(menu_home,0);
                toggle_menu();
                break;
            case 2: // По номеру цеха
                switch_data(data_find_guild);
                switch_menu(menu_home,0);
                toggle_menu();
                break;
            default:
                switch_menu(menu_index,2);
                break;
        }
        return true;
    }
    
    /* Обработка пункта в меню сотритовки */
    bool key_menu_sort(int c) {
        switch(c) {
            case 1: // По > ID
                list::sort(list::global,list::compare_by_id,false);
                break;
            case 2: // По < ID
                list::sort(list::global,list::compare_by_id,true);
                break;
            case 3: // По > имени
                list::sort(list::global,list::compare_by_name,false);
                break;
            case 4: // По < имени
                list::sort(list::global,list::compare_by_name,true);
                break;
            case 5: // По > профессии
                list::sort(list::global,list::compare_by_prof,false);
                break;
            case 6: // По < профессии
                list::sort(list::global,list::compare_by_prof,true);
                break;
            case 7: // По > зарплате
                list::sort(list::global,list::compare_by_salary,false);
                break;
            case 8: // По < зарплате
                list::sort(list::global,list::compare_by_salary,true);
                break;
        }
        data_list_selected = 0;
        data_list_offset = 0;
        switch_menu(menu_index,1);
        return true;
    }
    
    /* Обработка пункта в меню сохранения */
    bool key_menu_save(int c) {
        switch(c) {
            case 1: // В текстовый
                if(!list::save(list::global,"data.txt",false)) {
                    show_message(message_cantwrite);
                }
                break;
            case 2: // В бинарный
                if(!list::save(list::global,"data.bin",true)) {
                    show_message(message_cantwrite);
                }
                break;
        }
        switch_menu(menu_index,0);
        return true;
    }
    
    /* Обработка пункта в меню загрузки */
    bool key_menu_read(int c) {
        switch(c) {
            case 1: // Из текстового
                list::free(list::global);
                if(!list::read(list::global,"data.txt",false)) {
                    show_message(message_nofile);
                }
                data_list_selected = 0;
                data_list_offset = 0;
                break;
            case 2: // Из бинарного
                list::free(list::global);
                if(!list::read(list::global,"data.bin",true)) {
                    show_message(message_nofile);
                }
                data_list_selected = 0;
                data_list_offset = 0;
                break;
        }
        switch_menu(menu_index,0);
        return true;
    }
    
    /* Обработка пункта в меню выходной таблицы */
    bool key_menu_process(int c) {
        switch(c) {
            case 0: // Назад
                go_home();
                break;
            case 1: // Сохранить
                if(!list::save(data_process_data,data_process_length,"result.txt")) {
                    show_message(message_cantwrite);
                }
                break;
        }
        return true;
    }
    
    /* Обработка пункта в меню возврата на главную */
    bool key_menu_home(int c) {
        switch_menu(menu_index,0);
        switch_data(data_list);
        data_list_selected = 0;
        data_list_offset = 0;
        return true;
    }
    
    /* Обработка клавиши в окне данных */
    bool key_data(int c) {
        switch(c) {
            default:
                /* Делегация обработки клавиши */
                return data_current.key(c);
                break;
        }
        return true;
    }
    
    /* Обработка клавиши в данных списка */
    bool key_data_list(int c) {
        switch(c) {
            case KEY_UP: // Вверх
                if(data_list_selected != 0) {
                    data_list_selected--;
                }
                break;
            case KEY_DOWN: // Вниз
                if(data_list_selected < list::current->length-1) {
                    data_list_selected++;
                }
                break;
            default:
                break;
        }
        if(data_list_selected < data_list_offset) {
            data_list_offset--;
        }
        if(data_list_selected > data_list_offset+10) {
            data_list_offset++;
        }
        return true;
    }
    
    /* Обработка клавиши в данных выходной таблицы */
    bool key_data_process(int c) {
        switch(c) {
            case KEY_UP: // Вверх
                if(data_process_selected != 0) {
                    data_process_selected--;
                }
                break;
            case KEY_DOWN: // Вниз
                if(data_process_selected < data_process_length-1) {
                    data_process_selected++;
                }
                break;
            default:
                break;
        }
        if(data_process_selected < data_process_offset) {
            data_process_offset--;
        }
        if(data_process_selected > data_process_offset+9) {
            data_process_offset++;
        }
        return true;
    }
    
    /* Обработка клавиши в данных поиска по цеху */
    bool key_data_find_guild(int c) {
        list::data_t to_find;
        to_find.id = -1;
        wcscpy(to_find.name,L"");
        to_find.b_year = -1;
        to_find.sex = L'\0';
        wcscpy(to_find.prof,L"");
        to_find.exp = -1;
        to_find.rank = -1;
        to_find.guild = -1;
        to_find.sect = -1;
        to_find.salary = -1;
        switch(c) {
            case KEY_BACKSPACE: // Стереть
            case 127:
                if(data_find_guild_index != 0) {
                    data_find_guild_field[--data_find_guild_index] = L'\0';
                }
                break;
            case KEY_ENTER: // Найти
            case L'\n':
                to_find.guild = list::wtoi(data_find_guild_field);
                list::current = list::find(list::global,to_find);
                if(!list::current->length) {
                    show_message(message_nothingfound);
                    list::current = &list::global;
                    switch_menu(menu_index,0);
                } else {
                    switch_menu(menu_list,1);
                }
                switch_data(data_list);
                if(menu_hidden) {
                    toggle_menu();
                }
                break;
            default: // Ввод символа
                if(data_find_guild_index < 9 && (c >= L'0' && c <= L'9')) {
                    data_find_guild_field[data_find_guild_index++] = c;
                    data_find_guild_field[data_find_guild_index] = L'\0';
                }
                break;
        }
        return true;
    }
    
    /* Обработка клавиши в данных поиска по имени */
    bool key_data_find_name(int c) {
        list::data_t to_find;
        to_find.id = -1;
        wcscpy(to_find.name,L"");
        to_find.b_year = -1;
        to_find.sex = L'\0';
        wcscpy(to_find.prof,L"");
        to_find.exp = -1;
        to_find.rank = -1;
        to_find.guild = -1;
        to_find.sect = -1;
        to_find.salary = -1;
        switch(c) {
            case KEY_BACKSPACE: // Стереть
            case 127:
                if(data_find_name_index != 0) {
                    data_find_name_field[--data_find_name_index] = L'\0';
                }
                break;
            case KEY_ENTER: // Найти
            case L'\n':
                wcscpy(to_find.name,data_find_name_field);
                list::current = list::find(list::global,to_find);
                if(!list::current->length) {
                    show_message(message_nothingfound);
                    list::current = &list::global;
                    switch_menu(menu_index,0);
                } else {
                    switch_menu(menu_list,1);
                }
                switch_data(data_list);
                if(menu_hidden) {
                    toggle_menu();
                }
                break;
            default: // Ввод символа
                if(data_find_name_index < 17 && (
                        (c >= L'0' && c <= L'9') ||
                        (c >= L'a' && c <= L'z') ||
                        (c >= L'A' && c <= L'Z') ||
                        (c >= L'а' && c <= L'я') ||
                        (c >= L'А' && c <= L'Я') ||
                        (c == L'ё' || c == L'Ё') ||
                        (c == L' ' || c == L'-' || c == L'_')
                        )) {
                    data_find_name_field[data_find_name_index++] = c;
                    data_find_name_field[data_find_name_index] = L'\0';
                }
                break;
        }
        return true;
    }
    
    /* Обработка клавиши в данных формы */
    bool key_data_form(int c) {
        /* Делегация клавиши в форму */
        form::key(form_current,c);
        return true;
    }
    
    /* Отрисовка списка данных */
    void draw_data_list() {
        /* Заголовок */
        mvwprintw(data_win,0,0,"%S",L"┌───┬────────────────────┬────┬───┬──────────┬────┬──────┬───┬───────┬─────────┐");
        mvwprintw(data_win,1,0,"%S",L"│ ID│                 Имя│ Год│Пол│ Профессия│Опыт│Разряд│Цех│Участок│ Зарплата│");
        
        int y = 2;
        
        /* Отрисовка строки */
        list::node_t* ptr = list::get(*list::current,data_list_offset);
        while(ptr != NULL && y < 25) {
            mvwprintw(data_win,y,0,"%S",L"├───┼────────────────────┼────┼───┼──────────┼────┼──────┼───┼───────┼─────────┤");
            wchar_t tmp[81];
            swprintf(tmp,81,L"│%3d│%20S│%4d│%3C│%10S│%4d│%6d│%3d│%7d│%9.2f│\n",
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
            y++;
            if(data_list_offset + (y-1)/2 - 1 == data_list_selected) {
                wattron(data_win,COLOR_PAIR(2));
                mvwprintw(data_win,y,0,"%S",tmp);
                wattroff(data_win,COLOR_PAIR(2));
            } else {
                mvwprintw(data_win,y,0,"%S",tmp);
            }
            y++;
            
            ptr = ptr->next;
        }
        
        mvwprintw(data_win,y,0,"%S",L"└───┴────────────────────┴────┴───┴──────────┴────┴──────┴───┴───────┴─────────┘");
    }
    
    /* Отрисовка выходной таблицы */
    void draw_data_process() {
        wchar_t line[81];
        /* Заголовок */
        mvwprintw(data_win,0,0,"%S",L"┌───┬─────────────────────────────────────────────────────────────────┬───────┐");
        mvwprintw(data_win,1,0,"%S",L"│   │                 Количество рабочих по стажу лет                 │       │");
        mvwprintw(data_win,2,0,"%S",L"│Цех├──────────┬──────────┬──────────┬──────────┬──────────┬──────────┤ Всего │");
        mvwprintw(data_win,3,0,"%S",L"│   │   До 6   │   6-10   │   11-15  │   16-20  │   21-25  │ Свыше 26 │       │");
        int y = 4;
        
        /* Отрисовка строки */
        int i = data_process_offset;
        while(i < data_process_length && y < 25) {
            mvwprintw(data_win,y,0,"%S",L"├───┼──────────┼──────────┼──────────┼──────────┼──────────┼──────────┼───────┤");
            y++;
            
            swprintf(line,81,L"│%3d│%10d│%10d│%10d│%10d│%10d│%10d│%7d│",
                        data_process_data[i].guild,
                        data_process_data[i].cat1,
                        data_process_data[i].cat2,
                        data_process_data[i].cat3,
                        data_process_data[i].cat4,
                        data_process_data[i].cat5,
                        data_process_data[i].cat6,
                        data_process_data[i].total);
            if(data_process_offset + (y-3)/2 - 1 == data_process_selected) {
                wattron(data_win,COLOR_PAIR(2));
                mvwprintw(data_win,y,0,"%S",line);
                wattroff(data_win,COLOR_PAIR(2));
            } else {
                mvwprintw(data_win,y,0,"%S",line);
            }
            y++;
            
            i++;
        }
        
        mvwprintw(data_win,y,0,"%S",L"└───┴──────────┴──────────┴──────────┴──────────┴──────────┴──────────┴───────┘");
    }
    
    /* Отрисовка данных поиска по цеху */
    void draw_data_find_guild() {
        mvwprintw(data_win,11,30,"%S",L"Номер цеха для поиска");
        mvwprintw(data_win,12,30,"%S",data_find_guild_field);
        /* Перемещение курсора */
        cursor_x = 30+data_find_guild_index;
        cursor_y = 12;
    }
    
    /* Отрисовка данных поиска по имени */
    void draw_data_find_name() {
        mvwprintw(data_win,11,30,"%S",L"Имя для поиска");
        mvwprintw(data_win,12,30,"%S",data_find_name_field);
        /* Перемещение курсора */
        cursor_x = 30+data_find_name_index;
        cursor_y = 12;
    }
    
    /* Отрисовка данных формы */
    void draw_data_form() {
        form::form_t* form = form_current;
        form::field_t* field;
        for(int i=0;i<form->length;i++) {
            /* Отрисовка поля */
            field = form->field[i];
            wattron(data_win,COLOR_PAIR(1));
            mvwprintw(data_win,field->y,field->x,"%S",field->name);
            wattron(data_win,A_REVERSE);
            for(int j=0;j<field->length;j++) {
                mvwprintw(data_win,field->y+1,field->x+j," ");
            }
            mvwprintw(data_win,field->y+1,field->x,"%S",field->data);
            wattroff(data_win,A_REVERSE);
            wattroff(data_win,COLOR_PAIR(1));
        }
        
        /* Перемещение курсора */
        cursor_y = form->field[form->current_field]->y + 1;
        cursor_x = form->field[form->current_field]->x + form->field[form->current_field]->index;
    }
    
    /* Отрисовка сообщений */
    void draw_message() {
        wbkgd(message_win,COLOR_PAIR(2));
        
        wattron(message_win,COLOR_PAIR(2));
        mvwprintw(message_win,0,0,"%S",L"╔════════════════════════════════════════════════╗");
        mvwprintw(message_win,1,0,"%S",L"║                                                ║");
        mvwprintw(message_win,2,0,"%S",L"╠════════════════════════════════════════════════╣");
        mvwprintw(message_win,3,0,"%S",L"║                                                ║");
        mvwprintw(message_win,4,0,"%S",L"║                                                ║");
        mvwprintw(message_win,5,0,"%S",L"║                                                ║");
        mvwprintw(message_win,6,0,"%S",L"╚════════════════════════════════════════════════╝");
        int l = wcslen(message_current.title);
        mvwprintw(message_win,1,(50-l)/2-1,"%S",message_current.title);
        l = wcslen(message_current.text);
        mvwprintw(message_win,4,(50-l)/2-1,"%S",message_current.text);
        mvwprintw(message_win,5,7,"%S",L"Для закрытия нажмите любую клавишу");
        wattroff(message_win,COLOR_PAIR(2));
        
        wrefresh(message_win);
    }
    
}