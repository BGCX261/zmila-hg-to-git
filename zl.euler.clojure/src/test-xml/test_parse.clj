(ns src.test-parse)

(def xml-file-name "src/test-xml/test-parse.xml")
(def tpx (clojure.xml/parse xml-file-name))

(println (:attrs tpx))
;{:bbb ccc, :ccc bbb}

(println (:content tpx))
(quote 
[
	ddd1
 {:tag :eee, :attrs {:id 1}, :content [e1]} 
 {:tag :eee, :attrs {:id 2}, :content [e2]} 
 {:tag :eee, :attrs {:id 3}, :content nil} 
 {:tag :fff, :attrs nil, :content nil} 
 {:tag :ggg, :attrs nil, 
 		:content [{:tag :ggg, :attrs nil, 
 				:content [ggg]}]}
]
)
