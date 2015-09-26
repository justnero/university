program lab10;

uses crt;

const MAX_N=1000;
      MAX_M=5;
type student = record
      name,group:string[20];
      mark:array [1..MAX_M] of real;
      avarage:real;
      high:boolean;
     end;

var file_name:string;
    students:array [1..MAX_N] of student;
    f:file of student;
    count,high:integer;

procedure read_file();
var i:integer;
begin
  seek(f,0);
  for i:=1 to filesize(f) do begin
    read(f,students[i]);
    if(students[i].high) then
      high:= high+1;
  end;
  count:= filesize(f);
end;

procedure write_file();
var i:integer;
begin
  rewrite(f);
  seek(f,0);
  for i:=1 to count do
    write(f,students[i]);
end;

procedure sort();
var i:integer;
    stud:student;
begin
  i:= count;
  while((i>1) and (students[i].avarage < students[i-1].avarage)) do begin
    stud:= students[i-1];
    students[i-1]:= students[i];
    students[i]:= stud;
    i:= i-1;
  end;
end;

procedure read_student();
var stud:student;
    i:integer;
begin
  writeln('Введите Ф.И.О.:');
  readln(stud.name);
  writeln('Введите группу:');
  readln(stud.group);
  stud.avarage:= 0;
  for i:=1 to MAX_M do begin
    writeln('Введите оценку #',i);
    readln(stud.mark[i]);
    stud.avarage:= stud.avarage + stud.mark[i];
    if(stud.mark[i]>3) then
      stud.high:= true;
  end;
  stud.avarage:= stud.avarage/MAX_M;
  if(stud.high) then
    high:= high+1;
  count:= count+1;
  students[count]:= stud;
  sort();
  write_file();
end;

procedure print();
var i:integer;
begin
  if(high > 0) then begin
    writeln('+--------------------+--------------------+');
    writeln('|','Ф.И.О.':13,'|':8,'Группа':13,'|':8);
    for i:= 1 to count do 
      if(students[i].high) then begin
        writeln('+--------------------+--------------------+');
        writeln('|',students[i].name:20,'|',students[i].group:20,'|');
      end;
    writeln('+--------------------+--------------------+');
  end else begin
    writeln('Таких студентов нет.');
  end;
  writeln('Для продолжения нажмите Enter');
  readln;
end;

var action:integer;
begin
  writeln('Добро пожаловать! Введите имя файла с данными:');
  readln(file_name);
  assign(f,file_name);
  if(not(fileexists(file_name))) then
    rewrite(f)
  else
    reset(f);
  count:= 0;
  high:= 0;
  repeat
    clrscr();
    writeln('Выберите одно из доступных действий:');
    writeln('1 - Чтение файла');
    writeln('2 - Запись элемента в файл');
    writeln('3 - Вывод всех студентов с оценками 4 и 5');
    writeln('4 - Выход');
    readln(action);
    case action of
      1:read_file();
      2:read_student();
      3:print();
    end;
  until action = 4;
  writeln('Выключение...');
  close(f);
end.