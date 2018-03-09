:-dynamic %информирует интерпретатор о том, что определения предикатов
%могут изменяться в ходе выполнения программы
время/2,
поезд/3, % формат: <имя предиката>/<кол-во аргументов>
сотрудник_ф/8.
% первоначальная база, загружаемая при запуске программы
% Пункт назначения, № поезда, врем отправления.
поезд('Москва', 101, время(14, 00)).
поезд('Казань', 102, время(12, 00)).
поезд('Казань', 103, время(13, 00)).
поезд('Москва', 103, время(23, 00)).
поезд('Симферополь', 104, время(11, 30)).
start:- menu. %предикат для запуска программы

%0============= отображение меню ==============================================
menu:-
repeat, nl,
write('*******************************'),nl,
write('* 1. Добавление записи в БД *'),nl,
write('* 2. Удаление записи из БД *'),nl,
write('* 3. Выборка записей из БД *'),nl,
write('* 4. Просмотр БД *'),nl,
write('* 5. Загрузка БД из файла *'),nl,
write('* 6. Сохранение БД в файле *'),nl,
write('* 7. Реляционные операции *'),nl,
write('* 8. Выход *'),nl,
write('*******************************'), nl ,nl,
write('Введите номер пункта меню с точкой в конце!!!'),nl,
read(C),nl, %Ввод пункта меню
proc(C), %Запуск процедуры с номером С
C=8, %Если С не равно 8, то авт. возврат к repeat
!. %Иначе успешное завершение
%0-----------------------------------------------------------------------------

%1======= добавление записи в базу данных =====================================
proc(1):-
write('Ввод завершайте точкой!!! :'),nl,
write('Введите пункт назначения:'),nl, read(Пункт),
write('Введите номер поезда:'),nl, read(N),
write('Введите час отправления:'),nl, read(Час),
write('Введите минуту отправления:'),nl, read(Мин),
assertz(поезд(Пункт,N,время(Час,Мин))), %добавление факта в БД
write('Поезд №'),write(N),write(' был добавлен в БД'),nl,
write('Введите любой символ'),nl, %ожидание ввода литеры
get0(_).
%1-----------------------------------------------------------------------------

%2========= удаление записи из базы данных ====================================
proc(2):-
write('Введите номер поезда для удаления'), nl,
read(N),
retract(поезд(_,N,_)), %удаление записи
write('Поезд №'),
write(N), tab(2), %вывод сообщения об успешном удалении
write('был успешно удален из БД'),nl,
write('Введите любой символ'),nl,
get0(_), %ожидание ввода символа
!; %отсечение альтернативы и завершение
write('Такого поезда:'),tab(2), %вывод сообщения о безуспешном удалении
write('в базе данных нет'),nl,
write('Введите любой символ'),nl,
get0(_). %ожидание ввода символа
%2-----------------------------------------------------------------------------

%3====== выборка записи из базы данных по критерию ============================
%------- выбираются поезда, отправляющиеся после времени -------------------------
proc(3):-
write('Введите час отправления:'), nl,
read(Ч2), %ввод часа
write('Введите час отправления:'), nl,
read(М2), %ввод минуты
retractall(flag(_)), %удаление фактов - flag(_)
nl,write('Поезда:'),nl,
поезд(Пункт,N,Время), %выбор записи о сотруднике
Время = время(Ч1,М1),
отправляется_после(Время,время(Ч2,М2)), %проверка критерия
assert(flag(1)), %запомнить флаг – запись найдена
write('Номер: '), write(N),nl,
write('Пункт назачения: '), write(Пункт), nl,
write('Время отправлеия: '), write(Ч1), write(':'), write(М2), nl,nl,
fail; %возврат для выбора след. записи
flag(1),!. %eсли записи были найдены, то завершить успешно

proc(3):- %cообщение, если записи не найдены
write('В базе нет таких поездов'),nl,
write('Введите любой символ'),nl,
get0(_),get0(_).

%проверка времени после
отправляется_после(Время,После):-
Время = время(Ч1,М1),
После = время(Ч2,М2),
время_больше(Ч1,М1,Ч2,М2).

время_больше(Ч1,М1,Ч2,М2):-
Ч1>Ч2;
Ч1=:=Ч2,
М1>=М2.
%3-----------------------------------------------------------------------------

