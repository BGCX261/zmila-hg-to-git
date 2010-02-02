(ns problem56)

(quote 
"Considering natural numbers of the form, a^(b), where a, b < 100, what is the maximum digital sum?"
)

(defn power 
	([a n] (if (= n 1) a (power a n 1)))
	([a n accu] 
		(if (zero? n) 
			accu 
			(recur a (dec n) (* accu a))))
)

(defn sum-digits 
	([n] (sum-digits 0 n)) 
	([acc n] 
		(if (zero? n) 
			acc
			(let [r (rem n 10) q (quot n 10)]
				(recur (+ acc r) q))))
) 

(defn problem56 []
	(reduce max (for [a (range 2 101), b (range 2 101)] (sum-digits (power a b))))
)
; 972
