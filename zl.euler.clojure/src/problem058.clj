(ns problem058)
(use 'src.primes.primes)

(quote "Problem 58
	Starting with 1 and spiralling anticlockwise in the following way, 
	a square spiral with side length 7 is formed.
			37 36 35 34 33 32 31
			38 17 16 15 14 13 30
			39 18  5  4  3 12 29
			40 19  6  1  2 11 28
			41 20  7  8  9 10 27
			42 21 22 23 24 25 26
			43 44 45 46 47 48 49
	It is interesting to note that the odd squares lie along the bottom right diagonal, 
	but what is more interesting is that 8 out of the 13 numbers lying along both diagonals are prime; 
	that is, a ratio of 8/13 ≈ 62%.
	If one complete new layer is wrapped around the spiral above, 
	a square spiral with side length 9 will be formed. If this process is continued, 
	what is the side length of the square spiral for which the ratio of primes along both diagonals 
	first falls below 10%?"
)

(set! *print-length* 102)
;(def known-primes (into (sorted-set) (primes 1000000))) ; count = 78498
(def known-primes (into (sorted-set) (sieve 27000)))	; count = 664579
(def max-prime (apply max known-primes))	; 9999991


(defn is-prime? 
	[prime]
		(if (< prime max-prime) 
			(known-primes prime)
			(not (some #(zero? (rem prime %)) known-primes)))  
)

(defn count-primes [primes]
	(count (filter is-prime? primes))
)

(defn problem058 []
	;(loop [curr-num 1, curr-diff 2, primes-count 0, total-count 1]
	(loop [curr-num 49, curr-diff 8, primes-count 8, total-count 13]
		;(println curr-num curr-diff primes-count total-count (* 100.0 (/ primes-count total-count)))
		(if (< (/ primes-count total-count) 0.1)
			(dec curr-diff)
			(let [p1 (+ curr-num curr-diff),	p2 (+ p1 curr-diff), p3 (+ p2 curr-diff), sqr (+ p3 curr-diff)]
				(recur sqr (+ 2 curr-diff) (+ primes-count (count-primes [p1 p2 p3])) (+ 4 total-count)))))   
)

;"Elapsed time: 224961.933083 msecs"
; ...
; 688590081 26242 5248 52481 9.999809454850327
; 26241
