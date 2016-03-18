function B = meaneach(A, n);
    sum = 0;
    B = zeros(1,n);
    for i = 1:n
        sum = sum + A(i);
        B(i) = sum / i;
    end
