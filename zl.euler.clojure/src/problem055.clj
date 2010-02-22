(ns src.problem055)

(set! *print-length* 52)

(defn rever 
	([n] (rever 0 n))
	([rev n]
		(if (zero? n) rev
			(let [r (rem n 10) q (quot n 10)]
				(recur (+ (* 10 rev) r) q))))
)

(defn rever-n-add [n]
	(let [ra (+ n (rever n))]
		(lazy-seq (cons ra (rever-n-add ra))))
)

(defn palindrom? 
	([n] (let [arr (into [] (str n))] (palindrom? arr 0 (dec (count arr)))))
	([arr start end] 
		(cond (>= start end) true
				 	(not= (nth arr start) (nth arr end)) false
				 	:else (recur arr (inc start) (dec end))))
)

(defn is-lychrel? 
	([n] (is-lychrel? (+ n (rever n)) 1))
	([n cnt]
		(cond  
			(== cnt 50)	true
			(palindrom? n) (do (println cnt n) false)
			:else (let [ra (+ n (rever n))]
							(recur ra (inc cnt)))))
)

(time
(count 
	(filter is-lychrel? (range 1 10000))
)
)
; 249


