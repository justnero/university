(defvar *db* nil)

(defun insert (dest train time)
  (push (list :dest dest :train train :time time) *db*)
 )

(defun savef (filename)
  (with-open-file (out filename :direction :output :if-exists :supersede)
    (with-standard-io-syntax
      (print *db* out)
     )
   )
 )

(defun loadf (filename)
  (with-open-file (in filename)
    (with-standard-io-syntax
      (setf *db* (read in))
     )
   )
 )

(defun select* ()
  (format t "~%")
  (format t "~{~{~a:~10t~a~%~}~%~}" *db*)
 )

(defun where(&key dest train time)
  #'(lambda (row)
       (and 
         (if dest  (equal (getf row :dest)  dest)  t)
         (if train (equal (getf row :train) train) t)
         (if time  (equal (getf row :time)  time)  t)
        )
     )
 )

(defun update (where-func &key dest train time)
  (setf *db*
    (mapcar
     #'(lambda (row)
         (when (funcall where-func row)
           (if dest  (setf (getf row :dest)  dest))
           (if train (setf (getf row :train) train))
           (if time  (setf (getf row :time)  time))
          )
         row
          )
      *db*
     )
   )
 )

(defun selectByDest (_dest)
  (if (eq nil (setf rows (remove-if-not #'(lambda (row) (equal (getf row :dest) _dest)) *db*)))
      (print "Поезда туда не ездят")
      (print rows)
   )
 )