function z = mlogzn(am,aM,a,m,n)
    for i = 1:m
        for j = 1:n
            z(i,j) = logzn(am(i),aM(i),a(i,j));
        end
    end