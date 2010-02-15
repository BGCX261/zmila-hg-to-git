(ns src.problem044)

(set! *print-length* 102)

; solution taken from
; http://clojure-euler.wikispaces.com/Problem+044?responseToken=0b8641e7a21552c4fa083494c32610016

(def pents
  (map #(bit-shift-right (* % (dec (* 3 %))) 1) (iterate inc 1)))

(defn pent? [p]
  (zero? (rem (/ (+ 1 (Math/sqrt (+ 1 (* 24 p)))) 6) 1)))
 
(defn eul44 []
	;; p - iterator over penta seq  
	;; diffs = map{ p+x -> p-x }
  (loop [ [p & ps] pents diffs {}]
		; if current p is in the diffs map, 
    (if (contains? diffs p)
    		; then p = a + b, where a, b and a-b are penta
    		; so a-b is the required minimum 
      (get diffs p)
      	; else
      (recur ps
             (let [
			             	; ( x | is_pent(x) && x < p && is_pent(p-x) )
			             	filtered-pents (filter #(pent? (- p %)) (take-while #(< % p) pents))
			             	; ( [p+x p-x] | is_pent(x) && x < p && is_pent(p-x) )
             				new-diffs (mapcat #(vector (+ p %) (- p %)) filtered-pents)]
               (if (first new-diffs)
                 (apply assoc diffs new-diffs)
                 diffs)))))
)
