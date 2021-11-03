#|
Author: Jarod Bartlett
Date: 2/7/2021
Description: Working with and getting familiar with Scheme and the Functional Paradigm.
|#

;Part 1
;;;Each of these functions executes the given math functions
(define run1 (lambda () (+ 1 2 3)))
(define run2 (lambda () (+ 30 (* 3 (+ 2 (/ 10 4))))))
(define run3 (lambda () (- 10 (+ (* 3 5) (+ 2 (* 0 5))))))
(define run4 (lambda () (* 5 (+ 4 (/ (+ (+ 10 10) (* 5 8)) (+ 10 2))))))
(define run5 (lambda () (+ (/ (- (/ (/ (* (+ 3 5) (+ 6 4)) 2) 2) 5) 3) (+ (/ (+ (* 2 10) (* 5 4)) 2) (* 4 5)))))

;Part 2
;;;Calculates ff6 damage using floor and math functions
(define how-much-damage-ff6? (lambda (damage defense) (floor (+ (/ (* damage (- 255 defense)) 256) 1))))

;;;Calculates ff7 base attack 
(define base-attack-ff7 (lambda (strength weapon-bonus) (+ strength weapon-bonus)))

;;;Calculates ff7 base damage using floor and math functions
(define base-damage-ff7 (lambda (level base-attack) (+ (* (floor (/ (+ base-attack level) 32)) (floor (/ (* base-attack level) 32))) base-attack)))

;;;Calculates ff7 power 
(define calculate-power-ff7 (lambda (power base-damage) (* power (/ base-damage 16))))

;;;Calculates ff7 damage using floor and math functions
(define how-much-damage-ff7? (lambda (defense power) (floor (* power (/ (- 512 defense) 512)))))

;;;Calculates ff7 attack damage by using the appropriate previously defined function as input
(define get-attack-damage-ff7 (lambda (level strength weapon-bonus power defense) (how-much-damage-ff7? defense (calculate-power-ff7 power (base-damage-ff7 level (base-attack-ff7 strength weapon-bonus))))))

;Part 3
;;;Gets second item in list using car and cdr
(define get-second-item (lambda (list) (car (cdr list))))

;;;Gets third item in list using car and cdr
(define get-third-item (lambda (list) (car (cdr (cdr list)))))

;;;Gets length of a list. The base case returns 1 if the "back" of the list is empty, the recursive case adds 1 to the return value for each element removed before list is emptied.
(define list-length? (lambda (list) (if (equal? (cdr list) '())
                                        1 ;base case
                                        (+ 1 (list-length? (cdr list))) ;recursive case
                                        )
                       )
  )

;;;Returns a list with a certain number of elements removed. 
(define arbitrary-cdr (lambda (num list) (if (<= num 0)
                                             list ;if num is less than or equal to 0, returns list
                                             (if (< (list-length? list) num) ;if the given number is larger than the size of the list, returns #f
                                                 #f 
                                                 (if (= num 1)
                                                     list ;base case
                                                     (arbitrary-cdr (- num 1) (cdr list)) ;recursive case, keeps removing the front element from the list until num is 1.
                                                     )
                                                 )
                                             )
                        )
  )

;Part 4
;;;Checks if the given list is homogenous consisting of only numbers.
(define number-list? (lambda (list) (if (equal? list '())
                                        #t 
                                        (if (number? (car list)) ;if the first element of list is a number
                                            (number-list? (cdr list)) ;recursive case, keeps removing the checked front element from the list until the list is either empty or the new front element is not a number.
                                            #f ;base case
                                            )
                                        )
                       )
  )

;;;Interface function
(define sum-number-list (lambda (list) (sum-number-list-help list)))
;;;Helper function. Sums a list given the list consists of only numbers.
(define sum-number-list-help (lambda (list) (if (equal? list '())
                                                0 ;returns 0 if list is empty
                                                (if (number-list? list)
                                                    (+ (car list) (sum-number-list-help (cdr list))) ;recursive case, adds the value of the first element to the return value, removes said element from list, and calls function again with new list
                                                    #f ;base case
                                                    )
                                                )
                               )
  )