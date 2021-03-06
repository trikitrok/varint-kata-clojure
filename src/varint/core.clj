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
       (map (partial pad-left 7 "0"))))

(defn- int->bin-str [num]
  (Long/toBinaryString num))

(def ^:private drop-most-significat-bits (partial map rest))

(defn- varint->bytes [varint]
  (->> varint
       (partition-in-blocks-of 8)
       drop-most-significat-bits))

(defn- bytes->bin-str [bytes]
  (-> bytes
      reverse
      flatten))

(defn- int-pow [b exp]
  (reduce * (repeat exp b)))

(defn- char->int [ch]
  (Integer/parseInt (str ch)))

(def ^:private bin-str->bits (partial map char->int))

(defn- bits->int [bits]
  (->> bits
       reverse
       (map-indexed #(* %2 (int-pow 2 %1)))
       (reduce +)))

(defn- bin-str->int [bin-str]
  (-> bin-str
      bin-str->bits
      bits->int))

(defn- bytes->varint [bytes]
  (->> bytes
       add-most-significat-bits
       (apply str)))

(defn encode [num]
  (-> num
      int->bin-str
      bin-str->bytes
      bytes->varint))

(defn decode [varint]
  (-> varint
      varint->bytes
      bytes->bin-str
      bin-str->int))
