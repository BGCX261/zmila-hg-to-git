(ns src.problem043)

(defn different-chars 
	([n] (different-chars n #{}))
	([[f & r] chars] 
		(cond 
			(nil? f) true
		 	(chars f) false
		 	:else (recur r (conj chars f))))
)

(defn pad3 [n] (if (< n 100) (if (< n 10) (str "00" n) (str "0" n)) (str n)))

(defn candidates [prime]
	(for [x (range 1 (inc (quot 1000 prime))) 
				:let [x3 (pad3 (* x prime))] 
				:when (different-chars x3)] 
		x3)
)

(defn intersect-candidates [cand1 cand2] 
	(for [
		[a1 b1 c1] cand1, 
		[b2 c2 & r2 :as all2] cand2 
			:when (and (= b1 b2) (= c1 c2))
			:let [s-two (str a1 all2)]
			:when (different-chars s-two)] 
			 s-two)
)
(intersect-candidates (candidates 13) (candidates 17))

(apply +
	(mapcat 
		(fn [p9] (for [x "123456789" :let [p10 (cons x p9)] :when (different-chars p10)] 
							(bigint (apply str p10))))
		(reduce (fn [a b] (intersect-candidates b a)) (map candidates [17 13 11 7 5 3 2]))
		;(4106357289 4130952867 4160357289 1406357289 1430952867 1460357289)
	)
)

;16695334890

