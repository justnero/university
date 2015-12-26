clear
clc

% ������ �� �������
t = [1 1.2]*10^(-9);
U = 5;

% �������
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

fprintf('\t\t�������:\n');
fprintf('f�� \t= %10.6f ���\n',f/(10^6));
fprintf('U� \t\t= %10.6f �\n',Up);
fprintf('I�� \t= %10.6f ��\n',I(1)*10^3);
fprintf('I���� \t= %10.6f ��\n',I(2)*10^3);
fprintf('I�� \t= %10.6f ��\n',I(3)*10^3);
fprintf('M \t\t= %10.6f > 1\n',M);
fprintf('T21� \t= %10.6f ��\n',tau*10^9);
fprintf('t10 \t= %10.6f ��\n',t10*10^9);
fprintf('t01 \t= %10.6f ��\n',t01*10^9);
fprintf('tp  \t= %10.6f ��\n',tp*10^9);
fprintf('t10 \t= %10.6f �� <= %10.6f ��\n',t10*10^9,t(1)*10^9);
fprintf('tv \t\t= %10.6f �� <= %10.6f ��\n',tv*10^9,t(2)*10^9);