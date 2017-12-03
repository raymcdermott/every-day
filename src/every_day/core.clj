(ns every-day.core)

;;; vars and dynamic binding (via the Joy of Clojure)

(defn print-read-eval []
  (println "*read-eval* is currently" *read-eval*))

;; shows thread-local binding
(defn binding-play []
  (print-read-eval)
  (binding [*read-eval* false]
    (print-read-eval))
  (print-read-eval))


;; with-precision looks like a lexically scoped function but is in fact a side-effect
;; it sets the clojure.core/*math-context* var
(with-precision 4
  (/ 1M 3))

;; this fails because map is lazy and the evaluation does not occur in the same thread-local scope
(with-precision 4
  (map (fn [x] (/ x 3)) (range 1M 4M)))


;;; three options to fix

;; first with doall ... works cos no longer lazy, same thread-local context
(with-precision 4
  (doall (map (fn [x] (/ x 3)) (range 1M 4M))))

;; second with bound-fn, the *math-context* scope is recreated, map is lazy again
(with-precision 4
  (map (bound-fn [x] (/ x 3)) (range 1M 4M)))

;; third rearrange the scope so that it is set per item
(map (fn [x] (with-precision 4 (/ x 3))) (range 1M 4M))
