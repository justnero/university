function freqplot(b,m,n,islog)
    for i = 1:m
        for j = 1:n
            c(j,i) = freqp(b(i,1:j),j);
        end
    end
    if(islog == 1)
        semilogx(c);
    else
        plot(c);
    end
    grid on;
    xlabel('The number of experiments')
    ylabel('Frequency')
    legend('0.47-0.97','0.47-0.97','0.47-0.97','0.95-1.00','0.02-0.93',0);
