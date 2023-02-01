(ns logic.csv-logic
  (:require [clojure.java.io :as io]
            [clojure.walk :as walk]
            [clojure-csv.core :as csv]
           ))


;(defn empty-string-to-nil
;  "Returns a nil if given an empty string S, otherwise returns S."
;  [s]
;  (if (and (string? s) (empty? s))
;    nil
;    s))
;
;(defn dissoc-nils
;  "Drops keys with nil values, or nil keys, from the hashmap H."
;  [h]
;  (into {} (filter (fn [[k v]] (and v k)) h)))
;
;
;(defn load-csv
;  "Returns a data structure loaded from a CSV file at FILEPATH."
;  [filepath]
;  (with-open [reader (clojure.java.io/reader filepath)]
;    (->> (csv/read-csv reader)
;         (map (fn [row] (map empty-string-to-nil row)))
;         (doall))))
;
;;(defn save-csv
;;  "Saves a vector of vectors DATA (i.e. a CSV) to disk at FILEPATH. "
;;  [vec-of-vecs filepath]
;;  (with-open [writer (clojure.java.io/writer filepath)]
;;    (csv/write-csv writer vec-of-vecs)))
;
;(defn tabular->maps
;  "Converts a vector of vectors into a vector of maps. Assumes that the
;  first row of the CSV is a header that contains column names."
;  [tabular]
;  (let [header (first tabular)]
;    (-> (map zipmap (repeat header) (rest tabular))
;        (mapv dissoc-nils))))
;
;(defn tabular->maps
;  "Converts a vector of vectors into a vector of maps. Assumes that the
;  first row of the CSV is a header that contains column names."
;  [tabular]
;  (let [header (first tabular)]
;    (-> (map zipmap (repeat header) (rest tabular))
;        )))


;(defn maps->tabular
;  "Converts a vector of vectors into a vector of maps."
;  [rowmaps]
;  (let [columns (vec (sort (into #{} (map name (flatten (map keys rowmaps))))))]
;    (vec (conj (for [row rowmaps]
;                 (vec (for [col columns]
;                        (str (get row col "")))))
;               columns))))

;(comment
;
;  (def data (tabular->maps (load-csv "/path/to/mycsv.csv")))
;
;  (save-csv! (maps->tabular data) "/some/other/path.csv")
;
;  )

;(def data (tabular->maps (load-csv "C:/Users/LENOVO/Documents/FON/MASTER/Alati i metode/projekti/MyFoodData.csv")))
;
;(defn get-exact-map [map]
;  (let [{:strs [ID name FoodGroup Calories Fat Protein Carbohydrate]} map]
;
;    {:id ID :name name :group FoodGroup :calories Calories :fat Fat :protein Protein :carbs Carbohydrate}
;    ))
;
;(get-exact-map (first data))
;(print data)
;
;(map get-exact-map data)


;////////////////////////////
;(defn load-csv-file []
;  (csv_map/parse-csv (slurp "C:/Users/LENOVO/Documents/FON/MASTER/Alati i metode/projekti/MyFoodData.csv")))
;
;
;
;(let [[header rows] (-> "C:/Users/LENOVO/Documents/FON/MASTER/Alati i metode/projekti/MyFoodData.csv"
;                        io/file
;                        io/reader
;                        csv/read-csv)]
;  (map #(-> (zipmap header %)
;            (walk/keywordize-keys))
;       rows))

;(defn read-csv-2 []
;  (csv/read-csv "C:/Users/LENOVO/Documents/FON/MASTER/Alati i metode/projekti/MyFoodData.csv"))


(defn read-csv
  "Read data from file."
  [fname]
  (with-open [file (io/reader fname)]
    (-> file
        (slurp)
        (csv/parse-csv))))

(defn make-maps []
  (map #(-> (zipmap (first (read-csv "C:/Users/LENOVO/Documents/FON/MASTER/Alati i metode/projekti/MyFoodData.csv")) %)
            (walk/keywordize-keys))
       (rest (read-csv "C:/Users/LENOVO/Documents/FON/MASTER/Alati i metode/projekti/MyFoodData.csv")))
  )

(defn filter-by-food-group [group]
  (filter #(= (:FoodGroup %) group) (make-maps)))

(defn filter-by-calorie-range [min max]
  (filter #(and (> (read-string (:Calories %)) min) (< (read-string (:Calories %)) max)) (make-maps)))

(first (filter-by-calorie-range 230 240))




