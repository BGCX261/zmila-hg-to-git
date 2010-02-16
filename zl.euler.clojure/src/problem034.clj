(ns problem034)

(set! *print-length* 102)


(quote "145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.
Find the sum of all numbers which are equal to the sum of the factorial of their digits.
Note: as 1! = 1 and 2! = 2 are not sums they are not included.")

(def fs (sorted-map 0 1, 1 1, 2 2, 3 6, 4 24, 5 120, 6 720, 7 5040, 8 40320, 9 362880) )

(defn normalize [n]
	(apply str (sort (str n))))

(quote "A factorion is an integer which is equal to the sum of factorials  of its digits. 
There are exactly four such numbers:
1	=	1!	
2	=	2!	
145	=	1!+4!+5!	
40585	=	4!+0!+5!+8!+5!")	

(filter (fn [[f d]] (= f d))
(let [mi 10000, ma (* 10 mi)]
(for [ [n1 f1] fs :while (< f1 ma)
			 [n2 f2] fs :when (<= n1 n2) :while (< f2 ma)
			 [n3 f3] fs :when (<= n2 n3) :while (< f3 ma)
			 [n4 f4] fs :when (<= n3 n4) :while (< f4 ma)
			 [n5 f5] fs :when (<= n4 n5) :while (< f5 ma)
			 ;[n8 f8] fs :when (<= n7 n8) :while (< f8 ma)
			 ;:let [sumF (+ f1 f2 f3 f4 f5 f6 f7 f8), sumD (str n1 n2 n3 n4 n5 n6 n7 n8)]
			 :let [sumF (+ f1 f2 f3 f4 f5), sumD (str n1 n2 n3 n4 n5)]
			 ;:let [sumF (+ f1 f2 f3 f4), sumD (str n1 n2 n3 n4)]
			 ;:let [sumF (+ f1 f2 f3), sumD (str n1 n2 n3)]
			 :when (and (<= mi sumF) (<= sumF ma))
			]
	[(normalize sumF) (normalize sumD)])
)
)
