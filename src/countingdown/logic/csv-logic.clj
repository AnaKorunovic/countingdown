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

(defn add-data-to-csv [location data]
  (with-open [w (io/writer  location :append true)]
    (.write w (str "\n" data)))
  )


(defn get-csv-data [location]
  (map #(-> (zipmap (first (read-csv location)) %)
            (walk/keywordize-keys))
       (rest (read-csv location)))
  )

(def data (get-csv-data "C:\\Users\\LENOVO\\Documents\\FON\\MASTER\\Alati i metode\\projekti\\countingdown\\src\\countingdown\\MyFoodData1.csv"))



;(defn cast-calories [map]
;  (assoc map :Calories (read-string (:Calories map)))
;  )
;
;(defn get-csv-data-cast [data]
;  (mapv %(cast-calories) data)
;  (first data))




