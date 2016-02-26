--CONNECT 'D:\university\semestr.04\DM\Lab.03\db.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

-- from labs before
--CREATE TABLE employee (
--	id  INTEGER NOT NULL,
--	name VARCHAR(32) NOT NULL,
--	address VARCHAR(32),
--	b_date DATE
--);
--INSERT INTO employee VALUES (1, 'Andrew', 'Sevastopol', '12.09.1978'); 
--INSERT INTO employee VALUES (2, 'John', 'Yalta', NULL); 
--INSERT INTO employee VALUES (3, 'Bob', 'Kerch', NULL); 
--INSERT INTO employee VALUES (4, 'Mary', 'Sevastopol', '22.09.1996'); 
--INSERT INTO employee VALUES (5, 'Jerry', 'Simferopol', '09.05.1945'); 
--INSERT INTO employee VALUES (6, 'Arif', 'Simferopol', '06.10.1997'); 
--INSERT INTO employee VALUES (7, 'Jane', 'Alushta', NULL); 
--INSERT INTO employee VALUES (8, 'Kate', NULL, NULL); 
--INSERT INTO employee VALUES (9, 'Iris', NULL, NULL); 
--INSERT INTO employee VALUES (10, 'Vladimir', NULL, NULL);


-- changes
ALTER TABLE employee ADD PRIMARY KEY (id);
ALTER TABLE employee ADD UNIQUE (name, address);

-- create
--- basic tables
CREATE TABLE lang (
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(32) NOT NULL,
	
	UNIQUE (name)
);
CREATE TABLE education (
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(32) NOT NULL,
	specialty VARCHAR(32) NOT NULL,
	description VARCHAR(64),
	
	UNIQUE (name, specialty)
);

--- one fk tables
CREATE TABLE activity (
	id INTEGER NOT NULL PRIMARY KEY,
	employee_id INTEGER NOT NULL,
	a_date DATE,
	a_type VARCHAR(16) NOT NULL,
	
	FOREIGN KEY (employee_id) REFERENCES employee ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE vacation (
	id INTEGER NOT NULL PRIMARY KEY,
	start_date DATE NOT NULL,
	days_count INTEGER NOT NULL,
	
	FOREIGN KEY (id) REFERENCES activity ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE recruitment (
	id INTEGER NOT NULL PRIMARY KEY,
	post VARCHAR(32) NOT NULL,
	salary DECIMAL NOT NULL,
	
	FOREIGN KEY (id) REFERENCES activity ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE premium (
	id INTEGER NOT NULL PRIMARY KEY,
	amount DECIMAL NOT NULL,
	
	FOREIGN KEY (id) REFERENCES activity ON DELETE CASCADE ON UPDATE CASCADE
);

--- pivot tables 
CREATE TABLE employee_lang (
	employee_id INTEGER NOT NULL,
	lang_id INTEGER NOT NULL,
	
	PRIMARY KEY (employee_id, lang_id),
	FOREIGN KEY (employee_id) REFERENCES employee ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (lang_id) REFERENCES lang ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE employee_education (
	employee_id INTEGER NOT NULL,
	education_id INTEGER NOT NULL,
	
	PRIMARY KEY (employee_id, education_id),
	FOREIGN KEY (employee_id) REFERENCES employee ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (education_id) REFERENCES education ON DELETE CASCADE ON UPDATE CASCADE
);



-- fill
--- basic
---- lang
INSERT INTO lang VALUES (1, 'Russian');
INSERT INTO lang VALUES (2, 'English');
INSERT INTO lang VALUES (3, 'SQL');
INSERT INTO lang VALUES (4, 'Java');
INSERT INTO lang VALUES (5, 'PHP');
INSERT INTO lang VALUES (6, 'C++');

---- education
INSERT INTO education VALUES (1, 'SevSU', 'IS', 'Information systems');
INSERT INTO education VALUES (2, 'CFUV', 'BI', 'Buisnes informatics');


--- one fk
---- activity
INSERT INTO activity VALUES (1, 4, '01.09.2014', 'recruitment');
INSERT INTO activity VALUES (2, 6, '01.09.2014', 'recruitment');
INSERT INTO activity VALUES (3, 4, '14.02.2016', 'premium');
INSERT INTO activity VALUES (4, 6, '14.02.2016', 'vacation');

---- vacation
INSERT INTO vacation VALUES (4, '14.02.2016', 30);

---- recruitment
INSERT INTO recruitment VALUES (1, 'Senior programmer', 200000);
INSERT INTO recruitment VALUES (2, 'TeamLead', 300000);

---- premium
INSERT INTO premium VALUES (3, 150000);


--- pivot
---- employee_lang
INSERT INTO employee_lang VALUES (1, 1);
INSERT INTO employee_lang VALUES (2, 1);
INSERT INTO employee_lang VALUES (2, 2);
INSERT INTO employee_lang VALUES (3, 1);
INSERT INTO employee_lang VALUES (4, 1);
INSERT INTO employee_lang VALUES (4, 2);
INSERT INTO employee_lang VALUES (4, 3);
INSERT INTO employee_lang VALUES (4, 6);
INSERT INTO employee_lang VALUES (5, 2);
INSERT INTO employee_lang VALUES (6, 1);
INSERT INTO employee_lang VALUES (6, 2);
INSERT INTO employee_lang VALUES (6, 3);
INSERT INTO employee_lang VALUES (6, 4);
INSERT INTO employee_lang VALUES (6, 5);
INSERT INTO employee_lang VALUES (6, 6);
INSERT INTO employee_lang VALUES (7, 1);
INSERT INTO employee_lang VALUES (8, 1);
INSERT INTO employee_lang VALUES (9, 2);
INSERT INTO employee_lang VALUES (10,1);

---- employee_education
INSERT INTO employee_education VALUES (1, 1);
INSERT INTO employee_education VALUES (2, 2);
INSERT INTO employee_education VALUES (3, 1);
INSERT INTO employee_education VALUES (4, 1);
INSERT INTO employee_education VALUES (5, 1);
INSERT INTO employee_education VALUES (6, 1);
INSERT INTO employee_education VALUES (7, 1);
INSERT INTO employee_education VALUES (8, 2);
INSERT INTO employee_education VALUES (9, 2);
INSERT INTO employee_education VALUES (10,2);



-- querys
--- insert wrong data to pivot table
INSERT INTO employee_lang VALUES (11, 1);

--- insert wrong data to one fk table
INSERT INTO premium VALUES (5, 100500);

--- select current langs for employee 6
SELECT el.lang_id, l.name FROM employee_lang el JOIN lang l ON l.id = el.lang_id WHERE el.employee_id = 6;

--- delete lang 5
DELETE FROM lang WHERE id = 5;

--- select changed langs for employee 6
SELECT el.lang_id, l.name FROM employee_lang el JOIN lang l ON l.id = el.lang_id WHERE el.employee_id = 6;

--- select activities for users
SELECT a.id, a.employee_id, e.name, a.a_date, a.a_type FROM activity a JOIN employee e ON e.id = a.employee_id;

--- update employee id from 6 to 666
UPDATE employee SET id = 666 WHERE id = 6;

--- select changed activities for users
SELECT a.id, a.employee_id, e.name, a.a_date, a.a_type FROM activity a JOIN employee e ON e.id = a.employee_id;
