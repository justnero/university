clear all;    %  очистка рабочего пространства
close all;    %  закрываем все созданные фигуры
Ts=0.01;    %  шаг во времени (с) (частота квантования)
T= 100;     %  длительность процесса (с)
[F_Name,PathName]=uigetfile('*.jpg','Выберите имя изображения');
I=imread([PathName F_Name]);   
figure(1);
imshow(I); 
%  ПОСТРОЕНИЕ ГРАФИКА ПРОЦЕССА
A=double(I);  
%s = round(length(A)/2);  
variable = A(:,1);  
figure(2);
stem(variable);   
title('PROCESS');   
ylabel('Y');   
xlabel('N');

%  ПОСТРОЕНИЕ ГИСТОГРАММЫ    
n=length(variable);   
k=round(sqrt(n));     
figure(3);
hist(variable, k);       
title('HISTOGRAMMA');
ylabel('Q');
xlabel('N');
   
%  ПОСТРОЕНИЕ СП ПРИ ПОМОЩИ ПРОЦЕДУРЫ PSD
fsp=250;    
df=1/T;   Fmax=1/Ts;   f=-Fmax/2:df:Fmax/2;   dovg=length(f);
[c, f]=psd(variable, dovg, Fmax);
figure(4);
stem(f(1:fsp), c(1:fsp));grid;  title('PSD');  ylabel('SP');  xlabel('frequency'); 

%  ПОСТРОЕНИЕ АКФ СЛУЧАЙНОГО ПРОЦЕССА
R=xcorr(variable);  
tau = -(n/100 - 0.01):0.01:(n/100 - 0.01);
figure(5);
plot(tau,R);  grid;
title('AKVF');
ylabel('Bcov'); 
xlabel('tau'); 

R1=xcov(variable);    
figure(6);
plot(tau,R1);  grid;
title('AKRF');
ylabel('Bcor'); 
xlabel('tau'); 

% оценки численных характеристик
R = variable;
n = length(variable);

M1 = meanearch(R, n);
fprintf('Оценка математического ожидания: %g\n', M1(n));

mu = zeros(4, n);
for i = 1:4
    mu(i, :) = meanearch( (R - M1(n)) .^ i, n);
    fprintf('Оценка центрального момента %d-го порядка случайной величины: %g\n', i, mu(i, n));
end

y = zeros(2, n);
y(1, :) = mu(3, :) ./ (mu(2, :) .^ (3/2));
y(2, :) = mu(4, :) ./ (mu(2, :) .^ 2) - 3;

fprintf('\nОценка дисперсии: %g\n', mu(2, n));
fprintf('Оценка среднеквадратического значения: %g\n', sqrt(mu(2, n)));
fprintf('Оценка коэффициента асимметрии: %g\n', y(1, n));
fprintf('Оценка коэффициента эксцесса: %g\n', y(2, n));
