(ns varint.core)

(defn- pad-left [length element bin-num]
  (concat (repeat (- length (count bin-num)) element) bin-num))

(defn- add-most-significat-bits [bytes]
  (flatten (concat (map #(cons "1" %) (butlast bytes))
                   (cons "0" (last bytes)))))

(defn- partition-in-blocks-of [block-size coll]
  (partition-all block-size block-size coll))

(defn- bin-str->bytes [bin-str]
  (->> bin-str
       reverse
       (partition-in-blocks-of 7)
       (map reverse)
       (map (partial pad-left 7 "0"))
       (map #(apply str %))))

(defn- int->bin-str [num]
  (Long/toBinaryString num))

(def ^:private drop-most-significat-bits (partial map rest))

(defn- varint->bytes [varint]
  (->> varint
       (partition-in-blocks-of 8)
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

(defn encode [num]
  (->> num
       int->bin-str
       bin-str->bytes
       add-most-significat-bits
       (apply str)))

(defn decode [varint]
  (->> varint
       varint->bytes
       bytes->bin-str
       bin-str->int))
