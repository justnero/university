n = 1000;
L = rand(4,n); % ��������� ������ ��� ������������

% ����������� �������
for i = 1:n
	A(i) = logzn(0.3,0.7,L(1,i)); 
	B(i) = logzn(0.3,0.4,L(2,i));
	C(i) = logzn(0.5,0.9,L(3,i));
end;
F = (~A&~B | B&C); % ������� �������
Pnz = mean(F) % ����������� ��� ����������� �������
for j = 1:n
   Qnz(j) = freqp(F,j); % ������� ��� ����������� �������
end
figure;
plot(Qnz); % ������ ��� ����������� �������
grid on;
xlabel('The number of experiments');
ylabel('Frequency');

% ��������� �������
for i = 1:n
	A1(i) = logzn(0.3,0.7,L(4,i)); 
	B1(i) = logzn(0.3,0.4,L(4,i));
	C1(i) = logzn(0.5,0.9,L(4,i));
end;
F1 = (~A1&~B1 | B1&C1); % ������� �������
Pz = mean(F1) % ����������� ��� ��������� �������
for j = 1:n
      Qz(j) = freqp(F1,j); % ������� ��� ��������� �������
end
figure;
plot(Qz); % ������ ��� ��������� �������
grid on;
xlabel('The number of experiments')
ylabel('Frequency')
