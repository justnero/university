program lab03;

uses crt,Lab03mod;

var needle:integer;
	c:char;
	tobj:tree;
begin
	if(fileexists('data.bin')) then
		tobj.read_file;
	repeat
		clrscr;
		writeln('Выберите действие:');
		writeln('1 - Организация');
		writeln('2 - Добавление');
		writeln('3 - Печать рассписания');
		writeln('4 - Печать самого левого листа');
		writeln('5 - Печать структуры');
		writeln('6 - Удаление элемента');
		writeln('7 - Поиск элемента');
		writeln('8 - Выход');
		readln(c);
		clrscr;
		case c of
			'1':tobj.organize;
			'2':root:= tobj.put(root,tobj.input);
			'3':tobj.print(root,0);
			'4':tobj.print_left;
			'5':tobj.print_map(root,0);
			'6':begin
				write('Введите id для удаления: ');
				readln(needle);
				root:= tobj.pull(root,needle);
			end;
			'7':begin
				write('Введите id для поиска: ');
				readln(needle);
				tobj.print(tobj.search(root,needle),-1);
			end;
		end;
		tobj.write_file;
	until c = '8';
	tobj.free(root);
end.
