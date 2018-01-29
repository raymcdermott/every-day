(ns every-day.quotes)

(def qs [[{:pronoun "I"}
          {:verb-present "like"}
          {:adjective "green"}
          {:noun-plural "eggs"}
          {:conjunction "and"}
          {:noun "ham"}]
         [{:pronoun "You"}
          {:verb-present "are"}
          {:pronoun "you"}
          {:punctuation :full-stop}
          {:verb-present "Isn't"}
          {:pronoun "that"}
          {:adjective "pleasant"}]
         [{:adverb "Sometimes"}
          {:definite-article "the"}
          {:noun-plural "questions"}
          {:verb-present "are"}
          {:adjective "complicated"}
          {:conjunction "and"}
          {:definite-article "the"}
          {:noun-plural "answers"}
          {:verb-present "are"}
          {:adjective "simple"}]
         [{:verb-present "Don't"}
          {:verb-present "cry"}
          {:conjunction "because"}
          {:definite-article "it's"}
          {:noun-plural "over"}
          {:punctuation :full-stop}
          {:verb-present "Smile"}
          {:conjunction "because"}
          {:noun "it"}
          {:verb-past "happened"}]])

(def punctuation {:full-stop "."
                  :comma     ","})

