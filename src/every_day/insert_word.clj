(ns every-day.insert-word
  (:require [every-day.quotes :as quotes]
            [every-day.replacements :as replacements]
            [clojure.string :as string]
            [clojure.set :as set]
            [clojure.spec.alpha :as spec]))

(spec/def ::words (spec/coll-of string?))
(spec/def ::syntax-parts (spec/coll-of keyword?))
(spec/def ::badness-degrees (spec/coll-of keyword?))

(defn words
  "Return the words from a given quotation"
  [quotation punctuation]
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
  ; n must be <= (count degrees)
  (loop [kws-rand (conj #{} (rand-nth degrees))]
    (if (= (count kws-rand) n)
      (vec kws-rand)
      (recur (conj kws-rand (rand-nth degrees))))))

(defn n-parts
  "Find n distinct parts that are in both the syntax and the replacement parts"
  [syntax replacement-parts n]
  (let [keywords (vec (set/intersection syntax replacement-parts))]
    (if (<= (count keywords) n)
      keywords
      (loop [kws-rand (conj #{} (rand-nth keywords))]
        (if (= (count kws-rand) n)
          (vec kws-rand)
          (recur (conj kws-rand (rand-nth keywords))))))))

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
  [quotation replacements]
  (:output (reduce
             (fn [w1 w2]
               (let [{:keys [index replacements output]} w1
                     replace-1 (first replacements)
                     replace-2 (last replacements)]
                 (cond
                   (= (:pos replace-1) index) (assoc w1 :index (inc index)
                                                        :output (conj output (:replacement replace-1)))
                   (= (:pos replace-2) index) (assoc w1 :index (inc index)
                                                        :output (conj output (:replacement replace-2)))
                   :else (assoc w1 :index (inc index)
                                   :output (conj output w2)))))
             {:index 0 :replacements replacements :output []}
             quotation)))

(defn syntax-replace
  [replacement-parts badness-degrees replacements quotation]
  (let [quote-syntax (syntax quotation)
        [part-1 part-2] (n-parts (set quote-syntax)
                                 (set replacement-parts) 2)
        [degree-1 degree-2] (n-degrees badness-degrees 2)
        replace-1    (assoc {} part-1 (get (get replacements degree-1) part-1))
        replace-2    (assoc {} part-2 (get (get replacements degree-2) part-2))
        pos-1        (syntax-match-rand-nth quote-syntax part-1)
        pos-2        (syntax-match-rand-nth quote-syntax part-2)]

    (quote-replace-in quotation [{:pos pos-1 :replacement replace-1}
                                 {:pos pos-2 :replacement replace-2}])))

(defn replace-all
  [replacements quotes]
  (map (partial syntax-replace
                replacements/replacement-parts
                replacements/badness-degrees
                replacements
                quotes)))

(defn random-replacement
  [replacements quotes punctuation]
  (words (rand-nth (replace-all replacements quotes)) punctuation))

(defn random-repeat-replacements
  [replacements quotes repetitions punctuation]
  (repeatedly repetitions #(random-replacement replacements quotes punctuation)))

(defn format-quote
  [punctuation quote]
  (string/join (interpose " " (words quote punctuation))))

(defn format-random-replace
  [replacements quotes punctuation repetitions]
  (let [qs (random-repeat-replacements replacements quotes repetitions punctuation)]
    (map (partial format-quote punctuation) qs)))

(defn enhanced-quote
  [change-pack quote]
  (format-quote replacements/punctuation
    (syntax-replace replacements/replacement-parts replacements/badness-degrees change-pack quote)))

(def speak (partial format-quote replacements/punctuation))
(def veg-speak (partial enhanced-quote replacements/veg))
(def l33t-speak (partial enhanced-quote replacements/l33t))
(def potus-speak (partial enhanced-quote replacements/potus))

; mix em up a little


