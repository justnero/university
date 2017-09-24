(defun lab1 (x) (list (/ (+ (car x) (caddr x)) 2) (/ (+ (car x) (cadr x)) 2)))

(print (lab1 `(1 2 3)))
(print (lab1 `(3 4 5)))
(print (lab1 `(112 512 255)))