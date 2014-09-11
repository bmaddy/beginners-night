(ns beginners-night.java-interop
  (:require [clojure.repl :refer :all]))

(comment
  4. java interop
    * quick review of (ns (:import ...))
    * doto, ., .., try, catch, finally, throw
    * object instantiation
    * calling java static and instance functions
    * mention how nested classes are named (EnclosingClass$NestedClass)
    * gen-class, proxy)

;; download nightcode
;; nightcode.info

;; this file is here:
;; https://github.com/bmaddy/beginners-night

;; determine your ip start up labrepl for people to use
;; alternatively, they can install lieningen and clone
;; https://github.com/relevance/labrepl.git
;; run bin/repl
