(ns every-day.insert-word)

(def badness [:bad :very-bad :super-bad :the-worst])

(def l33t {:bad       {:noun         "n00b"
                       :noun-plural  "n00bs"
                       :adjective    "n00biness"
                       :verb-present "n00bing"
                       :verb-past    "n00bd"}
           :very-bad  {:noun         "h4x0r"
                       :noun-plural  "h4x0rs"
                       :adjective    "l33tness"
                       :verb-present "h4x1ng"
                       :verb-past    "h4xd"}
           :super-bad {:noun         "pwnz0r"
                       :noun-plural  "pwnz0rs"
                       :adjective    "pwnage"
                       :verb-present "pwning"
                       :verb-past    "pwnd"}
           :the-worst {:noun         "suxxor"
                       :noun-plural  "suxxors"
                       :adjective    "suxxage"
                       :verb-present "suxxing"
                       :verb-past    "suxxed"}})

(def veg {:bad       {:noun         "carrot"
                      :noun-plural  "carrots"
                      :adjective    "carotene"
                      :verb-present "carroting"
                      :verb-past    "carroted"}
          :very-bad  {:noun         "radish"
                      :noun-plural  "radishes"
                      :adjective    "radishy"
                      :verb-present "radishing"
                      :verb-past    "radished"}
          :super-bad {:noun         "leek"
                      :noun-plural  "leeks"
                      :adjective    "leekally"
                      :verb-present "leeking"
                      :verb-past    "leeked"}
          :the-worst {:noun         "sprout"
                      :noun-plural  "sprouts"
                      :adjective    "sprouty"
                      :verb-present "sprouting"
                      :verb-past    "sprouted"}})

(def replacement-syntax-parts (keys (:bad l33t)))

(def quotations [[{:pronoun "I"}
                  {:verb-present "like"}
                  {:adjective "green"}
                  {:noun-plural "eggs"}
                  {:conjunction "and"}
                  {:noun "ham"}
                  {:punctuation :full-stop}]
                 [{:pronoun "You"}
                  {:verb-present "are"}
                  {:pronoun "you"}
                  {:punctuation :full-stop}
                  {:verb-present "Isn't"}
                  {:pronoun "that"}
                  {:adjective "pleasant"}
                  {:punctuation :full-stop}]
                 [{:adverb "Sometimes"}
                  {:definite-article "the"}
                  {:noun-plural "questions"}
                  {:verb-present "are"}
                  {:adjective "complicated"}
                  {:conjunction "and"}
                  {:definite-article "the"}
                  {:noun-plural "answers"}
                  {:verb-present "are"}
                  {:adjective "simple"}
                  {:punctuation :full-stop}]
                 [{:verb-present "Don't"}
                  {:verb-present "cry"}
                  {:conjunction "because"}
                  {:definite-article "it's"}
                  {:noun-plural "over"}
                  {:punctuation :full-stop}
                  {:verb-present "Smile"}
                  {:conjunction "because"}
                  {:noun "it"}
                  {:verb-past "happened"}
                  {:punctuation :full-stop}]])

(def enhanced-quotes [[{:pronoun "I"}
                       {:verb-present "like"}
                       {:adjective "green"}
                       {:noun-plural "n00bs"}
                       {:noun-plural "eggs"}
                       {:conjunction "and"}
                       {:noun "ham"}
                       {:punctuation :full-stop}]
                      [{:pronoun "You"}
                       {:noun-plural "pwnz0rs"}
                       {:verb-present "are"}
                       {:pronoun "you"}
                       {:punctuation :full-stop}
                       {:verb-present "Isn't"}
                       {:pronoun "that"}
                       {:adjective "pleasant"}
                       {:noun "pwnage"}
                       {:punctuation :full-stop}]
                      [{:verb-present "Don't"}
                       {:verb-present "cry"}
                       {:conjunction "because"}
                       {:definite-article "it's"}
                       {:adjective "over"}
                       {:punctuation :full-stop}
                       {:verb-present "Smile"}
                       {:conjunction "because"}
                       {:noun "it"}
                       {:verb-past "happened"}
                       {:punctuation :full-stop}]])

(def punctuation {:full-stop "."
                  :comma     ","})

(def ^:private insertion-rules
  "Define a set of rules for inserting new words to quotations"
  [{:match [:pronoun :noun-plural] :insert :noun-plural}
   {:match [:pronoun :verb-present] :insert :noun-plural}
   {:match [:adjective :noun-plural] :insert :noun-plural}
   {:match [:adjective :punctuation] :insert :noun}
   {:match [:definite-article :adjective] :insert :verb-present}])

(defn syntax-match?
  "Check whether a given syntax includes a match"
  [syntax match]
  (= match (reduce
             (fn [w1 w2]
               (let [wm (if (coll? w1) (conj w1 w2) [w1 w2])]
                 (if (= wm match)
                   (reduced match)
                   [w2]))) syntax)))

(defn syntax-match-nth?
  "Find the index where a given syntax is included in a match"
  [syntax syntax-match]
  (let [pos (reduce
              (fn [w1 w2]
                (let [{:keys [index match]} w1]
                  (if (= syntax-match (conj match w2))
                    (reduced index)
                    (assoc {} :index (inc index) :match [w2]))))
              {:index 0 :match []} syntax)]
    (if (number? pos) pos nil)))


(defn matching-rules
  "Return the rules matched on the given syntax or an empty list"
  [rules syntax]
  (filter (fn [rule]
            (syntax-match? syntax (:match rule))) rules))

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

(defn bad-matches
  "Random l33t and veg elements based on a given degree of badness"
  [degree-badness]
  [(get l33t degree-badness)
   (get veg degree-badness)])

(defn syntax-replace
  [rules quote]
  (let [matches (take 2 (matching-rules rules (syntax quote)))
        [l33t veg] (bad-matches (rand-nth badness))]

    )

  )
