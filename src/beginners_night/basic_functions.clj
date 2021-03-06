(ns beginners-night.basic-functions
  (:require [clojure.repl :refer :all]))

(comment
  1. A walk through of the basic functions
    Should cover at least these:
      * basic function definitions (more advanced stuff like functions with multiple invoke methods, pre and post conditions will be covered in a different presentation)
      * defn, fn, #(...), let, do, println, doc, find-doc, source, loop, recur, =, str, +, -, *, /, ->
      * ns and it's various options
        * :require (including :refer and :as)
        * :import
        * mention :use and that :require/:refer is preferable
      * mention the docs at clojue.org and clojuredocs.org
      * show some of these functions in action in a repl)

;; download nightcode
;; nightcode.info

;; this file is here:
;; https://github.com/bmaddy/beginners-night

;; determine your ip start up labrepl for people to use
;; alternatively, they can install lieningen and clone
;; https://github.com/relevance/labrepl.git
;; run bin/repl















;; basic function invocation
(inc 4)
(dec 10)
(+ 1 2)
(+ 1 2 3)
(- 3 1)
(* 2 3)
(/ 6 3)
(/ 1 3)
(* 2 (/ 1 3))

;; keywords
:foo

;; strings
"foo"
(str "foo" "bar" 3)

;; printing stuff
(print "stuff")
(println "stuff")

(if true :success :fail)

;; do for multiple things at once
(if true
  (do
    (println "this will succeed")
    :success)
  :fail)

;; tests
(= 3 3)
(= (inc 1) 2)
(not= (inc 1) 2)
(= (inc 1) 2 (dec 3)) ;; variadic

;; lexical scope (think of it as initializing variables that can't be
;; changed)
(let [a 1
      b 2]
  (+ a b))

;; creating vars
(def foo :stuff)
foo

;; defining functions
(defn factorial [n]
  (if (= n 1)
    n
    (* n (factorial (dec n)))))
(factorial 1)
(factorial 5)

;; using a function as a recursion point
(defn my-sum [a b]
  (if (= b 0)
    a
    (recur (inc a) (dec b))))
(my-sum 4 5)

;; anonymous functions
(fn [x] (inc (* 3 x)))
((fn [x] (inc (* 3 x))) 3)
(def more-than-tripled (fn [x] (inc (* 3 x))))
(more-than-tripled 3)
(#(inc (* 3 %)) 3)

;; Collatz Conjecture
(defn collatz [n]
  (if (even? n)
    (/ n 2)
    (inc (* 3 n))))
(collatz 2)
(collatz 3)
(doc iterate)
(take 15 (iterate collatz 10))

;; looping
(loop [n 0]
  (println n)
  (if (< n 10)
    (recur (inc n))
    (println "done")))

(defn factorial-iter [n]
  (loop [acc 1
         i 1]
    (if (<= i n)
      (recur (* acc i)
             (inc i))
      acc)))
(factorial-iter 1)
(factorial-iter 5)

;; getting help
(use 'clojure.repl)
(doc inc)
(source identity)
(apropos "take")
(find-doc "take")

;; load the file
(require 'clojure.reflect)
(clojure.reflect/reflect inc)

(require '[clojure.reflect :as r])
(r/reflect inc)

(require '[clojure.pprint :refer [pprint]])
(pprint (r/reflect inc))


;; import java stuff
(java.util.Date.)
(import 'java.util.Date)
(Date.)
(import '[java.util Date GregorianCalendar])
(Date.)
(GregorianCalendar.)


;; Don't do this, always put ns at the beginning of the file, this is
;; just for explaining
(ns beginners-night.basic-functions
  (:require [clojure.reflect :as r]
            [clojure.pprint :as pprint :refer [pprint]]))

(r/reflect "foo")
(r/reflect identity)
(pprint (r/reflect identity))

;; mention docs at clojure.org and clojuredocs.org
