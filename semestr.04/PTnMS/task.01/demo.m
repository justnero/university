clear
clc
set(0,'defaultAxesFontName', 'HelveticaNeueCyr-Light')
set(0,'defaultTextFontName', 'HelveticaNeueCyr-Light')

m = 5;
n = 1000;
am = [0.47 0.47 0.47 0.95 0.02];
aM = [0.97 0.97 0.97 1.00 0.93];
str = {''
       'Количество экспериментов'
       'Частота'};
leg = {'0.47-0.97','0.47-0.97','0.47-0.97','0.95-1.00','0.02-0.93'};
    
a = rand(m,n);
z = mlogzn(am,aM,a,m,n);
v = freqs(z,m,n);
myplot(v, str, leg);