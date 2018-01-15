function build(i, s, t, fs)
    figure(1);
    subplot(4, 3, i);
    plot(t,s);
    title(sprintf('S%d', i));
    len = length(s);
    F = (0:len-1)/(len/fs)/1e3;
    figure(2);
    subplot(4, 3, i);
    ft = abs(fft(s));
    plot(F, ft);
    title(sprintf('S%d', i));
end