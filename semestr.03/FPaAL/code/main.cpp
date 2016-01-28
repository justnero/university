#include <stdlib.h>
#include <string.h>
#include <locale.h>
#include <unistd.h>
#include <signal.h>

#include "list.h"
#include "display.h"
#include "form.h"

int main(int argc,char **argv) {
    setlocale(LC_ALL,"");
    
    /* Блокировка сигналов */
    sigset_t mask;
    sigset_t orig_mask;
    sigfillset(&mask);
    sigaddset(&mask,SIGINT);
    if(sigprocmask(SIG_BLOCK,&mask,&orig_mask) < 0) {
        return -27;
    }
    
    /* Генерация списка по файлу имён из первого параметра */
    if(argc > 1) {
        list::list_t l = list::generate(argv[1]);
        list::save(l,"data.bin",true);
        list::save(l,"data.txt",false);
        list::free(l);
    }
    
    /* Инициализация форм */
    form::init_forms();
    /* Инициализация экрана */
    display::init_display();
    /* Отрисовка экрана */
    display::draw();
    /* Делегация клавиш и отрисовка экрана */
    do {
        display::draw();
    } while(display::key());
    /* Завершение рабоиы экрана */
    display::close_display();
    return 0;
}
