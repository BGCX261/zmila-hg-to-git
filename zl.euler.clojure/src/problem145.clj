(ns src.problem145)

(comment Problem145
"Some positive integers n have the property that the sum [ n + reverse(n) ] 
consists entirely of odd (decimal) digits. 
For instance, 36 + 63 = 99 and 409 + 904 = 1313. 
We will call such numbers reversible; so 36, 63, 409, and 904 are reversible. 
Leading zeroes are not allowed in either n or reverse(n).
There are 120 reversible numbers below one-thousand.
How many reversible numbers are there below one-billion (10^(9))?"
)

(defn sum-count [coll]
	"sum of counts of elements"
	(reduce (fn [a b] (+ a (count b))) 0 coll))
;(= 10 (sum-count [[2 4 6 8] [2 4 6] [2 4] [2]]))

; one-digit - 0

; eo
(def odd-even-no-shift {1 [2 4 6 8], 3 [2 4 6], 5 [2 4], 7 [2]})
; EO 
(def odd-even-with-shift {3 [8], 5 [6 8], 7 [4 6 8], 9 [2 4 6 8]})
; d 
(def can-double-no-shift [0 1 2 3 4])

(defn odd-all? [n]
	(if (< n 10) (odd? n)
		(and (odd? (rem n 10)) (odd-all? (quot n 10)))))


(defn co-num [v]
	(reduce #(+ (* %1 10) %2) 0 v)
	 ;(+ (* 10 n) f)))) 
)

(defn rev-num 
	([n] (rev-num n []))
	([n rev] (if (zero? n) (co-num rev) (recur (quot n 10) (conj rev (rem n 10)) )))
)
	
(defn reversible [from to]
	(for [x (range from to) 
			:when (not= 0 (rem x 10)) 
			:when (odd-all? (+ x (rev-num x)))] x )
)

(defn reversible1 [from to]
	(for [x (range from to) 
			:when (odd-all? (+ x (rev-num x)))] x )
)

(def mapping-2-w-0 {0 [1 3 5 7 9], 1 [0 2 4 6 8], 2 [1 3 5 7], 3 [0 2 4 6], 
					4 [1 3 5], 5 [0 2 4], 6 [1 3], 7 [0 2], 8 [1], 9 [0]})
; ad 30
(def two-digits-with-zero
	(count 
		(for [a (keys mapping-2-w-0), d (mapping-2-w-0 a)] (co-num [a d])))
)

; two digits = 20
(def two-digits  
	(* 2 (sum-count (vals odd-even-no-shift)))
)	; one digit must be odd the second - even. shift disabled
; eo oe

; three digits = 100
(def three-digits 
	(* 2 (sum-count (vals odd-even-with-shift)) 
			(count can-double-no-shift))
) ; first digit must be odd, the last - even. 
; shift is needed to make middle digit double into odd
; EdO OdE

; four digits = 600
(def four-digits 
		(* two-digits two-digits-with-zero)
)

; abcd 900
(def four-digits-with-zero
	(* two-digits-with-zero two-digits-with-zero)
;	(for [a (keys mapping-2-w-0), d (mapping-2-w-0 a),
;				b (keys mapping-2-w-0), c (mapping-2-w-0 b)] (co-num [a b c d]))
)


; five digits	= 0
(def five-digits 0	 
) ; eEdOo middle-three have no shift from side two

; six digits = 18000
(def six-digits
	(* two-digits four-digits-with-zero)
)


; seven digits = 50000 = (* 20 20 25 5)
(def seven-digits
	(let [ag {2 [9], 3 [8],  4 [7 9], 5 [6 8],  6 [5 7 9], 7 [4 6 8],  8 [3 5 7 9], 9 [2 4 6 8]},
		ce ag,
		bf {0 [0 2 4 6 8], 1 [1 3 5 7], 2 [0 2 4 6], 3 [1 3 5], 4 [0 2 4], 5 [1 3],
				6 [0 2], 7 [1], 8 [0]},
		dd [0 1 2 3 4]
	]
		;(println (sum-count (vals ag)) (sum-count (vals ce)) (sum-count (vals bf)) (count dd)) 
	(* (sum-count (vals ag)) (sum-count (vals ce)) (sum-count (vals bf)) (count dd))
		;(count (for [a (keys ag) g (ag a)
		;			c (keys ce) e (ce c)
		;			b (keys bf) f (bf b)
		;			d dd]
		;			(co-num [a b c d e f g])))
	)
) ; abcDefg : a+g>10, c+e>10, b+f<10, b+f=2n, d+d<10


; eight digits = 540000
(def eight-digits
	(* two-digits four-digits-with-zero two-digits-with-zero)
)

; nine digits = 
(def nine-digits 0
)

(def problem145	; = 608720
	(+ two-digits three-digits four-digits five-digits 
		six-digits seven-digits eight-digits nine-digits)
)

'(println two-digits three-digits four-digits five-digits six-digits seven-digits eight-digits nine-digits)
;20 100 600 0 18000 50000 540000 0
