function freqplot(b,m,n)
    for i = 1:m
        for j = 1:n
            c(j,i) = freqp(b(i,1:j),j);
        end
    end
    plot(c);
    grid on;
    legend('1','2','3','4','5',0);