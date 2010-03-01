(ns src.problem059)

(def cipher1 [
	79,59,12,2,79,35,8,,,73
])

(def az
	(range (int \a) (inc (int \z)))
)

(filter #(>= (.indexOf % "and ") 0)
	(for [a az, b az, c az] 
 		(str
 			[a b c] 
 			(apply str (map #(char (bit-xor %1 %2)) (cycle [a b c]) cipher1))
 		)
 	)
)

;("[103 111 100](The Gospel of John, chapter 1) 1 In the beginning the Word already existed. He was with God, and he was God. 2 He was in the beginning with God. 3 He created everything there is. Nothing exists that he didn't make. 4 Life itself was in him, and" 
;)

(apply + (map bit-xor (cycle [103 111 100]) cipher1))
; 107359
