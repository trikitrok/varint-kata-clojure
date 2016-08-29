(defproject varint "0.0.1-SNAPSHOT"
  :description "varint kata"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:dev {:dependencies
                   [[midje "1.7.0"]
                    [org.clojure/test.check "0.9.0"]]}
             :midje {}})
