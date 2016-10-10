function indiff(K1, K2)
    a = zeros(4);
    b = zeros(4);
    d = zeros(4, 100);
    e = zeros(4, 100);
    for i=2:4
        for j=0:i-1
            a(i, j+1) = K1(j+1);
            b(i, j+1) = K2(i-j);
        end
        c = polyfit(a(i,1:i), b(i,1:i), i-1);
        d(i,:) = linspace(min(a(i, 1:i)), max(a(i, 1:i)), 100);
        e(i,:) = polyval(c, d(i,:));
    end
    figure;
    plot(d(:,:)', e(:,:)', a(:,:)', b(:,:)', 'o');
    xlabel('K1');
    ylabel('K2');
    grid on;
end

