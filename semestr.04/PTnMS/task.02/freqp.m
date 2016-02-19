function y = freqp(v,m)
    cnt = 0;
    for i = 1:m
        if v(i) == 1
            cnt = cnt + 1;
        end
    end
    y = cnt/m;