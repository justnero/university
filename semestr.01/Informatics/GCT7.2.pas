program task2;

const MAX_N=1000;

var a:array [1..MAX_N,1..MAX_N] of real;
    n,m:integer;
    
procedure input();
var i,j:integer;
begin 
  readln(n,m);
  for i:=1 to n do
    for j:=1 to m do
      read(a[i,j]);
end;

function sum():real;
var tmp:real;
    i,j:integer;
begin
  tmp:= 0;
  for i:= 1 to n do
    for j:= 1 to m do
      if(i <> j) then
        tmp:= tmp+a[i,j];
  sum:= tmp;
end;

function min_line():integer;
var tmp:real;
    i,j,k:integer;
begin
  tmp:= a[1,1];
  k:= 1;
  for i:= 1 to n do 
    for j:= 1 to m do
      if(a[i,j] < tmp) then begin
        tmp:= a[i,j];
        k:= i;
      end;
  min_line:= k;
end;

procedure replace();
var tmp:real;
    i,j,k:integer;
begin
  readln(j,k);
  for i:= 1 to m do begin
    tmp:= a[j,i];
    a[j,i]:= a[k,i];
    a[k,i]:= tmp;
  end;
end;

procedure print();
var i,j:integer;
begin
  for i:=1 to n do begin
    write(a[i,1]);
    for j:= 2 to m do
      write(' ',a[i,j]);
    writeln();
  end;
end;

begin
  input();
  writeln(sum());
  writeln(min_line());
  replace();
  print();
end.