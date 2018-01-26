function lab01(w,w1,w2,t)
    w = tf(w{:});
    w1 = tf(w1{:});
    w2 = tf(w2{:});

    w
    if length(zero(w)) > length(pole(w))
        disp('More zeros than poles');
    else
        figure
        subplot(3,2,1)
        step(w)
        subplot(3,2,2)
        impulse(w)
        subplot(3,2,3)
        bode(w)
        subplot(3,2,4)
        nyquist(w)
        subplot(3,2,[5 6])
        step(w,w1,w2,t);
    end 
end