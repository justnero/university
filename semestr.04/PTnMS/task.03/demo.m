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
fprintf('\t\t������������� ��������\n');
fprintf('�������������� ��������: %g\n', M);
fprintf('���������:               %g\n', V);  
fprintf('\n');

M1 = meaneach(R, n); % �������������� ��������
mu = zeros(4, n); % ������ 4 ����������� �������
for i = 1:4
    mu(i, :) = meaneach((R - M1(n)) .^ i, n); % i-� ����������� ������
end
y = zeros(2, n);
y(1, :) = mu(3, :) ./ (mu(2, :) .^ (3/2)); % ����������� ����������
y(2, :) = mu(4, :) ./ (mu(2, :) .^ 2) - 3; % ����������� ��������
fprintf('\t\t����������������� ��������\n');
fprintf('�������������� ��������: %g\n', M1(n));
fprintf('\t����������� ������\n');
for i = 1:4
    fprintf('%d-�� �������:            %g\n', i, mu(i, n));
end
fprintf('\n');
fprintf('���������:               %g\n', mu(2, n));
fprintf('����������� ����������:  %g\n', y(1, n));
fprintf('����������� ��������:    %g\n', y(2, n));
fprintf('\n');
fprintf('\t\t�������� ������������������ � �������������� ��������\n');
fprintf('�������������� ��������: %g\n', abs(M1(n) - M));
fprintf('���������:               %g\n', abs(mu(2, n) - V));

% �������
str = {'������ ��������������� ��������' 
       '����� ���������'
       '�������������� ��������'};
myplot(M1, str, 1);

str(3) = {'����������� ������'};
for i = 1:4
    str(1) = {sprintf('������ ������������ ������� %d-�� �������', i)};
    myplot(mu(i, :), str, i+1);
end

str(1) = {'������ ������������ ����������'};
str(3) = {'����������� ����������'};
myplot(y(1, :), str, 6);

str(1) = {'������ ������������ ��������'};
str(3) = {'����������� ��������'};
myplot(y(2, :), str, 7);
