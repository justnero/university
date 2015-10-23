function demo()
    m = 5;
    n = 1000;
    am = [0.3 0.3 0.3 0.2 0.06];
    aM = [0.8 0.8 0.8 0.25 0.96];
    
    a = rand(m,n);
    b = mlogzn(am,aM,a,m,n);
    freqplot(b,m,n,1);