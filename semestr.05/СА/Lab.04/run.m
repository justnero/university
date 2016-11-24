clear
clc
close all;

var = 11;
x = 1:8;
F1 = [var   var   var   var+1 var+1 var+2 var+2 var+3];
F2 = [var+2 var+3 var+4 var+1 var+2 var+2 var+3 var+1];
F = [F1; F2];

alpha = 0.3;
beta = 0.7;

F11 = F1.*alpha;
F12 = F2.*beta;
Fconvolution = F11+F12;
best1 = find(Fconvolution == min(Fconvolution));
 
indMinF2 = find(F2 == min(F2));
indMinF1 = find(F1(indMinF2) == min(F1(indMinF2)));
best2 = indMinF1;
 
fun2 = @(x1, x2) (x1(1) > x2(1) && x1(2) >= x2(2)) || (x1(1) >= x2(1) && x1(2) > x2(2));
ind = [];
for i=1:length(F)
    temp = i;
    for j=1:length(F)
        if i == j
            continue;
        end
        if fun2(F(:,i), F(:, j)) 
            temp = 0;
            break;
        end
    end;
    
    if temp ~= 0 
        ind = [ind temp];
    end
end;
 
fprintf('\tInput\n');
fprintf(' Variant: ');
disp(var);
fprintf(' Source data table\n');
disp([x; F]);
fprintf('\n');

fprintf('\tPart 1\n');
fprintf(' Let alpha be equal to: ');
disp(alpha);
fprintf(' Let beta  be equal to: ');
disp(beta);
fprintf(' Recalculated table: \n');
disp([x; F11; F12]);
fprintf(' Linear convolution: \n');
disp([x; Fconvolution]);
fprintf(' Minimal solution: \n');
disp(x(best1));
fprintf('\n');

fprintf('\tPart 2\n');
fprintf(' Minimal by criteria F2 solutions: \n');
disp([x(indMinF2); F(:, indMinF2)]);
fprintf(' Minimal solution: ');
disp(best2);
fprintf('\n');

fprintf('\tPart 3\n');
fprintf(' Pareto set\n');
disp(ind);
 
plot(F1, F2, 'bo', F1(ind), F2(ind) , 'r*');
hold on;
grid on;
hold off;
title('Criterial space');
legend('common solution','pareto-optimal solution');
xlim([min(F1)-1 max(F1)+1]);
ylim([min(F2)-1 max(F2)+1]);