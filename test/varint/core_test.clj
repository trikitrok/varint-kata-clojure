(ns varint.core-test
  (:require
    [varint.core :refer :all]
    [midje.sweet :refer :all]))

(facts
  "about varint"
  (facts
    "encoding numbers under 128"
    (encode 1) => "00000001"
    (encode 8) => "00001000"
    (encode 127) => "01111111"))
