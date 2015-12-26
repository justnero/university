clear
clc

% Данные по условию
E = 17;
J = 9;
R = [2 8];

% Расчёты
A = [1     1
     R(1) -R(2)];
B = [J
    -E];
I = A\B;

% Проверка через баланс мощностей
Ppr = I(1)^2*R(1)+I(2)^2*R(2);
Pist = J*I(1)*R(1)+I(2)*E;

% Вывод
fprintf('\t\tДано\n');
fprintf('Напряжение питания\n');
fprintf('E  = %3d В\n',E);
fprintf('Сила тока питания\n');
fprintf('J  = %3d А\n',J);
fprintf('Сопротивления\n');
for i = 1:2
    fprintf('R%d = %3d Ом\n',i,R(i));
end
fprintf('\n');

fprintf('\t\tРешение\n');
fprintf('Решим СЛАУ из уравнений Кирхгофа:\n');
fprintf('{ I1 + I2 =  J\t{    I1 +    I2 =  J\t{    I1 +    I2 =  %2d\n',J);
fprintf('{ U1 - U2 = -E\t{ R1*I1 - R2*I2 = -E\t{ %2d*I1 - %2d*I2 = -%2d\n',R(1),R(2),E);
fprintf('Получим:\n');
for i = 1:2
    fprintf('{ I%d = %10.6f А\n',i,I(i));
end
fprintf('\n');

fprintf('\t\tПроверка\n');
fprintf('Расчитаем мощность источника\n');
fprintf('Pist = R1*I1*J +  E*I2   = %10.6f Вт\n',Pist);
fprintf('Расчитаем мощность приёмника\n');
fprintf('Ppr  = R1*I1^2 + R2*I2^2 = %10.6f Вт\n',Ppr);
fprintf('Видно, что Pist = Ppr следовательно задача решена правильно\n');