(ns every-day.boundaries
  (:require [clojure.test :refer :all]
            [shrubbery.core :refer :all]))

;; providing solid boundaries
;; using protocols and stubs - via duct from @weavejester and shrubbery from @bguthrie

(defprotocol BlockChainClient
  (create-firmware-release [client firmware-release])
  (endorsed-firmware-releases [client])
  (pending-firmware-releases [client])
  (query-firmware-release [client predicate]))

(def hl-stub
  (stub BlockChainClient
    {:create-firmware-release    [{:name "fw-1234" :hash "some-sha-value"}]
     :endorsed-firmware-releases (throws UnsupportedOperationException)
     :pending-firmware-releases  (throws UnsupportedOperationException)
     :query-firmware-release     (throws UnsupportedOperationException)}))

(defn create-release
  [client release]
  (create-firmware-release client release))

(deftest test-create-release
  (is (nil? (create-release (stub BlockChainClient) {:name "foo" :hash "sha"})))
  (is (= "fw-1234" (-> hl-stub (create-release {:name "foo" :hash "sha"})
                       (first)
                       (:name)))))

;; AND SPEC it please people