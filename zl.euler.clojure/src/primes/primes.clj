(ns src.primes.primes)

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
