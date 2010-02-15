(ns src.problem187)
(use 'src.primes.primes)

(set! *print-length* 102)


(defn primes-till-sqrt [limit]
	(let [s-l (int (Math/sqrt limit))]
		(reverse (take-while #(<= % s-l) (sieve limit)))))

(defn next-prime2 [n]
	"n must be odd"
	(loop [n (+ n 2)]
    (if (prime? n)
      n
      (recur (+ n 2))))
)

(defn problem187 [limit]
	(let [p (reverse (sieve (int (Math/sqrt limit))))  
				count-left (count p)
				left-triangle (quot (* count-left (inc count-left)) 2)]
	(loop [ [row & prev-rows :as rows] p
					col (next-prime2 row)
					curr-count 0
					curr-total 0]
		(cond 
			(= row nil)		(+ curr-total left-triangle)
			(< (* row col) limit) 
				(recur rows (next-prime2 col) (inc curr-count) curr-total)
			:else
				(do (println "done row" row col curr-count)
				(recur prev-rows col curr-count (+ curr-count curr-total))))))
)

;(time (count  
;	(let [p (sieve 5000)] 
;		(for [a p, b p :while (<= b a) :when (< (* a b) 10000)] [b a (* a b)]))
;))


; 100 - 35
; 1000 - 299
; 10000 - 2625
;(problem187 100000)
;23378 ;"Elapsed time: 2881.946197 msecs"

;(time (problem187 1000000)) 
;"Elapsed time: 30629.84669 msecs"
;210035


