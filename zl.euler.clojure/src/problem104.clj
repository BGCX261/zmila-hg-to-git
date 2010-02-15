(ns problem104)

;(dotimes [n 1000] (println n (time (fib n))))

(quote 
"The Fibonacci sequence is defined by the recurrence relation:
    F_(n) = F_(n−1) + F_(n−2), where F_(1) = 1 and F_(2) = 1.
It turns out that F_(541), which contains 113 digits, is the first Fibonacci number 
for which the last nine digits are 1-9 pandigital (contain all the digits 1 to 9, 
but not necessarily in order). 
And F_(2749), which contains 575 digits, is the first Fibonacci number 
for which the first nine digits are 1-9 pandigital.

Given that F_(k) is the first Fibonacci number for which 
the first nine digits AND the last nine digits are 1-9 pandigital, find k.
"
)

(defn first-9-digits [n] 
	(if (< n 1000000000) 
		n 
		(recur (quot n 10))))
		

(defn last-9-digits [n] 
	(rem n 1000000000)) 

(defn pandigital? 
	([n] (pandigital? n 0 #{}))
	([n size digits] 
		(if (zero? n) 
			(= size (count digits))
			(let [digit (rem n 10) n1 (quot n 10)]
				(if (or (zero? digit) (digits digit)) 
					false
					(recur n1 (inc size) (conj digits digit))))))
)


(defn pandigital19? 
	([n] (and (>= n 123456789) (<= n 987654321) (pandigital19? n #{}) ))
	([n digits] 
		;(println "pandigital" n size digits)
		(if (= n 0) 
			(= 9 (count digits))
			(let [digit (rem n 10) n1 (quot n 10)]
				(if (or (zero? digit) (digits digit)) 
					false
					(recur n1 (conj digits digit))))))
)


(defn print-fib [cond]
	"F(n) = F(n−1) + F(n−2), where F(1) = 1 and F(2) = 1."
	(loop [prev 1, curr 1, k 2] 
		(println k curr)
		(if (< k 101) 
			(recur curr (+ prev curr) (inc k))))
)

(defn find-fib [cond]
	"F(n) = F(n−1) + F(n−2), where F(1) = 1 and F(2) = 1."
	(loop [prev 1, curr 1, k 2] 
		;(println "find-fib" prev curr k)
		(when (zero? (mod k 10000)) (println k)) 
		(if (cond curr) 
			(list k curr prev) ; found!
			(recur curr (+ prev curr) (inc k))))
)

(defn calc-next-front [ [curr prev] ]
	"return next end curr Fib, keep first 13 digits"
	(let [next (+ curr prev)]
		(if (> next 100000000000000000)
			[(quot next 100) (quot curr 100)]
			[next curr]))
)


(defn calc-next-end [ [curr prev] ]
	"return next end curr Fib, keep last 10 digits"
	(let [next (+ curr prev)]
		(if (> next 10000000000)
			[(rem next 10000000000) (rem curr 10000000000)]
			[next curr]))
)

(defn find-fib-not-exact [cond]
	"F(n) = F(n−1) + F(n−2), where F(1) = 1 and F(2) = 1."

	; 'front and 'end = [currFib prevFib], k = index of currFib
	(loop [front [1 1], end [1 1], k 2]	  
		
		(when (zero? (mod k 10000)) (println k))
		;(println k front end) 
		
		(if (cond k (first front) (first end)) 
			(list k (first front) (first end)) ; found!
			
			(let [next-front (calc-next-front front) 
						next-end (calc-next-end end)]
				(recur next-front next-end (inc k)))))
)

(defn problem-104-condition [k currFront currEnd]
	(and
		(pandigital19? (last-9-digits currEnd))
		(pandigital19? (first-9-digits currFront))
	)
)

; test
;(541 ...839725641)
;(2749 143726895...)
;(329468 245681739...352786941)

;(find-fib-not-exact problem-104-condition)

