(ns varint.core)

(defn- pad-to-seven [bin-num]
  (concat (repeat (- 7 (count bin-num)) "0")
          bin-num))

(defn- add-most-significat-bits [bytes]
  (flatten (concat (map #(cons "1" %) (butlast bytes))
                   (cons "0" (last bytes)))))

(defn- to-bytes [bin-str]
  (->> bin-str
       reverse
       (partition-all 7 7)
       (map reverse)
       (map pad-to-seven)
       (map #(apply str %))))

(defn encode [int-num]
  (->> int-num
       Long/toBinaryString
       to-bytes
       add-most-significat-bits
       (apply str)))
