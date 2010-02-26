(ns problem065)

(quote "e = [2; 1,2,1, 1,4,1, 1,6,1 , ... , 1,2k,1, ...].
The first ten terms in the sequence of convergents for e are:
2, 3, 8/3, 11/4, 19/7, 87/32, 106/39, 193/71, 1264/465, 1457/536, ...
The sum of digits in the numerator of the 10^(th) convergent is 1+4+5+7=17.
Find the sum of digits in the numerator of the 100^(th) convergent of the continued fraction for e.")


(defn get-e [n]
	"n starts from 1"
	(lazy-seq
		(cons 1 (cons (+ n n) (cons 1 (get-e (inc n))))))
)

(defn e-seq [n]  (reverse (cons 2 (take (dec n) (get-e 1)))))

(defn calc-convergent [n]
	(let [[fi & re] (e-seq n)]
	(loop [[item & items :as all] re,  numr 1, denr fi]
		(if (nil? item) (/ denr numr)
			(recur items denr (+ numr (* denr item))))))
)

(defn sum-digits 
	([n] (sum-digits n 0))
	([n sum] 
		(if (zero? n) sum
			(recur (quot n 10) (+ sum (rem n 10)))))
)

(defn problem065 []
	(sum-digits (.numerator (calc-convergent 100))))

; 6963524437876961749120273824619538346438023188214475670667 / 2561737478789858711161539537921323010415623148113041714756

