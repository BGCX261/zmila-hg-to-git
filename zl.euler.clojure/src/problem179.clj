(ns src.problem179)

;;; variant, based on sieve
;;; "Elapsed time: 62572.888643 msecs" 

;;;TODO need to check indices, gives incorrect result

(def N 10000000);
(def N2 (int (Math/sqrt N)))

(defn inc-nth [arr ind inc]
	(assoc! arr ind (+ inc (arr ind))))

(defn update-nodiv [nodiv start step]
	(if (> start N) 
		nodiv 
		(recur 
			(inc-nth nodiv start 2)
			(+ start step)
			step))
)

(defn calc-numbers-of-divisors []
	(loop [i 2 nodiv (transient (into [] (take (inc N) (repeat 1)))) ]
		(if (> i N2)
			(persistent! nodiv)
			(let [i2 (* i i), j (inc i2)] 
				(recur (inc i) (update-nodiv (inc-nth nodiv i2 1) j i)))))
)

(defn calc-number-of-same-divisors-count []
	(loop [count 0, prev 0, nodiv (drop 2 (calc-numbers-of-divisors))]
		(let [curr (first nodiv)]
			(if nodiv 
				(do 
					;(println (inc curr))
					(recur 
						(if (= curr prev) (inc count) count)
						curr
						(next nodiv)))
				count)))
)
