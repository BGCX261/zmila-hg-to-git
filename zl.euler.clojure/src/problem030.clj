(ns problem030)

(set! *print-length* 102)

(quote "Surprisingly there are only three numbers that can be written as the sum of fourth powers of their digits:
    1634 = 1^(4) + 6^(4) + 3^(4) + 4^(4)
    8208 = 8^(4) + 2^(4) + 0^(4) + 8^(4)
    9474 = 9^(4) + 4^(4) + 7^(4) + 4^(4)
As 1 = 1^(4) is not a sum it is not included.
The sum of these numbers is 1634 + 8208 + 9474 = 19316.
Find the sum of all the numbers that can be written as the sum of fifth powers of their digits")

(def fifth
	(sorted-map 0 0, 1 1, 2 32, 3 243, 4 1024, 5 3125, 6 7776, 7 16807, 8 32768, 9 59049))

(defn normalize [n]
	(apply str (sort (str n))))

(filter (fn [[f d]] (= f d))
(let [mi 100000, ma (* 10 mi)]
(for [ [n1 f1] fifth :while (< f1 ma)
			 [n2 f2] fifth :when (<= n1 n2) :while (< f2 ma)
			 [n3 f3] fifth :when (<= n2 n3) :while (< f3 ma)
			 [n4 f4] fifth :when (<= n3 n4) :while (< f4 ma)
			 [n5 f5] fifth :when (<= n4 n5) :while (< f5 ma)
			 [n6 f6] fifth :when (<= n5 n6) :while (< f6 ma)
			 :let [sumF (+ f1 f2 f3 f4 f5 f6), sumD (str n1 n2 n3 n4 n5 n6)]
			 ;:let [sumF (+ f1 f2 f3 f4 f5), sumD (str n1 n2 n3 n4 n5)]
			 ;:let [sumF (+ f1 f2 f3 f4), sumD (str n1 n2 n3 n4)]
			 ;:let [sumF (+ f1 f2 f3), sumD (str n1 n2 n3)]
			 ;:let [sumF (+ f1 f2), sumD (str n1 n2)]
			 :when (and (<= mi sumF) (<= sumF ma))
			]
	[(normalize sumF) (normalize sumD)])
)
)

; 6 "147999"
; 5	"03489" "22779" "44578" "44578"
; 4 "0145" "1145" 

(def pres [3489 22779 44578 145 1145 147999])
(defn sum5 [n acc]
	(if (zero? n) acc
		(let [r (rem n 10) q (quot n 10)]
			(recur q (+ acc (fifth r))))))
(apply + 
(map #(sum5 % 0) pres)
)
 