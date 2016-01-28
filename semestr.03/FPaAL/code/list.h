#ifndef LIST_H
#define	LIST_H

#define DATA_NAME_LEN 20
#define DATA_PROF_LEN 10

namespace list {
    
    /* Элемент сводной таблицы */
    typedef struct result_t {
        /* Номер цеха */
        int guild;
        /* До 6 */
        int cat1;
        /* 6-10 */
        int cat2;
        /* 11-15*/
        int cat3;
        /* 16-20 */
        int cat4;
        /* 21-25 */
        int cat5;
        /* Свыше 25 */
        int cat6;
        /* Всего */
        int total;
    } result_t;
    
    /* Блок данных */
    typedef struct data_t {
        /* Табельный номер */
        int     id;
        /* ФИО */
        wchar_t    name    [DATA_NAME_LEN+1];
        /* Год рождения */
        int     b_year;
        /* Пол */
        wchar_t   sex;
        /* Профессия */
        wchar_t    prof    [DATA_PROF_LEN+1];
        /* Стаж/опыт работы */
        int     exp;
        /* Разряд */
        int     rank;
        /* Номер цеха */
        int     guild;
        /* Номер участка */
        int     sect;
        /* Зарплата */
        float   salary;
    } data_t;
    
    /* Элемент списка */
    typedef struct node_t {
        /* Индекс */
        int index;
        /* Данные */
        data_t data;
        /* Указатель на предыдущий элемент */
        node_t* prev;
        /* Указатель на следующий элемент */
        node_t* next;
    } node_t;
    
    /* Список */
    typedef struct list_t {
        /* Голова списка */
        node_t* head;
        /* Хвост списка */
        node_t* tail;
        /* Количество элементов */
        int length;
    } list_t;
    
    extern list_t global;
    extern list_t* current;
    
    /*
     * Создаёт пустой список
     * 
     * @return list_t созданный список
     */
    list_t create();
    
    /*
     * Добавляет элемент в конец списка
     * 
     * @param list_t& список в который добавлять
     * @param const data_t& данные которые добавлять
     * 
     * @return bool успешно ли добавилось
     */
    bool push_back(list_t&,const data_t&);

    /*
     * Изменяет данные в элементе списка
     * 
     * @param list_t& список в котором изменять
     * @param const int индекс по которому изменять
     * @param const data_t& данные для изменения
     * 
     * @return bool успешно ли изменилось
     */
    bool update(list_t&,const int,const data_t&);

    /*
     * Удаляет элемент списка
     * 
     * @param list_t& список в котором удалять
     * @param const int индекс который удалять
     * 
     * @return bool успешно ли удалилось
     */
    bool erase(list_t&,const int);

    /*
     * Возвращает элемент списка
     * 
     * @param list_t& список в котором найти
     * @param const int индекс который найти
     * 
     * @return node_t* элемент по индексу (или NULL если его нет)
     */
    node_t* get(list_t&,const int);

    /*
     * Ищет элемент в списке
     * 
     * @param list_t& список в котором найти
     * @param const data_t& данные с которыми сравнивать
     * 
     * @return list_t* найденные элементы
     */
    list_t* find(list_t&,const data_t&);
    
    /*
     * Сортирует список
     * 
     * @param list_t& список который сортировать
     * @param bool (*)(data_t&,data_t&) компаратор
     * 
     * @return void
     */
    void sort(list_t&,bool (*)(data_t&,data_t&),bool = false);
    
    /*
     * Компаратор по табельному номеру
     * 
     * @param data_t& первый элемент
     * @param data_t& второй элемент
     * 
     * @return bool результат сравнения
     */
    bool compare_by_id(data_t&,data_t&);
    
    /*
     * Компаратор по имени
     * 
     * @param data_t& первый элемент
     * @param data_t& второй элемент
     * 
     * @return bool результат сравнения
     */
    bool compare_by_name(data_t&,data_t&);
    
    /*
     * Компаратор по професси
     * 
     * @param data_t& первый элемент
     * @param data_t& второй элемент
     * 
     * @return bool результат сравнения
     */
    bool compare_by_prof(data_t&,data_t&);
    
    /*
     * Компаратор по зарплате
     * 
     * @param data_t& первый элемент
     * @param data_t& второй элемент
     * 
     * @return bool результат сравнения
     */
    bool compare_by_salary(data_t&,data_t&);
    
    /*
     * Считывает список из файла
     * 
     * @param list_t& список в который считывать данные
     * @param const char* имя файла
     * @param bool бинарный ли файл
     * 
     * @return bool успешно ли считывание
     */
    bool read(list_t&,const char*,bool);
    
    /*
     * Сохраняет список в файл
     * 
     * @param list_t& список который сохранять
     * @param const char* имя файла
     * @param bool бинарный ли файл
     * 
     * @return bool успешна ли запись
     */
    bool save(list_t&,const char*,bool);

    /*
     * Сохраняет сводную таблицу в файл
     * 
     * @param result_t* сводная таблица которую сохранять
     * @param const int длина сводной таблицы
     * @param const char* имя файла
     * 
     * @return bool успешна ли запись
     */
    bool save(result_t*,const int,const char*);
    
    /*
     * Глубоко копирует элемент
     * 
     * @param const data_t& данные которые копировать
     * 
     * @return data_t скопированные данные
     */
    data_t copy(const data_t&);
    
    /*
     * Сравнивает элементы по всем полям
     * 
     * @param const data_t& первый элемент
     * @param const data_t& второй элемент
     * 
     * @return bool похожи ли элементы
     */
    bool compare(const data_t&,const data_t&);
    
    /*
     * Освобождает память от списка
     * 
     * @param const list_t& список для очистки
     * 
     * @return void
     */
    void free(list_t&);
    
    /*
     * Создаёт сводную таблицу
     * 
     * @param list_t& список для обработки
     * @param int& длина сводной таблицы
     * 
     * @return result_t* сводная таблица
     */
    result_t* process(list_t&,int&);
    
    /*
     * Генерирует список из имён
     * 
     * @param const char* имя файла с именами
     * 
     * @return list_t созданный файл
     */
    list_t generate(const char*);
    
    /*
     * Удаляет лидирующие пробелы
     * 
     * @param wchar_t* строка которую очистить от пробелов
     * 
     * @return void
     */
    void erase_spaces(wchar_t*);
    
    /*
     * Преобразует строку в целое число
     * 
     * @param const wchar_t* числовая строка
     * 
     * @return int число
     */
    int wtoi(const wchar_t*);
    
    /*
     * Преобразует целое число в строку
     * 
     * @param int число
     * @param int максимальная длина
     * 
     * @return const wchar_t* числовая строка
     */
    wchar_t* itow(const int,const int);
    
    /*
     * Преобразует строку в число с плавающей точкой
     * 
     * @param const wchar_t* числовая строка
     * 
     * @return число с плавающей точкой
     */
    float wtof(const wchar_t*);
    
    /*
     * Преобразует число с плавающей точкой в строку
     * 
     * @param const wchar_t* формат
     * @param float число
     * @param int максимальная длина
     * 
     * @return const wchar_t* числовая строка
     */
    wchar_t* ftow(const wchar_t*,const float,const int);
}

#endif	/* LIST_H */

