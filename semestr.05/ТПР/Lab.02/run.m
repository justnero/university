clear;
clc;
x = [30 50 80 25;
     45 30 20 55];
v1 = 25:7.5:100;
v2 = 20:6:80;
ud = 0:10;

k1 = sort(1./v1);
k2 = sort(1./v2);
indiff(k1, k2);
[u1, u2] = aprox(k1, k2, ud);
j = (k2(2) - k2(1)) / (k1(2) - k2(1) - k1(1) + k2(2));
u = [0 0 0 0];
imax = 0;
for i = 1:4
    u(i) = polyval(u1,1/x(1,i))*j + polyval(u2,1/x(2,i))*(1-j);
    fprintf('\tVariant %d:\n', i);
    fprintf('Price: %d\n', x(1, i));
    fprintf('Mileage: %d\n', x(2, i));
    fprintf('Utility %.6f\n', u(i));
    if imax == 0 || u(i) > u(imax) 
        imax = i;
    end
end

fprintf('\n');
fprintf('\tBest:\n');
fprintf('Price: %d\n', x(1, imax));
fprintf('Mileage: %d\n', x(2, imax));
fprintf('Utility %.6f\n', u(imax));
