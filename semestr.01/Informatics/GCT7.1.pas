program task1;

const MAX_N=1000;

var n:integer;
    a:array[1..MAX_N] of integer;
    
procedure input();
var i:integer;
begin
  readln(n);
  for i:=1 to n do
    read(a[i]);
end;
  
procedure replace_l2();
var i:integer;
begin
  for i:=1 to n do
    if(a[i] < 2) then
      a[i]:= 0;
end;
  
function sum_i37():integer;
var ret_val,i:integer;
begin
  ret_val:= 0;
  for i:= 1 to n do
    if((a[i] >= -3) and (a[i] <= 7)) then 
      ret_val:= ret_val + a[i];
  sum_i37:= ret_val;
end;

function div35():integer;
var cnt,i:integer;
begin
  cnt:= 0;
  for i:= 1 to n do
    if((a[i] mod 3 = 0) and (a[i] mod 5 <> 0)) then 
      cnt:= cnt + 1;
  div35:= cnt;
end;

procedure absmin(var value,index:integer);
var v,i,j:integer;
begin
  v:= a[1];
  i:= 1;
  for j:= 2 to n do
    if(abs(a[i]) < abs(v)) then begin
      v:= a[i];
      i:= j;
    end;
  value:= v;
  index:= j;
end;

procedure sort();
var i,j,tmp:integer;
begin
  for i:= 1 to n-1 do
    if(a[i] < 0) then 
      for j:= i+1 to n do
        if(a[j] >= 0) then begin
          tmp:= a[i];
          a[i]:= a[j];
          a[j]:= tmp;
          break;
        end;
end;

procedure print();
var i:integer;
begin
  write(a[1]);
  for i:= 2 to n do 
    write(' ',a[i]);
  writeln();
end;

var v,i:integer;
begin
  input(); {Ввод элементов одномерного массива}
  replace_l2(); {Замена нулями всех элементов, меньших двух}
  writeln(sum_i37()); {Вычисление суммы элементов на интервале [-3,7]}
  writeln(div35()); {Количество элементов кратных 3 и не кратных 5}
  absmin(v,i); {Минимальный по модулю элемент и его индекс}
  writeln('a[',i,'] = ',v);
  sort(); {Упрядочение так, что бы положительные элементы были в начале}
end.