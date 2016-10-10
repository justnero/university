function [u1, u2] = aprox(K1, K2, U)
    u1 = polyfit(K1, U, 2);
    u2 = polyfit(K2, U, 2);
    x1 = linspace(min(K1), max(K2), 100);
    x2 = linspace(min(K1), max(K2), 100);
    y1 = polyval(u1, x1);
    y2 = polyval(u2, x2);
    figure;
    subplot(1, 2, 1);
    plot(K1, U, '+', x1, y1);
    xlabel('K');
    ylabel('U');
    grid on;
    subplot(1, 2, 2);
    plot(K2, U, 'o', x2, y2);
    xlabel('K');
    ylabel('U');
    grid on;
end