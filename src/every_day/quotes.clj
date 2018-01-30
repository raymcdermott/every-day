(ns every-day.quotes)

; Be who you are and say what you feel because those who mind don't matter and those who matter don't mind.
; Why fit in when you were born to stand out?
; Unless someone like you cares a whole awful lot, nothing is going to get better. It's not.
; If you never did you should. These things are fun and fun is good
; You can get help from teachers, but you are going to have to learn a lot by yourself, sitting alone in a room.
; Remember me and smile, for it's better to forget than to remember me and cry.
; Today is your day! Your mountain is waiting. So... get on your way.
; From there to here, and here to there, funny things are everywhere.
; Things may happen and often do to people as brainy and footsy as you.

(def qs [[{:pronoun "I"}
          {:verb-present "like"}
          {:adjective "green"}
          {:noun-plural "eggs"}
          {:conjunction "and"}
          {:noun-plural "ham"}]
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
          {:definite-article "it"}
          {:verb-past "happened"}]])


(def syntax-parts
  " Obtain all keys and place them in a set "
  (reduce into #{}
          (map (comp flatten (partial map keys)) qs)))