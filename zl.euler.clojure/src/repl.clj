(ns repl)
(use 'clojure.contrib.java-utils
     'clojure.contrib.pprint
     'clojure.contrib.seq-utils
     'clojure.contrib.str-utils
     'clojure.contrib.repl-utils
     'clojure.contrib.duck-streams
     'clojure.contrib.lazy-xml)
     
(set! *print-length* 102)

(-> 3 (* 1.8) (+ 32))
