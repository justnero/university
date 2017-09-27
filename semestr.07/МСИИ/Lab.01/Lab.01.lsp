(defun lab1 (a b c) (let ((x (list a b c))) (cons (/ (+ (car x) (caddr x)) 2) (cons (/ (+ (car x) (cadr x)) 2) NIL))))

(print (lab1 1 2 3))
(print (lab1 3 4 5))
(print (lab1 112 512 255))