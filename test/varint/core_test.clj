(ns varint.core-test
  (:require
    [varint.core :refer :all]
    [midje.sweet :refer :all]
    [clojure.test.check.clojure-test :refer [defspec]]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop]))

(facts
  "about varint"

  (facts
    "encoding numbers under 128"
    (encode 1) => "00000001"
    (encode 8) => "00001000"
    (encode 127) => "01111111")

  (facts
    "encoding numbers greater or equal than 128"
    (encode 300) => "1010110000000010")

  (facts
    "decoding varints"
    (decode "1010110000000010") => 300))

(defspec coding-and-decoding
         1000
         (prop/for-all
           [num (gen/large-integer* {:min 0})]
           (= (-> num encode decode) num)))
