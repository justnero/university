function a = myplot(V, str)    
    figure
    
    subplot(2,1,1);
    plot(V);
    grid on;
    title(str(1));
    xlabel(str(2));
    ylabel(str(3));
    
    subplot(2,1,2);
    semilogx(V);
    grid on;
    xlabel(str(2));
    ylabel(str(3));