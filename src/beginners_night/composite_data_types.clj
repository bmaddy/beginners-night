(ns beginners-night.composite-data-types
  (:require [clojure.repl :refer :all]
            [clojure.set :as set]))

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
'(1 2 3)
(vec '(1 2 3))
"string"
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
(def v [:a :b :c :d :e])
(nth v 2)
(nth v 4)
(subvec v 2 4)
;; O(1) reversal
[:a :b]
(rseq [:a :b])
(class [:a :b])
(class (rseq [:a :b]))

; persistent
(def x [:a :b :c])
x
(def y x)
y
(= x y)
(identical? x y)
(def z (conj x :d))
x
y
z
(= x y)
(subvec z 0 3)
(= (subvec z 0 3) y)
(identical? (subvec z 0 3) y)

(def a '(1 2))
(def b (conj a 3))
a
b
(identical? (rest b) a)
(= (rest b) a)

(= 0.5 1/2)
(== 0.5 1/2)


(= [:foo] [:foo])
(identical? [:foo] [:foo])

;;  == vs .equals()
;; deep copy vs shallow copy
;; structural sharing - https://en.wikipedia.org/wiki/Persistent_data_structure#Trees
;; concurrent programming


;; lists
(list 1 2 3)
'(1 2 3)
(list* 1 2 3 '(4 5 6))
(list* :div
 {:class "stuff"}
 "content"
 [[:div] [:div]])

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
(set [:foo :bar])
(set "wow")
(def animals #{:tiger :elephant :wolf :panda})
(first animals)
(rest animals)
(conj animals :wombat)
;; no duplicates
(conj animals :wolf)
(disj animals :tiger)

(def furry-animals #{:cat :dog :mouse :moose})
(def big-animals #{:elephant :moose})
furry-animals
big-animals

(set/intersection furry-animals big-animals)
(set/difference furry-animals big-animals)
(set/difference big-animals animals)

;; also, sorted-set



;; maps
(def person {:fname "Richard" :lname "Feynman"})
person
(dissoc person :fname)
person
;; still immutable
(def person2 (dissoc person :fname))
person2
person


(first person)
;; O(1)
(count person)
(get person :fname)
(get person :mname)
(get person :fname "missing")
(get person :mname "missing")
(:fname person)
(person :fname)
(conj person {:foo :bar :a :b})
(assoc person :field :physics)
(dissoc person :lname)
(keys person)
(vals person)
(merge person {:field :physics :nationality :american})
(merge person {:fname "wrong"} {:fname :fred})

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
(drop 4 (range))
(take 10 (cycle [:a :b :c]))
(conj '(1 2) 3)
(cons 3 '(1 2))
(defn fib [a b]
  (cons a (lazy-seq (fib b (+ a b)))))
(take 34 (fib 1 1))

;; seq abstraction for walking collections
(class (seq [:a :b :c]))
(class (seq '(:a :b :c)))
(class (seq {:a 1 :b 2}))
(seq #{:a :b :c})
(seq "string")
(drop 2 [:a :b :c :d])
(concat [:a :b] [:c :d])
(interpose :between [:a :b :c])
(apply str (interpose ", " ["thing" "other" "stuff"]))
(str 1 2 3)
(apply str [1 2 3])
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
