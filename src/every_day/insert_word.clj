(ns every-day.insert-word
  (:require [every-day.replacements :as replacers]
            [every-day.quotes :as quotes]
            [clojure.set :as set]))

(defn words
  "Return the words from a given quotation"
  [quotation]
  (map (fn [word]
         (if (keyword? word)
           (get punctuation word)
           word)) (flatten (map vals quotation))))

(defn syntax
  "Return the syntax of a given quotation"
  [quotation]
  (flatten (map keys quotation)))

(defn n-degrees
  "Find n distinct degrees of badness from the given list"
  [degrees n]
  (loop [kws-rand (conj #{} (rand-nth degrees))]
    (if (= n (count kws-rand))
      (vec kws-rand)
      (recur (conj kws-rand (rand-nth degrees))))))

(defn n-parts
  "Find n distinct parts that are in the syntax and the replacement parts"
  [syntax replacement-parts n]
  (let [keywords (vec (set/intersection syntax replacement-parts))]
    (loop [kws-rand (conj #{} (rand-nth keywords))]
      (if (= n (count kws-rand))
        (vec kws-rand)
        (recur (conj kws-rand (rand-nth keywords)))))))

(defn syntax-match-rand-nth
  "Give rand-nth on index/es where a given syntax part is located in a given syntax"
  [syntax part]
  (rand-nth (:positions (reduce
                          (fn [w1 w2]
                            (let [{:keys [index match positions]} w1]
                              (if (= match w2)
                                (assoc w1 :index (inc index) :positions (conj positions index))
                                (assoc w1 :index (inc index)))))
                          {:index 0 :match part :positions []} syntax))))

(defn quote-replace-in
  "Inject the replacements into the quote at the given spots"
  [q replacements]
  (:output (reduce
             (fn [w1 w2]
               (let [{:keys [index replacements output]} w1
                     replace-1 (first replacements)
                     replace-2 (last replacements)]
                 (cond
                   (= (:pos replace-1) index) (assoc w1 :index (inc index) :output (conj output (:replacement replace-1)))
                   (= (:pos replace-2) index) (assoc w1 :index (inc index) :output (conj output (:replacement replace-2)))
                   :else (assoc w1 :index (inc index) :output (conj output w2)))))
             {:index 0 :replacements replacements :output []} q)))

(defn syntax-replace
  [replacements quotation]
  (let [quote-syntax (syntax quotation)
        [part-1 part-2] (n-parts (set quote-syntax)
                                 (set replacers/replacement-parts) 2)
        [degree-1 degree-2] (n-degrees replacers/badness-degrees 2)
        replace-1    (assoc {} part-1 (get (get replacements degree-1) part-1))
        replace-2    (assoc {} part-2 (get (get replacements degree-2) part-2))
        pos-1        (syntax-match-rand-nth quote-syntax part-1)
        pos-2        (syntax-match-rand-nth quote-syntax part-2)]

    (quote-replace-in quotation [{:pos pos-1 :replacement replace-1}
                                 {:pos pos-2 :replacement replace-2}])))

(defn replace-all
  [replacements quotes]
  (map (partial syntax-replace replacements) quotes))

(defn random-replacement
  [replacements quotes]
  (words (rand-nth (replace-all replacements quotes))))

(defn random-repeat-replacements
  [replacements quotes repetitions]
  (repeatedly repetitions #(random-replacement replacements quotes)))

(defn display-replacements
  [replacements quotes repetitions]
  (let [qs (random-repeat-replacements replacements quotes repetitions)]
    (map (comp clojure.string/join (partial clojure.string/join " ")) qs)))

(display-replacements replacers/potus quotes/qs 1)
