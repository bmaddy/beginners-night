(ns beginners-night.concurrency
  (:require [clojure.repl :refer :all]))
;; C-c M-n to switch repl to this namespace

(comment
  6. Concurrency
    * vars, refs, agents, atoms, futures, and how to use them
    * Epochal time model (if there's time))

;; download nightcode
;; nightcode.info

;; this file is here:
;; https://github.com/bmaddy/beginners-night




























;;futures
(def sandwich (future (println "starting to make sandwich...")
                      (Thread/sleep 5000)
                      (println "...finished making sandwich")
                      :sandwich))
(println "got" @sandwich)

(if (deref sandwich 1000 false)
  (println "eat sandwich")
  (println "drive away"))



;; simulate web requests
(defn calculate [n]
  (let [seconds (rand-int 10)]
    (Thread/sleep (* 1000 seconds)) ;;wait for 1-10 seconds
    (println "took" seconds "seconds to get result" n)
    (str "result " n " (" seconds "s)")))

;;without futures
(let [results (map calculate (range 10))]
  (clojure.pprint/pprint results))

;; with futures
(let [results (map #(future (calculate %)) (range 10))]
  (clojure.pprint/pprint results)
  (doseq [f results]
    (println "received:" @f))
  (clojure.pprint/pprint results))





;;promises
(def a (promise))
(def b (promise))
(future
  (println "result:" (str @a @b)))
(deliver a "Clojure")
(deliver b "Bridge")



;;vars

;;atoms (synchronous, uncoordinated)
(def cnt (atom 0))
(defn next-val []
  (swap! cnt inc))
(deref cnt)
(next-val)
@cnt

;;uses: counters, application state, memoization, etc.


;;refs (synchronous, coordinated)
;;idea: mult threads transfer random amounts between bank accounts
;; different thread prints amounts and the sum of all accounts
;; show with atom/swap! and ref/alter
(def acct1 (atom 100))
(def acct2 (atom 100))
(def acct3 (atom 100))

(defn rand-xfer []
  (let [[a b] (take 2 (shuffle [acct1 acct2 acct3]))
        amount (rand-int 10)]
    (print ".")
    (dosync
     (swap! a (fn [x] (+ x amount)))
     (Thread/sleep 1)
     (swap! b (fn [x] (- x amount))))))

(defn print-balances []
  (Thread/sleep 1)
  (dosync
   (println @acct1 "\t+ " @acct2 "\t+ " @acct3 "\t= " (+ @acct1 @acct2 @acct3))))

(defn many-times [f]
  (dotimes [n 100]
    (f)))

(pmap many-times [rand-xfer rand-xfer rand-xfer print-balances])


;;agents (asynchronous)


;;locking
(defn my-print [a b]
  (locking *out*
    (println a b)))

(pmap #(println "this is a line of text" %) (range 10))
(pmap #(my-print "this is a line of text" %) (range 10))



;;threads


;;executors
