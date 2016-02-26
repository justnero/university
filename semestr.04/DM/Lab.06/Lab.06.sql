--CONNECT 'D:\university\semestr.04\DM\Lab.06\db.fdb' USER 'SYSDBA' PASSWORD 'masterkey';

-- create views
--- create view for vacations
CREATE VIEW employee_vacation(id, employee_id, a_date, start_date, days_count) AS
SELECT a.id, a.employee_id, a.a_date, v.start_date, v.days_count
FROM activity a, vacation v
WHERE a.a_type = 'vacation';

--- create view for premiums
CREATE VIEW employee_premium(name, premium, vacation) AS
SELECT e.name, SUM(p.amount), SUM(v.days_count)
FROM employee e, activity a
LEFT JOIN premium p ON a.a_type = 'premium' AND p.id = a.id
LEFT JOIN vacation v ON a.a_type = 'vacation' AND v.id = a.id
WHERE a.employee_id = e.id
GROUP BY e.id, e.name;

--- create editable view
CREATE VIEW employee_secret(id, name, b_date) AS
SELECT id, name, b_date
FROM employee;

-- select views
--- select from vacations view
SELECT * FROM employee_vacation;

--- select from premiums view
SELECT * FROM employee_premium;

--- select from editable view
SELECT * FROM employee_secret;


-- update views
--- update editable view
UPDATE employee_secret SET b_date = b_date - 1;

--- select updated editable view
SELECT * FROM employee_secret;


-- for views to be closed
COMMIT;


-- drop views
--- drop vacations view
DROP VIEW employee_vacation;

--- drop vacations view
DROP VIEW employee_premium;

--- drop editable view
DROP VIEW employee_secret;
