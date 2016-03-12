--CONNECT 'D:\university\semestr.04\DM\Lab.07\db.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

-- create generators
--- create employee generator
CREATE GENERATOR employee_id;
SET GENERATOR employee_id TO 10;

--- create activity generator
CREATE GENERATOR activity_id;
SET GENERATOR activity_id TO 4;

--- create lang generator
CREATE GENERATOR lang_id;
SET GENERATOR lang_id TO 6;

--- create education generator
CREATE GENERATOR education_id;
SET GENERATOR education_id TO 2;


-- create triggers for auto increments
SET TERM !!;
--- create auto increment trigger for employee
CREATE TRIGGER employee_auto_increment
FOR employee ACTIVE
BEFORE INSERT AS BEGIN
	IF((NEW.id IS NULL) OR (NEW.id = 0)) THEN BEGIN
		NEW.id = GEN_ID(employee_id, 1);
	END
END!!

--- create auto increment trigger for activity
CREATE TRIGGER activity_auto_increment
FOR activity ACTIVE
BEFORE INSERT AS BEGIN
	IF((NEW.id IS NULL) OR (NEW.id = 0)) THEN BEGIN
		NEW.id = GEN_ID(activity_id, 1);
	END
END!!

--- create auto increment trigger for lang
CREATE TRIGGER lang_auto_increment
FOR lang ACTIVE
BEFORE INSERT AS BEGIN
	IF((NEW.id IS NULL) OR (NEW.id = 0)) THEN BEGIN
		NEW.id = GEN_ID(lang_id, 1);
	END
END!!

--- create auto increment trigger for education
CREATE TRIGGER education_auto_increment
FOR education ACTIVE
BEFORE INSERT AS BEGIN
	IF((NEW.id IS NULL) OR (NEW.id = 0)) THEN BEGIN
		NEW.id = GEN_ID(education_id, 1);
	END
END!!


-- create trigger for editable view
--- insert
CREATE TRIGGER employee_vacation_insert
FOR employee_vacation ACTIVE
BEFORE INSERT AS BEGIN
	INSERT INTO activity VALUES (NEW.id, NEW.employee_id, NEW.a_date, 'vacation');
	INSERT INTO vacation VALUES (NEW.id, NEW.start_date, NEW.days_count);
END!!

--- update
CREATE TRIGGER employee_vacation_update
FOR employee_vacation ACTIVE
BEFORE UPDATE AS BEGIN
	UPDATE activity
	SET id = NEW.id, employee_id = NEW.employee_id, a_date = NEW.a_date
	WHERE id = OLD.id;
	UPDATE vacation
	SET start_date = NEW.start_date, days_count = NEW.days_count
	WHERE id = NEW.id;
END!!

--- delete
CREATE TRIGGER employee_vacation_delete
FOR employee_vacation ACTIVE
BEFORE DELETE AS BEGIN
	DELETE FROM activity WHERE id = OLD.id;
	DELETE FROM vacation WHERE id = OLD.id;
END!!

SET TERM ;!!


-- insert new data with generators and auto generators
--- to employee
INSERT INTO employee VALUES (GEN_ID(employee_id, 1), 'Jack', 'Alupka', '28.03.1983');
INSERT INTO employee VALUES (GEN_ID(employee_id, 1), 'Bruce', 'Feodosia', '12.08.1948');
INSERT INTO employee(name, address, b_date) VALUES ('Lex', 'Yalta', '25.05.1973');

--- to lang
INSERT INTO lang VALUES (GEN_ID(lang_id, 1), 'C#');
INSERT INTO lang(name) VALUES ('BrainFuck');


-- change data with triggers
--- insert
INSERT INTO employee_vacation VALUES (5, 1, '12.03.2016', '12.03.2016', 10);
INSERT INTO employee_vacation VALUES (6, 2, '12.03.2016', '15.03.2016', 16);
INSERT INTO employee_vacation VALUES (7, 3, '12.03.2016', '17.03.2016',  7);

--- update
UPDATE employee_vacation SET days_count = 6 WHERE id = 6;

--- delete
DELETE FROM employee_vacation WHERE id = 7;


-- show changes
--- show employee table
SELECT * FROM employee;

--- show lang table
SELECT * FROM lang;

--- show employee_vacation table
SELECT * FROM employee_vacation;
