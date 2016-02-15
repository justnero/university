--CONNECT 'D:\university\semestr.04\DM\Lab.04\db.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

-- select two tables with(out) join
--- with join
SELECT a.id as "Activity ID", e.id as "Employee ID", e.name as "Employee name", a.a_date as "Activity date", a.a_type as "Activity type" 
FROM activity a 
JOIN employee e 
ON a.employee_id = e.id;

--- without
SELECT a.id as "Activity ID", e.id as "Employee ID", e.name as "Employee name", a.a_date as "Activity date", a.a_type as "Activity type" 
FROM activity a, employee e 
WHERE a.employee_id = e.id;


-- select more then two tables with(out) join
--- with join
SELECT e.id AS "Employee ID", e.name AS "Employee name", l.name as "Lang name"
FROM employee_lang el
JOIN employee e
ON e.id = el.employee_id
JOIN lang l
ON l.id = el.lang_id
WHERE el.lang_id <= 2;

--- without join
SELECT e.id AS "Employee ID", e.name AS "Employee name", l.name as "Lang name"
FROM employee_lang el, employee e, lang l
WHERE e.id = el.employee_id AND l.id = el.lang_id AND el.lang_id <= 2;


-- sql features
--- recurcive
SELECT e1.id AS "ID 1", e2.id AS "ID 2"
FROM employee e1, employee e2
WHERE e1.id < e2.id AND e2.id <= 3;

--- sub-query
SELECT e.id AS "ID", e.name AS "Name"
FROM employee e
WHERE EXISTS (SELECT employee_id FROM activity a WHERE a.employee_id = e.id);

--- sub-query with agregate
SELECT e.id AS "ID", e.name AS "Name"
FROM employee e
WHERE e.id IN (SELECT el.employee_id FROM employee_lang el GROUP BY el.employee_id HAVING COUNT(el.lang_id) > 1);

--- sub-query with single result
SELECT r1.salary as "Max salary"
FROM recruitment r1
WHERE r1.salary = (SELECT MAX(r2.salary) FROM recruitment r2);

--- sub-query with multiple results
SELECT e.id AS "ID", e.name AS "Name"
FROM employee e
WHERE e.id IN (SELECT employee_id FROM activity a);

--- sub-query with calculations
SELECT r.salary as "Salary"
FROM recruitment r
WHERE r.salary >= (SELECT MAX(amount)*1.5 FROM premium);

--- sub-query in having
SELECT r.post as "Post", AVG(r.salary) as "Avg. salary"
FROM recruitment r
GROUP BY r.post
HAVING AVG(r.salary) >= (SELECT MAX(amount)*0.5 FROM premium);