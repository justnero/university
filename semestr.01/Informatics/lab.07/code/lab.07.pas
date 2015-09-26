program lab7;

uses crt;

var list:array[0..1000] of real;
	n,i,j,min_index,max_index,tmp_integer:integer;
	sum,mult,tmp_real:real;
begin 
	sum:= 0.0;
	mult:= 1.0;
	writeln('Введите кол-во элементов n');
	readln(n);
	writeln('Введите ',n,' элементов массива');
	min_index:= 1;
	max_index:= 1;
	for i:= 1 to n do begin
		read(list[i]);
		if(abs(list[i]) > abs(list[max_index])) then
			max_index:= i; {Индекс максимального элемента}
		if(abs(list[i]) < abs(list[min_index])) then
			min_index:= i; {Индекс минимального элемента}
		if(list[i] < 0) then
			sum:= sum + list[i]; {Считаем сумму}
	end;
	writeln('Сумма отрицательных элементов массива:');
	writeln(sum);
	if(min_index > max_index) then begin
		tmp_integer:= max_index;
		max_index:= min_index;
		min_index:= tmp_integer;
	end;
	for i:=min_index+1 to max_index-1 do
		mult:= mult * list[i]; {Считаем произведение}
	writeln('Произведение элементов между максимальным и минимальным:');
	writeln(mult);
	for i:= 2 to n do begin {Сортировка методом вставки}
		tmp_real:= list[i];
		list[0]:= tmp_real;
		j:= i;
		while(tmp_real > list[j-1]) do begin
			list[j]:= list[j-1];
			j:= j-1;
		end;
		list[j]:= tmp_real;
	end;
	writeln('Отсортированые методом вставки элементы:');
	for i:= 1 to n do
		write(list[i],' ');
	writeln();
	readln;
end.