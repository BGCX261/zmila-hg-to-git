(ns problem206)

(comment "Problem 206 Concealed Square
	Find the unique positive integer whose square has the form 1_2_3_4_5_6_7_8_9_0,
	where each '_' is a single digit."
)

(quote 1389019160 1929374254627488900)

; 1_2_3_4_5_6_7_8_9_0
(defn check-num 
	([num] (and (zero? (rem num 10)) (check-num 9 (quot num 100))))
	([dig num] 
		;(println dig num)
		(if (= 1 dig num) true
			(and (= dig (rem num 10)) (recur (dec dig) (quot num 100)))))
)  

(defn sqrt [n] (bigint (Math/sqrt n)))
(defn square [n] (* n n))

(def number206 1020304050607080900)
(def sqrt-min (sqrt number206))	; 1010101010
(def sqr-min (square sqrt-min)) ; 1020304050403020100

(def number296 1929394959697989990)
(def sqrt-max 1389026630)	; 1389026630 (round to 10)
(def sqr-max 1929394978849156900) ; 1929394978849156900

(defn problem206 
	"iterate all from sqrt-min"
	([] (problem206 sqrt-min sqr-min))
	([curr-sqrt curr-sqr]
		(if (zero? (rem curr-sqrt 1000000)) (println curr-sqrt curr-sqr))
		(if (check-num curr-sqr)
			(println "found!" curr-sqrt curr-sqr)
			(recur (+ curr-sqrt 10) (square curr-sqrt))))
)

(defn problem206- 
	"iterate all from sqrt-max"
	([] (problem206- sqrt-max sqr-max))
	([curr-sqrt curr-sqr]
		(if (zero? (rem curr-sqrt 1000000)) (println curr-sqrt curr-sqr))
		(if (check-num curr-sqr) 
			(println "found!" curr-sqrt curr-sqr)
			(recur (- curr-sqrt 10) (square curr-sqrt))))
)
