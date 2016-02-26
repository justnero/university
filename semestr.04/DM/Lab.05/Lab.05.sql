--CONNECT 'D:\university\semestr.04\DM\Lab.05\db.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

-- select inner from same table
SELECT e1.id AS "Employee ID", e1.name AS "Employee name"
FROM employee e1
WHERE e1.id >= (SELECT AVG(e2.id) FROM employee e2 WHERE e2.address = e1.address);


-- select inner from different table
SELECT e.id AS "Employee ID", e.name AS "Employee name"
FROM employee e
WHERE (SELECT COUNT(el.lang_id) FROM employee_lang el WHERE el.employee_id = e.id) > 1;

-- select with exists
SELECT e.id AS "ID", e.name AS "Name"
FROM employee e
WHERE EXISTS (SELECT employee_id FROM activity a WHERE a.employee_id = e.id);

-- select with all
SELECT e1.id AS "ID", e1.name AS "Name", e1.address AS "Address"
FROM employee e1
WHERE e1.id > ALL(SELECT e2.id FROM employee e2 WHERE e2.address <> e1.address);

-- select with any
SELECT e1.id AS "ID", e1.name AS "Name", e1.address AS "Address"
FROM employee e1
WHERE e1.id > ANY(SELECT e2.id FROM employee e2 WHERE e2.address <> e1.address);
