(ns src.problem113)

(defn next-inc [[f & r]]
	(if (nil? r) 
		[f] 
		(let [res (next-inc r)]  (cons (+ f (first res)) res)))
)

(defn next-dec 
	([ [f & r] ]  (cons f (next-dec f r)))
	([ p [f & r]] 
		(if f	(let [s (+ p f)]  (cons s (next-dec s r)))))
)

(defn sum-inc-dec [incs decs]
	(+ (apply + (next incs)) (apply + decs) -9)
		; (next incs) - don't calculate numbers starting with 0
)

(defn problem113 [limit]
	(loop [incs [0 1 1 1 1 1 1 1 1 1],
				decs [1 1 1 1 1 1 1 1 1 0],
				curr 1,
				sum (sum-inc-dec incs decs)]
		(if (< curr limit)
			(let [n-i (next-inc incs), n-d (next-dec decs)]
				(recur n-i n-d (inc curr) (+ sum (sum-inc-dec n-i n-d))))
			sum))
)

;(time (problem113 100))
;"Elapsed time: 9.888613 msecs"
;51161058134250
