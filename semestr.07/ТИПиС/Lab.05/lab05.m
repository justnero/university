clear
clc

% t
fs = 6e3;
t = 0:1/fs:5;
t = t';

% s1
A = 3; 
phi = pi/4; 
f = 5;
s1 = A*sin(2*pi*f*t+phi);
build(1, s1, t, fs);

% s2
alpha = 2;
s2 = exp(-alpha*t).*s1;
build(2, s2, t, fs);

% s3
s3 = rectpuls(t);
build(3, s3, t, fs);

% s4
s4 = tripuls(t);
build(4, s4, t, fs);

% s5
s5 = sinc(t);
build(5, s5, t, fs);

% s6
s6 = gauspuls(t);
build(6, s6, t, fs);

% s7
s7 = pulstran(t, 0:1/3:1, 'tripuls');
build(7, s7, t, fs);

% new t
T = 10*(1/50);
Fs = 4000;
dt = 1/Fs;
t = 0:dt:T-dt;

% s8
s8 = sawtooth(2*pi*50*t);
build(8, s8, t, Fs);

% s9
s9 = square(t);
build(9, s9, t, Fs);

% s10
s10 = diric(t, 3);
build(10, s10, t, Fs);

% s11
s11 = chirp(t, 4000, 200, 5000);
build(11, s11, t, Fs);

% s12
[y,Fs]=audioread('tada.wav');
sound(y,Fs);
figure(1);
subplot(4, 3, 12);
plot(y);
title('S12');
len = length(y);
F = (0:len-1)/(len/96000);
figure(2);
subplot(4, 3, 12);
plot(F, abs(fft(y)));
title('S12');