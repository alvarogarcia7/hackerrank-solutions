(use '[clojure.string :only (split triml)])

(defn read-ints []
  (map #(Integer/parseInt %) (split (read-line) #"\s+")))

(defn is-canceled? [testcase]
  (let [cancellation-treshold (:treshold testcase)
        arrived-late (count (filter #(>= 0 %) (:arrival-times testcase)))]
  (> cancellation-treshold arrived-late)))

(let [t (Integer/parseInt (read-line))
      matrix (for [_ (range t)] (let [[students treshold] (read-ints)
                                    arrival-times (take students (read-ints))]
                                    {:arrival-times arrival-times :treshold treshold}))]
  (->> matrix
    (map is-canceled?)
    (map #(if % "YES" "NO"))
    (map println)
    doall))
