clc;
clear;
n = 2;
X1 = [3 3 3 6 4 4 9 9 1 1 1 9 9 2 2 2 8 5 5 5 3 4 4 7 7 2 10 10 7 7];
X2 = [6 6 6 4 7 7 1 1 10 10 10 2 2 9 9 9 1 5 5 5 7 6 6 3 3 8 2 2 4 4];
Y  = [0.016 0.015 0.014 0.014 0.013 0.013 0.011 0.012 0.012 0.017 0.015 0.009 0.010 0.014 0.018 0.016 0.009 0.013 0.011 0.014 0.016 0.012 0.011 0.013 0.012 0.011 0.010 0.009 0.010 0.011 ];
Ynominal = [0.009 0.01 0.011 0.01];
absX1 = [60 80]; 
absX2 = [0.5 1];

fun1 = @(x)(absX1(2)-absX1(1))*x/100.0 + absX1(1);
fun2 = @(x)(absX2(2)-absX2(1))*x/100.0 + absX2(1);

X1 = fun1(X1);
X2 = fun2(X2);

X = [X1; X2];

Y_X1 = Y.*X1;
Y_X2 = Y.*X2;

B = [sum(Y) sum(Y_X1) sum(Y_X2)]';

X = [ones(1, length(X1)); X]';
XT = X';
A = XT*X;

A(1,1) = length(X1);

b = mldivide(A, B);
fun = @(x1, x2)b(1)+b(2)*x1+b(3)*x2;
Y2 = fun(X1, X2);

syms x1 x2 f;
f = collect([1 x1 x2]*b);

fprintf('-- System regression:\n');
fprintf('f(x1, x2) = ');
pretty(vpa(collect(f), 3))

x = linspace(min(X1), max(X1));
y = linspace(min(X2), max(X2));
z = fun(x, y);
plot3(x,y,z, X1, X2, Y, 'o');
grid on;

index = cell(1, length(X1)+2);
ind = 1:(length(X1));
for i=1:length(X1) 
    index(i) = {ind(i)};
end
index(length(X1)+1) = {' SUM '};
index(length(X1)+2) = {' AVG '};
X1sqr = X1.^2;
X2sqr = X2.^2;
YX1 = Y_X1;
YX2 = Y_X2;
X1X2 = X1.*X2;
Ysqr  = Y .^2;
Y_Y2 = Y-Y2;
Y_Y2sqr = Y_Y2.^2;
A = abs(Y_Y2./Y).*100;

X1 = [X1 sum(X1) mean(X1)];
X2 = [X2 sum(X2) mean(X2)];
Y = [Y sum(Y) mean(Y)];
Y2 = [Y2 sum(Y2) mean(Y2)];
X1sqr = [X1sqr sum(X1sqr) mean(X1sqr)];
X2sqr = [X2sqr sum(X2sqr) mean(X2sqr)];
YX1 = [YX1 sum(YX1) mean(YX1)];
YX2 = [YX2 sum(YX2) mean(YX2)];
X1X2 = [X1X2 sum(X1X2) mean(X1X2)];
Ysqr  = [Ysqr sum(Ysqr) mean(Ysqr)];
Y_Y2 = [Y_Y2 sum(Y_Y2) mean(Y_Y2)];
Y_Y2sqr = [Y_Y2sqr sum(Y_Y2sqr) mean(Y_Y2sqr)];
A = [A sum(A) mean(A)];

index = index';
X1 = X1';
X2 = X2';
Y = Y';
X1sqr = X1sqr';
X2sqr = X2sqr';
YX1 = YX1';
YX2 = YX2';
X1X2 = X1X2';
Ysqr = Ysqr';
Y2 = Y2';
Y_Y2 = Y_Y2';
Y_Y2sqr = Y_Y2sqr';
A = A';

MainTable = table(index, X1, X2, Y, X1sqr, X2sqr, YX1, YX2, X1X2, Ysqr, Y2, Y_Y2, Y_Y2sqr, A);
disp(MainTable);

f1 = solve(f-Ynominal(1));
f2 = solve(f-Ynominal(2));
f3 = solve(f-Ynominal(3));
f4 = solve(f-Ynominal(4));
F = [f1; f2; f3; f4];
F = subs(F, x2, absX2(1));

Ynom = Ynominal';
X1 = double(F);
X2 = double(F);
X2(:) = absX2(1);
SearchYnominal = table(Ynom, X1, X2);
disp(SearchYnominal);