(use '[clojure.string :only (split triml)])

(defn read-int []
  (Integer/parseInt (read-line)))
  
(defn candidate-sums[N] 
  (->> (range)
    (map #(* 3 %))
    (take-while #(<= % N))))

(defn find-decent-composition [N]
  (->> (candidate-sums N)
    (map #(-> [% (- N %)]))
    (filter #(= 0 (mod (second %) 5)))
    (take 1)))
    
(defn generate-decent [[fives threes]]
  (apply str (concat 
    (take fives (repeat 5))
    (take threes (repeat 3)))))
    
(defn make-decent [composition]
  (if (empty? composition)
    "-1"
    (generate-decent (first composition))))

(let [t (Integer/parseInt (read-line))
      matrix (for [_ (range t)] (read-int))]
  (->> matrix
    (map find-decent-composition)
    (map make-decent)
    (map println)
    doall))
    