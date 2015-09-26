program lab8;
type Tmatrix = array [1..1000,1..1000] of integer;
var n,m,cnt:integer;
    matrix:Tmatrix;
procedure readMatrix(); {Чтение матрицы}
var i,j:integer;
    flag:boolean;
begin
  readln(n,m);
  cnt:= 0;
  for i:= 1 to n do begin
    flag:= true;
    matrix[i,m+1]:= 0;
    for j:= 1 to m do begin
      read(matrix[i,j]);
      if(matrix[i,j] = 0) then flag:= false;
      if((matrix[i,j] > 0) and (matrix[i,j] mod 2 = 0)) then
        inc(matrix[i,m+1],matrix[i,j]);
    end;
    if(flag) then inc(cnt);
  end;
end;
procedure swap(var a,b:integer); {Обмен переменных значениями}
var buffer:integer;
begin
  buffer:= a;
  a:= b;
  b:= buffer;
end;
procedure sort(); {Сортировка матрици по характеристикам}
var i,j,minIndex:integer;
begin
  for i:= 1 to n-1 do begin
    minIndex:= i;
    for j:= i+1 to n do begin
      if(matrix[j,m+1] < matrix[minIndex,m+1]) then
        minIndex:= j;
    end;
    if(minIndex <> i) then begin
      for j:= 1 to m+1 do
        swap(matrix[i,j],matrix[minIndex,j]);
    end;
  end;
end;
procedure printMatrix(); {Печать элементов матрицы}
var i,j:integer;
begin
  for i:= 1 to n do begin
    for j:= 1 to m do
      write(matrix[i,j],' ');
    writeln;
  end;
end;
begin
  readMatrix();
  writeln('Количество строк без нулей: ',cnt);
  sort();
  writeln('Отсортированная матрица');
  printMatrix();
end.