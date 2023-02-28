(ns logic.csv-logic
  (:require [clojure.java.io :as io]
            [clojure.walk :as walk]
            [clojure-csv.core :as csv]
           ))

(defn read-csv
  "Read data from file."
  [fname]
  (with-open [file (io/reader fname)]
    (-> file
        (slurp)
        (csv/parse-csv))))

(defn get-csv-data []
  (map #(-> (zipmap (first (read-csv "C:\\Users\\LENOVO\\Documents\\FON\\MASTER\\Alati i metode\\projekti\\countingdown\\src\\countingdown\\MyFoodData1.csv")) %)
            (walk/keywordize-keys))
       (rest (read-csv "C:\\Users\\LENOVO\\Documents\\FON\\MASTER\\Alati i metode\\projekti\\countingdown\\src\\countingdown\\MyFoodData1.csv")))
  )

(def data (get-csv-data))

;(defn cast-calories [map]
;  (assoc map :Calories (read-string (:Calories map)))
;  )
;
;(defn get-csv-data-cast [data]
;  (mapv %(cast-calories) data)
;  (first data))




