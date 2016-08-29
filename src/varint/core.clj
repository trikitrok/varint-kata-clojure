(ns varint.core)

(defn pad-to-seven [bin-num]
  (concat (repeat (- 7 (count bin-num)) "0")
          bin-num))

(defn encode [int-num]
  (let [bin-num (Long/toBinaryString int-num)]
    (apply str (cons "0" (pad-to-seven bin-num)))))
