(ns src.problem112)

(set! *print-length* 602)

(defn check-for-inc [n0 r0]
	(if (zero? n0) :<
		(let [r1 (rem n0 10)  n1 (quot n0 10)]
			(if	(<= r1 r0) (check-for-inc n1 r1)
				:-))))

(defn check-for-dec [n0 r0]
	(if (zero? n0) :>
		(let [r1 (rem n0 10)  n1 (quot n0 10)]
			(if	(>= r1 r0) (check-for-dec n1 r1)
				:-))))

(defn b-type 
	([n] 	
		(let [r0 (rem n 10)  n0 (quot n 10)] (b-type n0 r0)))
	([n0 r0]
		(if (zero? n0) :=
				(let [r1 (rem n0 10)  n1 (quot n0 10)]
					(cond 
						(< r1 r0) (check-for-inc n1 r1)
						(> r1 r0) (check-for-dec n1 r1)
						:else 		(b-type n1 r1)))))
)

(defn b-types [n]
	(lazy-seq (cons [n (b-type n)] (b-types (inc n)))))

(defn bt-proportions [n b]
	(lazy-seq
		(let [bt (b-type n) b1 (if (= :- bt) (inc b) b)] 
		(cons 
			[b1 n (/ b1 n)] 
			(bt-proportions (inc n) b1))))
)
(take 100 (drop 400  (bt-proportions 99 0)))

(time 
(take 3 (drop-while (fn [ [b n r] ] (not (== r 0.99))) (bt-proportions 99 0))) 
)
