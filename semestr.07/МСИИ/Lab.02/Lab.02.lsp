(defun lab2_r (x) (let
                    ((y (cdr x)))
                    (cond
                        ((NULL y) x)
                        (t (let
                            ((z (lab2_r y)))
                            (cons (* (car x) (car z)) z)
                           )
                        )
                    )
                )
)

(defun lab2_i (x) (let 
					   ((l (length x)) (y NIL)) 
                       (dotimes (i l y)
                           (setq y (cons 
                                    (cond
                                        ((null y) (car (last x)))
                                        (t (setq x (butlast x)) (* (car y) (car (last x))))
                                     ) 
                                     y
                                    ))
                        ))
)

(trace lab2_r)
(print (lab2_r `(1 2 3 4 5 6)))
(untrace lab2_r)
(print (lab2_i `(1 2 3 4 5 6)))