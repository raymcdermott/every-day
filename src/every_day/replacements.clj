(ns every-day.replacements)

(def l33t {:bad       {:noun         "n00b"
                       :noun-plural  "n00bs"
                       :adjective    "n00b13"
                       :verb-present "n00b"
                       :verb-past    "n00bd"}
           :very-bad  {:noun         "h4x0r"
                       :noun-plural  "h4x0rs"
                       :adjective    "l33t"
                       :verb-present "h4x"
                       :verb-past    "h4xd"}
           :super-bad {:noun         "pwnz0r"
                       :noun-plural  "pwnz0rs"
                       :adjective    "pwn13"
                       :verb-present "pwn"
                       :verb-past    "pwnd"}
           :the-worst {:noun         "suxx0r"
                       :noun-plural  "suxx0rs"
                       :adjective    "suxx13"
                       :verb-present "suxx"
                       :verb-past    "suxxd"}})

(def potus {:bad       {:noun         "4 years"
                        :noun-plural  "8 years"
                        :adjective    "least racist"
                        :verb-present "preside"
                        :verb-past    "presided"}
            :very-bad  {:noun         "fine person"
                        :noun-plural  "fine people"
                        :adjective    "huge victory"
                        :verb-present "elect"
                        :verb-past    "elected"}
            :super-bad {:noun         "fraudster"
                        :noun-plural  "fraudsters"
                        :adjective    "fake"
                        :verb-present "defraud"
                        :verb-past    "defrauded"}
            :the-worst {:noun         "covfefe"
                        :noun-plural  "covfefe"
                        :adjective    "stable genius"
                        :verb-present "con"
                        :verb-past    "conned"}})

(def veg {:bad       {:noun         "carrot"
                      :noun-plural  "carrots"
                      :adjective    "carotene"
                      :verb-present "firmly carrots"
                      :verb-past    "carroted"}
          :very-bad  {:noun         "radish"
                      :noun-plural  "radishes"
                      :adjective    "radishy"
                      :verb-present "ravishingly radishes"
                      :verb-past    "radished"}
          :super-bad {:noun         "leek"
                      :noun-plural  "leeks"
                      :adjective    "leekally"
                      :verb-present "loosely leeks"
                      :verb-past    "leeked"}
          :the-worst {:noun         "sprout"
                      :noun-plural  "sprouts"
                      :adjective    "sprouty"
                      :verb-present "severely sprouts"
                      :verb-past    "sprouted"}})

(def badness-degrees
  [:bad :very-bad :super-bad :the-worst])

(def replacement-parts
  [:noun :noun-plural :adjective :verb-present :verb-past])

(def punctuation {:full-stop "."
                  :comma     ","})

