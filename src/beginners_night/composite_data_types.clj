(ns beginners-night.basic-functions
  (:require [clojure.repl :refer :all]))

;; download nightcode
;; nightcode.info

;; this file is here:
;; https://github.com/bmaddy/beginners-night

;; determine your ip start up labrepl for people to use
;; alternatively, they can install lieningen and clone
;; https://github.com/relevance/labrepl.git
;; run bin/repl

(comment
	3. Go over the collection types
		* do a quick review of vectors, lists, maps, and sets
		* discuss the performance differences of each
		* explain the seq abstraction
		* explain what lazy sequences are and how to make them
		* conj, disj, get, assoc, assoc-in, dissoc, first, rest, keys, vals, merge, concat, count, repeat, range, cycle, interpose, nth, take, drop, filter, dorun (doseq and doall if there's time)
		* list comprehensions with for
		* if there's time, explain what persistent data structures are and how these are all just trees under the hood.
		* if there's time, include array-maps, sorted-sets, and the clojure.set namespace)






























;; vectors
(vector 1 2 3)
[1 2 3]
[:a :b :c]
["a" "b" "c"]
[1 \a "foo"]
(vec '(1 2 3))
(vec "a string")

(first [:a :b :c])
(rest [:a :b :c])
;; log32N
(nth [:a :b :c] 1)
([:a :b :c] 1)
(last [:a :b :c])
;; O(1)
(count [:a :b :c])
;; O(1)
(rseq [:a :b :c])
(conj [:a :b :c] :d)
;; very fast
(subvec [1 2 3 4 5] 2 4)
;; O(1) reversal
[:a :b]
(rseq [:a :b])
(class [:a :b])
(class (rseq [:a :b]))

; persistent
(def a [:a :b :c])
a
(def b a)
b
(def a (conj a :d))
a
b
;;  == vs .equals()
;; deep copy vs shallow copy
;; structural sharing - https://en.wikipedia.org/wiki/Persistent_data_structure#Trees
;; concurrent programming


;; lists
(list 1 2 3)
'(1 2 3)
(list* 1 2 3 '(4 5 6))

(first '(:a :b :c))
;; O(1)
(count '(:a :b :c))
;; walks the list
(nth '(:a :b :c) 2)


;; as stacks
;; vectors
(conj [:a :b :c] :added)
(peek [:a :b :c])
(pop [:a :b :c])
(seq [:a :b :c])
;; lists
(conj '(:a :b :c) :added)
(peek '(:a :b :c))
(pop '(:a :b :c))
(seq '(:a :b :c))



;; sets
(def animals #{:tiger :elephant :wolf :panda})
(first animals)
(rest animals)
(conj animals :wombat)
;; no duplicates
(conj animals :wolf)
(disj animals :tiger)
;; also, sorted-set



;; maps
(def person {:fname "Richard" :lname "Feynman"})
(first person)
;; O(1)
(count person)
(get person :fname)
(get person :mname)
(get person :fname "missing")
(get person :mname "missing")
(:fname person)
(assoc person :field :physics)
(dissoc person :lname)
(keys person)
(vals person)
(merge person {:field :physics :nationality :american})

;; vectors are associative
(get [:a :b :c] 1)
(get [:a :b :c] 10) ; out of range
(nth [:a :b :c] 1)
(nth [:a :b :c] 10)
(assoc [:a :b :c] 1 :changed)

(def feynman {:field :physics
              :books [{:title "Surely you're joking Mr. Feynman!"}]})
(get-in feynman [:books 0 :title])
(assoc-in feynman [:books 0 :pages] 200)
;; anything that implements Comperable can be used as keys

;; also, sorted-map

;; lazy-seqs
(take 10 (repeat 1))
(take 10 (range))
(range 2 7)
(take 10 (cycle [:a :b :c]))
(defn fib [a b]
  (cons a (lazy-seq (fib b (+ a b)))))
(take 10 (fib 1 1))

;; seq abstraction for walking collections
(seq [:a :b :c])
(seq '(:a :b :c))
(seq {:a 1 :b 2})
(seq #{:a :b :c})
(seq "string")
(drop 2 [:a :b :c :d])
(concat [:a :b] [:c :d])
(interpose :between [:a :b :c])
(interleave [:a :b :c] (range))
(take 10 (filter even? (range)))
(take 10 (remove even? (range)))
;; conversion
(into [] {:a 1 :b 2})
(into {} [[:a 1] [:b 2]])
(into #{} '(1 2 3))

(map even? [1 2 3 4 5])

(for [x [1 2 3]
      y [10 20 30]]
  [x y])

(for [suit [:spades :hearts :diamonds :clubs]
      rank [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace]]
  [rank suit])

(reduce + [1 2 3])



;; 4clojure problems
;; 4-13
;; 19-21
;; 23
