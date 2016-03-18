clear
clc
set(0,'defaultAxesFontName', 'HelveticaNeueCyr-Light')
set(0,'defaultTextFontName', 'HelveticaNeueCyr-Light')
m = 1;
n = 10000;
NU1 = 10;
NU2 = 10;
DELTA = 1;
R = ncfrnd(NU1, NU2, DELTA, m, n);
[M, V] = ncfstat(NU1, NU2, DELTA);
fprintf('\t\tТеоретические значения\n');
fprintf('Математическое ожидание: %g\n', M);
fprintf('Дисперсия:               %g\n', V);  
fprintf('\n');

M1 = meaneach(R, n); % Математическое ожидание
mu = zeros(4, n); % Первые 4 центральных момента
for i = 1:4
    mu(i, :) = meaneach((R - M1(n)) .^ i, n); % i-й центральный момент
end
y = zeros(2, n);
y(1, :) = mu(3, :) ./ (mu(2, :) .^ (3/2)); % Коеффициент асимметрии
y(2, :) = mu(4, :) ./ (mu(2, :) .^ 2) - 3; % Коеффициент эксцесса
fprintf('\t\tЭкспериментальные значения\n');
fprintf('Математическое ожидание: %g\n', M1(n));
fprintf('\tЦентральный момент\n');
for i = 1:4
    fprintf('%d-го порядка:            %g\n', i, mu(i, n));
end
fprintf('\n');
fprintf('Дисперсия:               %g\n', mu(2, n));
fprintf('Коэффициент асимметрии:  %g\n', y(1, n));
fprintf('Коэффициент эксцесса:    %g\n', y(2, n));
fprintf('\n');
fprintf('\t\tРазность экспериментального и теоретического значения\n');
fprintf('Математическое ожидание: %g\n', abs(M1(n) - M));
fprintf('Дисперсия:               %g\n', abs(mu(2, n) - V));

% Графики
str = {'Оценка математического ожидания' 
       'Число испытаний'
       'Математическое ожидание'};
myplot(M1, str, 1);

str(3) = {'Центральный момент'};
for i = 1:4
    str(1) = {sprintf('Оценка центрального момента %d-го порядка', i)};
    myplot(mu(i, :), str, i+1);
end

str(1) = {'Оценка коэффициента асимметрии'};
str(3) = {'Коэффициент асимметрии'};
myplot(y(1, :), str, 6);

str(1) = {'Оценка коэффициента эксцесса'};
str(3) = {'Коэффициент эксцесса'};
myplot(y(2, :), str, 7);
