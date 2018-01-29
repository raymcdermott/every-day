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

(defn get-replacements
  [replacements quotation]
  (let [[part-1 part-2] (n-parts (set (syntax quotation)) (set replacers/replacement-parts) 2)
        [degree-1 degree-2] (n-degrees replacers/badness-degrees 2)]
    [(assoc {} part-1 (get (get replacements degree-1) part-1))
     (assoc {} part-2 (get (get replacements degree-2) part-2))]))

(defn syntax-replace
  [replacements quotation]
  (let [[replace-1 replace-2] (get-replacements replacements quotation)]
    ; REMOVE DUPS! - maybe reduce rather than map
    (map (fn [original first-opt second-opt]
           (cond
             (= (keys original) (keys first-opt)) first-opt
             (= (keys original) (keys second-opt)) second-opt
             :else original))
         quotation (repeat replace-1) (repeat replace-2))))

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

(display-replacements replacers/potus quotes/qs 10)
