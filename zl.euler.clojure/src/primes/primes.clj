(ns src.primes.primes
	;; TODO: Only expt from math is needed, but how do I use :only with multiple libs?
  (:use [clojure.contrib math test-is])
)

(quote
	"Everybody loves the Sieve of Eratosthenes" 
	"http://clj-me.cgrand.net/2009/07/30/everybody-loves-the-sieve-of-eratosthenes/")

(defn primes [max]
	(let [enqueue (fn [sieve n factor] 
						(let [m (+ n factor factor)]
							;(println "(enqueue" n factor sieve \))
							(if (sieve m)
								(recur sieve m factor)
								(assoc sieve m factor))))
				next-sieve (fn [sieve candidate]
						;(println "(next-sieve" candidate sieve \)) 
						(if-let [factor (sieve candidate)]
							(-> sieve
								(dissoc ,,, candidate)
								(enqueue ,,, candidate factor))
							(enqueue sieve candidate candidate)))]
		(cons 2 (vals (reduce next-sieve {} (range 3 max 2)))))
)

(defn primes-2 [max]
	"primes without 2"
	(let [enqueue (fn [sieve n factor] 
						(let [m (+ n factor factor)]
							;(println "(enqueue" n factor sieve \))
							(if (sieve m)
								(recur sieve m factor)
								(assoc sieve m factor))))
				next-sieve (fn [sieve candidate]
						;(println "(next-sieve" candidate sieve \)) 
						(if-let [factor (sieve candidate)]
							(-> sieve
								(dissoc ,,, candidate)
								(enqueue ,,, candidate factor))
							(enqueue sieve candidate candidate)))]
		(vals (reduce next-sieve {} (range 3 max 2))))
)

(defn lazy-primes []
  (letfn [(enqueue [sieve n step]
            (let [m (+ n step)]
              (if (sieve m)
                (recur sieve m step)
                (assoc sieve m step))))
          (next-sieve [sieve candidate]
            (if-let [step (sieve candidate)]
              (-> sieve
                (dissoc ,,, candidate)
                (enqueue ,,, candidate step))
              (enqueue sieve candidate (+ candidate candidate))))
          (next-primes [sieve candidate]
            (if (sieve candidate)
              (recur (next-sieve sieve candidate) (+ candidate 2))
              (cons candidate
                (lazy-seq (next-primes (next-sieve sieve candidate) (+ candidate 2))))))]
    (cons 2 (lazy-seq (next-primes {} 3)))))

;(time (count (sieve 10000000)))	; 664579

(defn sieve [n]
	"Returns a list of all primes from 2 to n
		http://paste.lisp.org/display/69952"
  (let [n (int n), root (int (Math/round (Math/floor (Math/sqrt n))))]
    (loop [i (int 3),		a (int-array n),	result (list 2)]
      (if (>= i n)
        (reverse result)
        (recur (+ i (int 2)), 
        		   (if (< i root)
                 (loop [arr a, step (+ i i), j (* i i)]
                   (if (< j n)
                     (recur (do (aset arr j (int 1)) arr)
                            step
                            (+ j step))
                     arr))
                 a)
               (if (zero? (aget a i))
                 (conj result i)
                 result)))))
)
; http://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test


(def
 #^{:doc "The chances that prime? returns true for a composite 
		if *pseudo* is true is (expt 4 (* -1 *pseudo-accuracy*))."}
 *pseudo-accuracy* 10)

(defn factorize-out
  "Factorizes out all x factors from n.
		Examples:
			(factorize-out 10 2) ==> 5, because 2^1 * 5 = 10
			(factorize-out 90 3) ==> 10, because 3^2 * 10 = 90"
  [n x]
  (loop [acc n exp 0]
    (if (= 0 (mod acc x))
      (recur (/ acc x) (inc exp))
      (hash-map :exponent exp :rest acc))))

(defn expt-mod
  "Equivalent to (mod (expt n e) m), but faster.
		http://en.wikipedia.org/wiki/Modular_exponentiation#An_efficient_method:_the_right-to-left_binary_algorithm";
  [n e m]
  (loop [r 1, b n, e e]
    (if (= e 0)
      r
      (recur (if (odd? e)
               (mod (* r b) m)
               r)
             (mod (expt b 2) m)
             (bit-shift-right e 1)))))

(defn prime?
  "Checks if n is a prime using the Miller-Rabin pseudo-primality test.  
  	Also see *pseudo* and *pseudo-accuracy*."
  [n]
  (cond
    (< n 2)   false
    (= n 2)   true
    (even? n) false
    :else 
    	(let [m (factorize-out (dec n) 2)
            d (:rest m)
            s (:exponent m)]
        (loop [k 1]
          (if (> k *pseudo-accuracy*)
            true
            (let [a (+ 2 (rand-int (- n 4)))
                  x (expt-mod a d n)]
              (if (or (= x 1) (= x (dec n)))
                (recur (inc k))
                (if (loop [r 1
                           x (expt-mod x 2 n)]
                      (cond
                       (or (= x 1) (>  r (dec s)))  false
                       (= x (dec n))                true
                       :else (recur (inc r) (mod (* x x) n))))
                  (recur (inc k))
                  false))))))))

(defn next-prime [n]
  "Returns the next prime greater than n."
  (cond
    (< n 2)  2
    :else (loop [n (inc n)]
            (if (prime? n)
              n
              (recur (inc n))))))

;; test stuff

(def
 #^{:private true}
 first-1000-primes
 '(   2      3      5      7     11     13     17     19     23     29 
 ;; [Snipped, but the test works with all 1000 primes...]
   7841   7853   7867   7873   7877   7879   7883   7901   7907   7919))

(deftest test-prime-fns
  (loop [a (take 1000 (iterate next-prime 2)) 
         b (take 1000 first-1000-primes)]
    (when (and (empty? a) (empty? b))
      (is (= (first a) (first b)))
      (recur  (rest a) (rest b)))))

