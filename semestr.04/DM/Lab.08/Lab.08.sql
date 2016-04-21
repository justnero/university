1. Выдать информацию о всех отпусках
	R1 = sigma a_type = 'vacation' (activity);
	R2 = R1 |><| vacation
	R  = pi id, employee_id, a_date, start_date, days_count (R2);

	SELECT A.id, A.employee_id, A.a_date, B.start_date, B.days_count
	FROM activity A
	JOIN vacation B
	ON A.id = B.id
	WHERE A.a_type = 'vacation';

2. Вывести список служащих, владеющих первыми двумя языками
	R1 = pi id (sigma id <= 2 (lang));
	R2 = R1 RENAME id AS lang_id;
	R  = pi employee_id (employee_lang/R2);

	SELECT DISTINCT A.employee_id
	FROM employee_lang A
	WHERE NOT EXISTS (SELECT B.id
					  FROM lang B
					  WHERE B.id <= 2
					  AND NOT EXISTS(SELECT C.employee_id
					  				   FROM employee_lang C
					  				   WHERE C.employee_id = A.employee_id
					  				   AND C.lang_id = B.id));

3. Вывести список служащих, живущих в Севастополе и рождённых до 20.04.2000 или владеющих языком номер 3
	R1 = pi id (sigma address = 'Sevastopol' ^ b_date < '20.04.2000' (employee));
	R2 = pi employee_id (sigma lang_id = 3 (employee_lang));
	R  = R1 U R2;

	SELECT id 
	FROM employee
	WHERE address = 'Sevastopol'
	UNION 
	SELECT employee_id id
	FROM employee_lang
	WHERE lang_id = 3;

4. Вывести список служащих с именами и адресами, владеющих первыми двумя языками
	R1 = pi id (sigma id <= 2 (lang));
	R2 = R1 RENAME id AS lang_id;
	R3 = pi employee_id (employee_lang/R2);
	R4 = R3 RENAME employee_ID AS id;
	R5 = R4 |><| employee;
	R  = pi id, name, address (R5);

	SELECT DISTINCT A.employee_id, D.name, D.address
	FROM employee_lang A
	JOIN employee D
	ON D.id = A.employee_id
	WHERE NOT EXISTS (SELECT B.id
					  FROM lang B
					  WHERE B.id <= 2
					  AND NOT EXISTS(SELECT C.employee_id
					  				   FROM employee_lang C
					  				   WHERE C.employee_id = A.employee_id
					  				   AND C.lang_id = B.id));

5. Вывести список служащих из Севастополя или Симферополя, за исключением владеющих языком 3
	R1 = pi id (sigma address = 'Sevastopol' V address = 'Simferopol' (employee));
	R2 = pi employee_id (sigma lang_id = 3 (employee_lang));
	R3 = R2 RENAME employee_id AS id;
	R  = R1 - R3;

	SELECT A.id
	FROM employee A
	WHERE (address = 'Sevastopol'
	OR address = 'Simferopol')
	AND NOT EXISTS (SELECT B.employee_id
					FROM employee_lang B
					WHERE B.employee_id = A.id
					AND B.lang_id = 3);

6. Вывести наибольшую премию
	SELECT MAX(amount)
	FROM premium;

	На РА возможно выдать только все премии из-за отсутствия агрегатных функций
	R  = pi amount (premium);