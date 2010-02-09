(ns problem046)
(use 'src.primes.primes)

(quote "problem 46
	27 = 19 + 2×2^(2)
	33 = 31 + 2×1^(2)
	What is the smallest odd composite that cannot be written as the sum of a prime and twice a square?"
)

(defn primes2 [limit] 
	(next (primes limit)))

(defn squares2 [limit]
	(map #(* 2 % %) (range limit)))

(defn odds [limit]
	(into #{} (map #(+ (* 2 %) 1) (range 1 limit))))

(defn sums [limit]
	(into #{} 
		(for [p (primes2 limit), s (squares2 (int (Math/sqrt limit))) 
			:let [ps (+ p s)] :when (< ps limit)] 
			ps)))

(clojure.set/difference (odds 5000) (sums 10000))

; #{5993 5777} -> min = 5777
 