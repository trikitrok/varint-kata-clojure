(ns varint.core)

(defn pad-to-seven [bin-num]
  (concat (repeat (- 7 (count bin-num)) "0")
          bin-num))

(defn add-msn [bytes]
  (flatten (concat (map #(cons "1" %) (butlast bytes))
                   (cons "0" (last bytes)) )))

(defn encode [int-num]
  (->> int-num
       Long/toBinaryString
       reverse
       (partition-all 7 7)
       (map reverse)
       (map pad-to-seven)
       (map #(apply str %))
       add-msn
       (apply str)))
