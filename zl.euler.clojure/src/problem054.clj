(ns src.problem054)

(set! *print-length* 1002)

(def all-hands
	(into [] 
		(.split 
"8C TS KC 9H 4S 7D 2S 5D 3S AC
5C AD 5D AC 9C 7C 5H 8D TD KS
,,,
AS KD 3D JD 8H 7C 8C 5C QD 6C" 
	"\n")
	)
)

(def hands
	(map #(partition 5 (into [] (.split % " "))) all-hands)
)

(defn value [n]
	({\2 2, \3 3, \4 4, \5 5, \6 6, \7 7, \8 8, 
	\9 9, \T 10, \J 11, \Q 12, \K 13, \A 14} n))

(defn consecutive 
	([n] (consecutive (first n) (rest n)))
	([a [b & r]]
		(if (nil? b) 
			true 
			(and (== (inc a) b) (consecutive b r))))
)

(defn sorted-values [ [[c1 _] [c2 _] [c3 _] [c4 _] [c5 _]] ]
	(sort (map value [c1 c2 c3 c4 c5])))

(defn same-suit? [ [[_ s1] [_ s2] [_ s3] [_ s4] [_ s5]] ]
	(= s1 s2 s3 s4 s5)
)

(defn royal-flush? [ [[c1 s1] [c2 s2] [c3 s3] [c4 s4] [c5 s5]] ]
	"Ten, Jack, Queen, King, Ace, in same suit"
	(and (= s1 s2 s3 s4 s5) (= #{\T \J \Q \K \A} (into #{} [c1 c2 c3 c4 c5])))
)

(defn straight-flush? [ hand sorted-values ]
	"All cards are consecutive values of same suit"
	(and (same-suit? hand) (consecutive sorted-values))
)

(defn four-of-a-kind? [ [s1 s2 s3 s4 s5] ]
	"Four cards of the same value"
	(or (== s1 s2 s3 s4) (== s2 s3 s4 s5))
)

(defn full-house? [ [s1 s2 s3 s4 s5]  ]
	"Three of a kind and a pair"
	(or 
		(and (== s1 s2) (== s3 s4 s5)) 
		(and (== s1 s2 s3) (== s4 s5)))
)

(defn flush? [ hand ]
	"All cards of the same suit"
	(same-suit? hand)
)

(defn straight? [ sorted-values ]
	"All cards are consecutive values"
	(consecutive sorted-values)
)

(defn three-of-a-kind? [ [s1 s2 s3 s4 s5] ]
	"Three cards of the same value"
	(or (== s1 s2 s3) (== s2 s3 s4) (== s3 s4 s5))
)

(defn two-pairs? [ [s1 s2 s3 s4 s5] ] 
	"Two different pairs (called after four-of-a-kind?)"
	(or 
		(and (== s1 s2) (== s3 s4)) 
		(and (== s1 s2) (== s4 s5))
		(and (== s2 s3) (== s4 s5)))
)

(defn one-pair? [ [s1 s2 s3 s4 s5] ] 
	"Two cards of the same value"
	(or (== s1 s2) (== s2 s3)	(== s3 s4) (== s4 s5))
)

(defn high-card [ sv1 sv2 ] 
	"Highest value card"
	(if (> (last sv1) (last sv2)) :player1 :player2)
)

(defn check [sv1 sv2 pred? kind]
	(if	(pred? sv1) 
		[kind (if (pred? sv2) (high-card sv1 sv2) :player1)]
		(if (pred? sv2)
			[kind :player2]))
)


(defn winner [ player1 player2 ]
	(let 
		[sv1 (sorted-values player1), sv2 (sorted-values player2)] 
		(cond 
			(royal-flush? player1) 
				[:royal-flush :player1]
			
			(straight-flush? player1 sv1) 
				[:straight-flush (if (straight-flush? player2 sv2) (high-card sv1 sv2) :player1)]
			(straight-flush? player2 sv2)
				[:straight-flush :player2]

			:else
				(or
					(check sv1 sv2 four-of-a-kind? :four-of-a-kind)
					(check sv1 sv2 full-house? :full-house)
					(check player1 player2 flush? :flush)
					(check sv1 sv2 straight? :straight)
					(check sv1 sv2 three-of-a-kind? :three-of-a-kind)
					(check sv1 sv2 two-pairs? :two-pairs)
					(check sv1 sv2 one-pair? :one-pair)
					[:high-card (high-card sv1 sv2)]
				)))
)

(defn hand-kind [hand] 
	(let [sv (sorted-values hand)
				kind (cond 
					(royal-flush? hand) 9
					(straight-flush? hand sv) 8
					(four-of-a-kind? sv) 7
					(full-house? sv) 6
					(flush? hand) 5
					(straight? sv) 4
					(three-of-a-kind? sv) 3
					(two-pairs? sv) 2
					(one-pair? sv) 1
					:else 0)]
				{:kind kind, :hand hand, :high (last sv)})
)

(def wins2 (map (fn [ [h1 h2] ] [(hand-kind h1) (hand-kind h2)]) hands))
;1000
(def player1-kind-win (filter (fn [[w1 w2]] (> (:kind w1) (:kind w2))) wins2))
;235

(def kind-equ (filter (fn [[w1 w2]] (== (:kind w1) (:kind w2))) wins2))
; 367 (count kind-equ)

;(count (filter (fn [[w1 w2]] (< (:kind w1) (:kind w2))) wins2))
; (+ 398 367 235) 1000

(def kind-0 (filter (fn [[w1 w2]] (zero? (:kind w1))) kind-equ)) ; 214

(def high-card-of-kind-0
	(filter (fn [[w1 w2]] (let [h1 (:high w1), h2 (:high w2)] (> h1 h2))) kind-0)
) ; 72


(defn high-of-pair [kind-hand] 
	(let [ [s1 s2 s3 s4 s5] (sorted-values (:hand kind-hand)) ]
		(cond 
			(== s1 s2) s1
			(== s2 s3) s2
			(== s3 s4) s3
			(== s4 s5) s4))
)

(def kind-1 (filter (fn [[w1 w2]] (== 1 (:kind w1))) kind-equ))	; 153

(def win-high-of-pair
	(filter (fn [[w1 w2]] (> (high-of-pair w1) (high-of-pair w2))) kind-1)
)


(def problem054 
	(+ 
		(count player1-kind-win) 
		(count high-card-of-kind-0)
		(count win-high-of-pair)))	; (+ 235 72 69)
; 376
