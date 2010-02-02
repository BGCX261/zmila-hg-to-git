(ns problem72)

;(count (set (for [ d (range 2 1000001) n (range 1 d)] (/ n d))))
; see http://mathworld.wolfram.com/FareySequence.html

(defn gcd [a b] (if (zero? b) a (recur b (rem a b)))) 

(defn divide 
	"return [newN new list of [factor pow]]"
	([factor [n factors]] (divide 0 factor n factors))
	([pow factor n factors]
		(if (zero? (mod n factor))
			(recur (inc pow) factor (quot n factor) factors)
			(if (zero? pow) 
				(vector n factors) 
				(vector n (cons [factor pow] factors)) )))
)

(def wheel-size 6)

(defn factorize 
	([n] (factorize wheel-size (divide 3 (divide 2 [n nil]))))
	([c [n factors]]
		(let [c-1 (dec c), c+1 (inc c), 
					[n1 factors1] (divide c+1 (divide c-1 [n factors]))]
				(if (= n1 1)
					factors1
					(recur (+ c wheel-size) [n1 factors1]))))
)


(defn phi [n] 
	"phi(n) 	+0 	+1 	+2 	+3 	+4 	+5 	+6 	+7 	+8 	+9
		0+ 	  			1 	1 	2 	2 	4 	2 	6 	4 	6
		...
		90+ 		24 	72 	44 	60 	46 	72 	32 	96 	42 	60"
	(reduce (fn [acc [p e]] (* acc (dec p) (Math/pow p (dec e)))) 1 (factorize n))
)

(defn farey [n]
	"The Farey sequence F_n for any positive integer n 
	is the set of irreducible rational numbers a/b with 0<=a<=b<=n and gcd(a,b)=1 
	arranged in increasing order."
	(reduce (fn [acc k] (+ acc (phi k))) 1 (range 1 (inc n)))
)

;TODO generate list of primes, implement quick factorize
