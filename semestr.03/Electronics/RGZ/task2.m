clear
clc

% Дано
E = 12;
w = 250;
X = [6 11];
R = [2 1];

% Расчеты
eps = 0.0001;
z1 = (R(1)+X(1))*X(2)/(R(1)+X(1)+X(2));
z = sqrt(z1^2+R(2)^2);
I = E/z;
UR = I*R;
UX = I*X;
P = UR*I;
Q = UX*I;
U1 = (UR(1)+UX(1))*UX(2)/(UR(1)+UX(1)+UX(2));
U = sqrt(U1^2+UR(2)^2);
S1 = (P(1)+Q(1))*Q(2)/(P(1)+Q(1)+Q(2));
S = sqrt(S1^2+P(2)^2);
Pt = I*E;

% Вывод
fprintf('\t\tДано\n');
fprintf('Напряжение питания\n');
fprintf('E  = %2d В\n',E);
fprintf('Круговая частота\n');
fprintf('w  = %4d рад/с\n',w);
fprintf('Сопротивление индуктивности\n');
fprintf('Xl = %2d Ом\n',X(1));
fprintf('Сопротивление ёмкости\n');
fprintf('Xc = %2d Ом\n',X(2));
fprintf('Сопротивления резисторов\n');
for i = 1:2
    fprintf('R%d = %2d Ом\n',i,R(i));
end
fprintf('\n');

fprintf('\t\tРешение\n');
fprintf('Расчитаем сопротивление первого участка\n');
fprintf('z1 = (R1+Xl)*Xc/(R1+Xl+Xc) = %10.6f Ом\n',z1);
fprintf('Расчитаем полное сопротивление\n');
fprintf('z  = sqrt(z1^2+R2^2) = %10.6f Ом\n',z);
fprintf('Расчитаем силу тока из закона Ома\n');
fprintf('I = E/z = %10.6f А\n',I);
fprintf('Расчитаем напряжение на индуктивности\n');
fprintf('Ul  = I*Xl = %10.6f В\n',UX(1));
fprintf('Расчитаем напряжение на ёмкости\n');
fprintf('Uc  = I*Xc = %10.6f В\n',UX(2));
fprintf('Расчитаем напряжения на резисторах\n');
for i = 1:2
    fprintf('Ur%d = I*R%d = %10.6f В\n',i,i,UR(i));
end
fprintf('Расчитаем мощность на индуктивности\n');
fprintf('Ql  = I*Ul  = %10.6f Вт\n',Q(1));
fprintf('Расчитаем мощность на ёмкости\n');
fprintf('Qc  = I*Uc  = %10.6f Вт\n',Q(2));
fprintf('Расчитаем мощности на резисторах\n');
for i = 1:2
    fprintf('Pr%d = I*Ur%d = %10.6f Вт\n',i,i,P(i));
end
fprintf('\n');

fprintf('\t\tПроверка\n');
fprintf('Посчитаем суммарное напряжение первого участка\n');
fprintf('U1 = (Ur1+Ul)*Uc/(Ur1+Ul+Uc) = %10.6f В\n',U1);
fprintf('Расчитаем полное напряжение\n');
fprintf('U  = sqrt(U1^2+Ur2^2) = %10.6f В\n',U);
if abs(U-E) <= eps
    fprintf('\tU  =  E - проверка по ЗК пройдена\n');
else
    fprintf('\tU !=  E - проверка по ЗК не пройдена\n');
end
fprintf('\n');
fprintf('Посчитаем мощность первого участка\n');
fprintf('S1 = (Pr1+Ql)*Qc/(Pr1+Ql+Qc) = %10.6f В\n',S1);
fprintf('Расчитаем полную мощность\n');
fprintf('S = sqrt(S1^2+(Pr2)^2) = %10.6f Вт\n',S);
fprintf('Расчитаем теоретическую мощность\n');
fprintf('Pt = I*E = %10.6f Вт\n',Pt);
if abs(S-Pt) <= eps
    fprintf('\tS  =  Pt - проверка по БМ пройдена\n');
else
    fprintf('\tS !=  Pt - проверка по БМ не пройдена\n');
end