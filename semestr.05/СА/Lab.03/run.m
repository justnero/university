clc;
clear;
close all;

inputDate = [20; 25; 30; 15; 10; 27; 14; 21; 23; 25; 27; 24; 24; 20; 15; 12; 11; 9; 14; 10]';
index = min(inputDate):max(inputDate);
[sort, countSort] = countingSort(inputDate);

sumCount = sum(countSort);
relative = countSort/sumCount;

fprintf('\tInput\n');
disp(inputDate)

fprintf('\tSorted\n');
disp(sort)

fprintf('Statistical discrete series of frequency distribution\n');
fprintf('Indices:   '); 
disp(index)

fprintf('Frequency: '); 
disp(countSort)

fprintf('Relative frequences distribution\n');
disp([index; relative])

subplot(2, 1, 1);
title('Frequencies polygon');
hold on;
plot(index, countSort, index, countSort, 'o');
ylim([0 max(countSort)+0.3]);
grid on;

subplot(2, 1, 2);
title('Relative frequencies polygon');
hold on;
ylim([0 1]);
grid on;
plot(index, relative, index, relative, 'o');
