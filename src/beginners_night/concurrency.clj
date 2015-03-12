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




























;;locking
(defn my-println [& coll]
  (locking *out*
    (apply println coll)))

(pmap #(println "this is a line of text" %) (range 10))
(pmap #(my-println "this is a line of text" %) (range 10))





;;futures
(def sandwich (future (println "starting to make sandwich...")
                      (Thread/sleep 5000)
                      (println "...finished making sandwich")
                      :sandwich))
(println "got" (deref sandwich))
(println "got" @sandwich)

(if (deref sandwich 1000 false)
  (println "eat sandwich")
  (println "drive away"))

;; uses a thread pool (#-of-processors + 2)
;; not for IO


;; simulate long calculations
(defn calculate [n]
  (let [seconds (rand-int 10)]
    (Thread/sleep (* 1000 seconds)) ;;wait for 0-9 seconds
    (my-println "took" seconds "seconds to get result" n)
    (str "result " n " (" seconds "s)")))

;; synchronously
(let [results (map #(calculate %) (range 10))]
  (clojure.pprint/pprint results))

;; with futures
(let [results (map #(future (calculate %)) (range 10))]
  (clojure.pprint/pprint results)
  (doseq [f results]
    (my-println "received:" @f))
  (clojure.pprint/pprint results))





;; promises
(def a (promise))
(def b (promise))
(future
  (println "result:" (str @a @b)))

(deliver b "Bridge")
(deliver a "Clojure")

;; from different thread
(deliver b "Bridge")
(.start (Thread. #(deliver a "Clojure")))
(future (deliver a "stuff"))




;; atoms (synchronous, uncoordinated)
(def cnt (atom 0))
(swap! cnt inc)
(deref cnt)
@cnt
(reset! cnt 0)



(defn show-retries-with-swap! [thread-num target f]
  (let [seen-values (atom [])]
    (swap! target (fn [val]
                    (if-not (empty? @seen-values)
                      (my-println "thread" thread-num
                                  "saw values:" @seen-values
                                  "retrying with" val))
                    (swap! seen-values conj val)
                    (f val)))))

(reset! cnt 0)
(deref cnt)
(pmap #(dotimes [n 100]
         (show-retries-with-swap! % cnt inc))
      (range 50))

;; uses: counters, application state, memoization, etc.

;; river example

;; "No man can cross the same river twice,
;;  because neither the man nor the river are the same."
;; - Heraclitus

;; The Langoliers by Stephen King

;; hall of mirrors in old video games




;; refs (synchronous, coordinated)
;; mult threads transfer random amounts between bank accounts
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
  (println @acct1 "\t+ " @acct2 "\t+ " @acct3
           "\t= " (+ @acct1 @acct2 @acct3)))

(pmap #(dotimes [n 100] (%))
      [rand-xfer rand-xfer rand-xfer print-balances])

;; setting the value
(deref acct1)
(dosync (ref-set acct1 100))




;;agents (asynchronous, uncoordinated)

;; problems with my-println above
;; * synchronous
;; * threads using it have to wait


;; send
;; shares thread pool with futures (#-of-processors + 2)
;; good for cpu intensive stuff
(def a (agent 0))
(send a (fn [x]
          (Thread/sleep 5000)
          (inc x)))
(println @a)
(do (await a)
    (println @a))


;; send-off
;; unbounded thread pool with 1 minute keep alive
;; bounded only by memory (millions)
;; good for IO
(def printer (agent 0))
(send-off printer)




;; vars