%4================== просмотр базы данных =====================================
proc(4):-
write('Поезда:'),nl,
поезд(Пункт,N,Время), %извлечение записи из БД
Время = время(Час,Мин),
write('Номер поезда: '), write(N),nl, %отображение на дисплее
write('Пункт назначения: '), write(Пункт), nl, %элементов запаси
write('Время: '), write(Час), write(':'), write(Мин),nl,nl,
fail; %возврат к выбору записи
write('Введите любой символ'),nl,
get0(_),get0(_), %ожидание ввода символ
true. %завершение - записей больше нет
%4-----------------------------------------------------------------------------

%5======== загрузка базы данных из файла ======================================
proc(5):-
see('lab1.dat'), %текущий входной поток - lab1.dat
retractall(поезд(_,_,_)),%очистка БД от фактов "поезд"
db_load, %загрузка БД термами из файла
seen, %закрытие потока
write('БД загружена из файла'),nl.
%загрузка термов в БД из открытого вх. потока
db_load:-
read(Term), %чтение терма
(Term == end_of_file,!; %если конец файла, то завершение
assertz(Term), %иначе добавить терм в конец БД
db_load). %рекурсивный вызов для чтения след. терма
%5-----------------------------------------------------------------------------

%6========== сохранение БД в файле ============================================
proc(6):-
tell('lab1.dat'), %открытие вых. потока
save_db(поезд(Пункт,N,Время)), %сохранение терма
told, %закрытие вых. потока
write('БД скопирована в файл lab1.dat'),nl.
%сохранение терма в открытом файле
save_db(Term):- %сохранение терма (факта!) Term в БД
Term, %отождествление терма с термом в БД
write_term(Term, [quoted(true)]), %запись терма
write('.'),nl, %запись точки в конце терма
fail; %неудача с целью поиска след. варианта
true. %завершение, если вариантов отождествления нет
%6-----------------------------------------------------------------------------

%7============ реализация операций реляционной алгебры ========================
proc(7):-
write('Формирование отношения r1: поезда в Москву'), nl,
подмножество_поездов('Москва',R1), %R1 - список поездов в Москву
список_в_бд(R1), %добавление элементов из R1 в базу данных
вывод_списка(R1),nl, %вывод списка R1 на экран
write('Формирование отношения r2: поезда в Казань'), nl,
подмножество_поездов('Казань',R2), %R2 - список поездов в Казань
список_в_бд(R2), %добавление элементов из R2 в базу данных
вывод_списка(R2),nl, %вывод списка R2 на экран
write('Объединенное отношение r1_или_r2: '), nl,
объединение('Москва','Казань',Rez1), %Rez1 - список поездов в Москву и Казань
вывод_списка(Rez1),nl,
write('Пересечение отношений r1_и_r2: '), nl,
пересечение('Москва','Казань',Rez2), %Rez2 - список поездов в оба города
вывод_списка(Rez2),nl,
write('Разность отношений r1-r2: '), nl,
разность('Москва','Казань',Rez3), %Rez3-список поездов в Москву, но не в Казань
вывод_списка(Rez3),nl,
write('Введите любой символ'),nl,
get0(_),get0(_).
%------------------------------------------------------------------------------

подмножество_поездов(Пункт,R):-
bagof(поезд_п(Пункт,N,Время),
поезд(Пункт,N,Время), R).

объединение_r1_r2(П1,П2,Пункт,N,Время):-
поезд_п(П1,N,Время),Пункт=П1;
поезд_п(П2,N,Время),Пункт=П2.

объединение(П1,П2,Rez):-
bagof(поезд_п1_или_п2(Пункт,N,Время),
объединение_r1_r2(П1,П2,Пункт,N,Время), %условие вкл. в список
Rez).

пересечение_r1_r2(П1,П2,N,В1,В2):-
поезд_п(П1,N,В1),
поезд_п(П2,N,В2).

пересечение(П1,П2,Rez):-
bagof(поезд_п1_и_п2(П1,N,В1,П2,N,В2),
пересечение_r1_r2(П1,П2,N,В1,В2),
Rez).

разность_r1_r2(П1,П2,N,В1,В2):-
              поезд_п(П1,N,В1),
              not(поезд_п(П2,N,В2)).

разность(П1,П2,Rez):-
bagof(поезд_п1_и_не_п2(П1,N,В1),
разность_r1_r2(П1,П2,N,В1,В2),
Rez).


список_в_бд([]).
список_в_бд([H|T]):-
H=поезд_п(Пункт,N,Время),
assertz(поезд_п(Пункт,N,Время)),
список_в_бд(T).

вывод_списка([]).
вывод_списка([H|T]):-write(H),nl,вывод_списка(T).
%7-----------------------------------------------------------------------------
%8============выход============================================================
proc(8):-write('Ну и ладно...'),nl.
%8-----------------------------------------------------------------------------