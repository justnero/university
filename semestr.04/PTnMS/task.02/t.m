n = 1000;
L = rand(4,n); % Случайные данные для эксперимента

% Независимые события
for i = 1:n
	A(i) = logzn(0.3,0.7,L(1,i)); 
	B(i) = logzn(0.3,0.4,L(2,i));
	C(i) = logzn(0.5,0.9,L(3,i));
end;
F = (~A&~B | B&C); % Булевая функция
Pnz = mean(F) % Вероятность для независимых событий
for j = 1:n
   Qnz(j) = freqp(F,j); % Частоты для независимых событий
end
figure;
plot(Qnz); % График для независимых событий
grid on;
xlabel('The number of experiments');
ylabel('Frequency');

% Зависимые события
for i = 1:n
	A1(i) = logzn(0.3,0.7,L(4,i)); 
	B1(i) = logzn(0.3,0.4,L(4,i));
	C1(i) = logzn(0.5,0.9,L(4,i));
end;
F1 = (~A1&~B1 | B1&C1); % Булевая функция
Pz = mean(F1) % Вероятность для зависимых событий
for j = 1:n
      Qz(j) = freqp(F1,j); % Частоты для зависимых событий
end
figure;
plot(Qz); % График для зависимых событий
grid on;
xlabel('The number of experiments')
ylabel('Frequency')
