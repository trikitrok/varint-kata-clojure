(ns varint.core-test
  (:require
    [varint.core :refer :all]
    [midje.sweet :refer :all]))

(facts
  "about varint encoding"
  (encode 1) => "00000001")
