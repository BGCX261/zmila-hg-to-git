(ns problem062)

(quote "Problem 62"
"The cube, 41063625 (345^(3)), can be permuted to produce two other cubes: 
	56623104 (384^(3)) and 66430125 (405^(3)). 
In fact, 41063625 is the smallest cube which has exactly three permutations of its digits 
which are also cube.
Find the smallest cube for which exactly five permutations of its digits are cube.")

(defn cube [start end]
	(if (= start end) (list (* start start start))
		(lazy-seq (cons (* start start start) (cube (inc start) end))))
)
;(cube 215 465)
;(9938375 10077696 ... 99897344 100544625)


(defn canon
	" canonical permutation hash: 127035954683 -> \"1112121111\" " 
	([n] (canon n (transient (into [] (repeat 10 0))) ))
	([n digits] 
		(if (zero? n) 
			(apply str (persistent! digits)) 
			(let [d (rem n 10)]
				(recur (quot n 10) (assoc! digits d (inc (digits d)))))))
)

; filter those canons, which is frequent enough > 2
(filter (fn [[canon freq]] (> freq 2)) 
	; convert list of canons into map: canon->freq
	(reduce 
		(fn [acc curr] (assoc acc curr (inc (get acc curr 0)))) 
		{}
		(map canon (cube 215 465)) )
)
;(["1111112000" 3])

; 12 digits: 4641 9999
; more-than-4-in-12-digits
(filter (fn [[canon freq]] (> freq 4)) 
	(reduce 
		(fn [acc curr] (assoc acc curr (inc (get acc curr 0)))) 
		{}
		(map canon (cube 4641 9999)) )
)
; there are two 12-digit cubes, each with 5 permutations 
;(["1112112111" 5] ["1112121111" 5])

; found cubes, which have given canon
(for [n (range 4641 9999) :let [n3 (* n n n), cn (canon n3)] :when (= "1112112111" cn)] [n n3])
'([5196 140283769536] [8124 536178930624] [8496 613258407936] [9702 913237656408] [9783 936302451687])

(for [n (range 4641 9999) :let [n3 (* n n n), cn (canon n3)] :when (= "1112121111" cn)] [n n3])
'([5027 127035954683] [7061 352045367981] [7202 373559126408] [8288 569310543872] [8384 589323567104])

; minimal is 127035954683 = 5027^3
 