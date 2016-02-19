function demo()
    m = 5;
    n = 1000;
    am = [0.47 0.47 0.47 0.95 0.02];
    aM = [0.97 0.97 0.97 1.00 0.93];
    
    a = rand(m,n);
    b = mlogzn(am,aM,a,m,n);
    freqplot(b,m,n,1);