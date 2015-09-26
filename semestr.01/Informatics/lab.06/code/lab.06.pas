program lab6;
uses crt;
var
	si,x_begin,x_end,dx,y,x,E:real;
	i:integer;
begin
clrscr;
writeln('Введите значение x начальное');
readln(x_begin); 
writeln;
writeln('Введите значение x конечное');
readln(x_end);
writeln;
writeln('Введите значение шага dx');
readln(dx);
writeln;
writeln('Введите точность E');
readln(E);
writeln;
x:=x_begin;
if (x>x_end) then begin {Нет смысла, если начально больше конечного}
	writeln('Значение х-начального больше х-конечного');
	writeln('Решения нет');
end else begin
    while (x<=x_end) do begin {Цикл по X-ам}
		si:=-x;
		y:=1+si;
		i:=0;
		while (abs(si)>E) do begin {Контроль точности}
			inc(i); {Cчетик просуммированных элементов ряда}
			si:=si*(-x/(i+1));  
			y:=y+si;                             
		end;
		writeln('x=',x,' y=',y,' Количество просуммированных членов ряда ',i);
		x:=x+dx; {Шаг}
	end;
end;
readln;
end.
