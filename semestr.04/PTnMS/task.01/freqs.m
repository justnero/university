function v = freqs(z,m,n)
    for i = 1:m
        for j = 1:n
            v(j,i) = sum(z(i,1:j))/j;
        end
    end