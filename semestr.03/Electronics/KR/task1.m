clear
clc

% Данные по условию
t = [1 1.2]*10^(-9);
U = 5;

% Расчеты
f = 1/(2*pi*t(1));
Up = 1.2*U;
R = 5*10^3;
h = 90;
I = [Up/R 100*10^(-3) Up/(h*R)];
M = (0.1*10^(-3))/I(3);
tau = 1/(2*pi*f);
t10 = tau*log(M/(M-1));
tp = tau*log(2*M/(M+1));
t01 = tau*log((M+1)/M);
tv = tp+t01;

fprintf('\t\tРешение:\n');
fprintf('fгр \t= %10.6f МГц\n',f/(10^6));
fprintf('Uп \t\t= %10.6f В\n',Up);
fprintf('Iкн \t= %10.6f мА\n',I(1)*10^3);
fprintf('Iкдоп \t= %10.6f мА\n',I(2)*10^3);
fprintf('Iбн \t= %10.6f мА\n',I(3)*10^3);
fprintf('M \t\t= %10.6f > 1\n',M);
fprintf('T21э \t= %10.6f нс\n',tau*10^9);
fprintf('t10 \t= %10.6f нс\n',t10*10^9);
fprintf('t01 \t= %10.6f нс\n',t01*10^9);
fprintf('tp  \t= %10.6f нс\n',tp*10^9);
fprintf('t10 \t= %10.6f нс <= %10.6f нс\n',t10*10^9,t(1)*10^9);
fprintf('tv \t\t= %10.6f нс <= %10.6f нс\n',tv*10^9,t(2)*10^9);