(ns varint.core)

(defn- pad-to-seven [bin-num]
  (concat (repeat (- 7 (count bin-num)) "0")
          bin-num))

(defn- add-most-significat-bits [bytes]
  (flatten (concat (map #(cons "1" %) (butlast bytes))
                   (cons "0" (last bytes)))))

(defn- bin-str->bytes [bin-str]
  (->> bin-str
       reverse
       (partition-all 7 7)
       (map reverse)
       (map pad-to-seven)
       (map #(apply str %))))

(defn encode [int-num]
  (->> int-num
       Long/toBinaryString
       bin-str->bytes
       add-most-significat-bits
       (apply str)))

(def ^:private drop-most-significat-bits (partial map rest))

(defn- varint->bytes [var-int]
  (->> var-int
       (partition-all 8 8)
       drop-most-significat-bits))

(defn- bytes->bin-str [bytes]
  (->> bytes
       reverse
       flatten))

(defn- bin-str->int [bin-str]
  (->> bin-str
       reverse
       (map #(Integer/parseInt (str %)))
       (map-indexed #(* %2 (reduce * (repeat %1 2))))
       (reduce +)))

(defn decode [var-int]
  (->> var-int
       varint->bytes
       bytes->bin-str
       bin-str->int))
