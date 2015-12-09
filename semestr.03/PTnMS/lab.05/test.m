clear all;    %  ������� �������� ������������
close all;    %  ��������� ��� ��������� ������
Ts=0.01;    %  ��� �� ������� (�) (������� �����������)
T= 100;     %  ������������ �������� (�)
[F_Name,PathName]=uigetfile('*.jpg','�������� ��� �����������');
I=imread([PathName F_Name]);   
figure(1);
imshow(I); 
%  ���������� ������� ��������
A=double(I);  
%s = round(length(A)/2);  
variable = A(:,1);  
figure(2);
stem(variable);   
title('PROCESS');   
ylabel('Y');   
xlabel('N');

%  ���������� �����������    
n=length(variable);   
k=round(sqrt(n));     
figure(3);
hist(variable, k);       
title('HISTOGRAMMA');
ylabel('Q');
xlabel('N');
   
%  ���������� �� ��� ������ ��������� PSD
fsp=250;    
df=1/T;   Fmax=1/Ts;   f=-Fmax/2:df:Fmax/2;   dovg=length(f);
[c, f]=psd(variable, dovg, Fmax);
figure(4);
stem(f(1:fsp), c(1:fsp));grid;  title('PSD');  ylabel('SP');  xlabel('frequency'); 

%  ���������� ��� ���������� ��������
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

% ������ ��������� �������������
R = variable;
n = length(variable);

M1 = meanearch(R, n);
fprintf('������ ��������������� ��������: %g\n', M1(n));

mu = zeros(4, n);
for i = 1:4
    mu(i, :) = meanearch( (R - M1(n)) .^ i, n);
    fprintf('������ ������������ ������� %d-�� ������� ��������� ��������: %g\n', i, mu(i, n));
end

y = zeros(2, n);
y(1, :) = mu(3, :) ./ (mu(2, :) .^ (3/2));
y(2, :) = mu(4, :) ./ (mu(2, :) .^ 2) - 3;

fprintf('\n������ ���������: %g\n', mu(2, n));
fprintf('������ ��������������������� ��������: %g\n', sqrt(mu(2, n)));
fprintf('������ ������������ ����������: %g\n', y(1, n));
fprintf('������ ������������ ��������: %g\n', y(2, n));
