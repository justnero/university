unit Lab03mod;

interface
type 
	TNodePointer=^TNode;
	TData=record
		id:integer;
		origin,destination:string[16];
		time_departure,time_arrival:string[5];
		price:integer;
	end;
	TNode=record
		data:TData;
		left,right:TNodePointer;
	end;
	TDataFile=file of TData;
	tree=object
		function  search(node:TNodePointer;needle:integer):TNodePointer;
		function  pull(node:TNodePointer;needle:integer):TNodePointer;
		function  put(node:TNodePointer;element:TData):TNodePointer;
		function  input:TData;
		procedure organize;
		procedure read_file;
		procedure free(node:TNodePointer);
		procedure write_file;
		procedure writef(var f:TDataFile;tmpp:TNodePointer);
		procedure print_left;
		procedure print(node:TNodePointer;level:integer);
		procedure print_map(node:TNodePointer;tab:integer);
	end;

var root:TNodePointer;

implementation
function  tree.search(node:TNodePointer;needle:integer):TNodePointer;
var tmpp:TNodePointer;
begin
	if(node = nil) then
		search:= nil
	else if(node^.data.id = needle) then 
		search:= node
	else begin
		tmpp:= search(node^.left,needle);
		if(tmpp <> nil) then
			search:= tmpp
		else begin
			tmpp:= search(node^.right,needle);
			if(tmpp <> nil) then
				search:= tmpp
			else
				search:= nil;
		end;
	end;
end;

function  tree.pull(node:TNodePointer;needle:integer):TNodePointer;
var tmpp:TNodePointer;
begin
	if(node = nil) then
		pull:= nil
	else if(node^.data.id = needle) then begin
		if((node^.left = nil) and (node^.right = nil)) then begin
			pull:= nil;
			dispose(node);
		end else if((node^.left <> nil) and (node^.right = nil)) then begin
			pull:= node^.left;
			dispose(node);
		end else if((node^.left = nil) and (node^.right <> nil)) then begin
			pull:= node^.right;
			dispose(node);
		end else begin
			if(node^.right^.left = nil) then begin
				node^.right^.left:= node^.left;
				pull:= node^.right;
				dispose(node);
			end else begin
				tmpp:= node^.right;
				while(tmpp^.left <> nil) do
					tmpp:= tmpp^.left;
				node^.data:= tmpp^.data;
				node^.right:= pull(node^.right,tmpp^.data.id);
				pull:= node;
			end;
		end;
	end else begin
		node^.left:= pull(node^.left,needle);
		node^.right:= pull(node^.right,needle);
		pull:= node;
	end;
end;

function  tree.put(node:TNodePointer;element:TData):TNodePointer;
begin
	if(node = nil) then begin
		new(node);
		node^.data:= element;
		node^.left:= nil;
		node^.right:= nil;
	end else
		if(node^.data.id > element.id) then
			node^.left:= tree.put(node^.left,element) 
		else if (node^.data.id < element.id) then
			node^.right:= tree.put(node^.right,element);
	put:= node;
end;

function  tree.input:TData;
var data:TData;
begin
	write('Введите номер поезда: ');
	readln(data.id);
	write('Введите пункт отправления: ');
	readln(data.origin);
	write('Введите пункт назначения: ');
	readln(data.destination);
	write('Введите время отправления: ');
	readln(data.time_departure);
	write('Введите время прибытия: ');
	readln(data.time_arrival);
	write('Введите стоимость билета: ');
	readln(data.price);
	input:= data;
end;

procedure tree.free(node:TNodePointer);
begin
	if(node <> nil) then begin
		free(node^.left);
		free(node^.right);
		dispose(node);
	end;
end;

procedure tree.organize;
var c:char;
begin
	if(root <> nil) then
		free(root);
	writeln('Организация рассписания');
	repeat
		root:= put(root,input);
		writeln('Продолжить? (y/n)');
		readln(c);
	until(c <> 'y');
end;

procedure tree.read_file;
var f:file of TData;
    tmpd:TData;
begin
	assign(f,'data.bin');
	reset(f);
	if(root <> nil) then
		free(root);
	root:= nil;
	while(not(EOF(f))) do begin
		read(f,tmpd);
		root:= put(root,tmpd);
	end;
	close(f);
end;

procedure tree.writef(var f:TDataFile;tmpp:TNodePointer);
begin
	if(tmpp <> nil) then begin
		writef(f,tmpp^.left);
		writef(f,tmpp^.right);
		write(f,tmpp^.data);
	end;
end;

procedure tree.write_file;
var f:file of TData;
begin
	assign(f,'data.bin');
	rewrite(f);
	writef(f,root);
	close(f);
end;

procedure tree.print_left;
var tmpp:TNodePointer;
begin
	tmpp:= root;
	while(tmpp^.left <> nil) do	
		tmpp:= tmpp^.left;
	print(tmpp,-1);
end;

procedure tree.print(node:TNodePointer;level:integer);
begin
	if(level < 1) then begin
		writeln('+-------+-------------------+-------------------+-------------+-------------+----------+');
		writeln('| Номер | Пункт отправления |  Пункт назначения | Отправление |   Прибытие  |   Цена   |');
		writeln('+-------+-------------------+-------------------+-------------+-------------+----------+');
	end;
	if(node <> nil) then begin
		if(level <> -1) then
			print(node^.left,1);
		with node^.data do
			writeln('|',id:7,'|',origin:19,'|',destination:19,'|',time_departure:13,'|',time_arrival:13,'|',price:10,'|');
		writeln('+-------+-------------------+-------------------+-------------+-------------+----------+');
		if(level <> -1) then
			print(node^.right,1);
	end;
	if(level < 1) then begin
		writeln('Для продолжения нажмите клавишу Enter');
		readln;
	end;
end;

procedure tree.print_map(node:TNodePointer;tab:integer);
begin
	if(node<>nil) then begin
		tab:= tab+3;
		tree.print_map(node^.right,tab);
		writeln('':tab,node^.data.id);
		tree.print_map(node^.left,tab);
		if(tab = 3) then begin
			writeln('Для продолжения нажмите клавишу Enter');
			readln;
		end;
	end;
end;

begin
	root:= nil;
end.
