(ns logic.csv-logic
  (:require [clojure.java.io :as io]
            [clojure.walk :as walk]
            [clojure-csv.core :as csv]
           ))

(defn read-csv
  "Read data from csv file."
  [file-name]
  (with-open [file (io/reader file-name)]
    (csv/parse-csv (slurp file))))

(defn write-csv [file-name data]
  (with-open [w (io/writer  file-name :append true)]
    (.write w (str "\n" data)))
  )

(defn get-data
  [row-data]
  (map #(zipmap (map keyword (first row-data)) %)
       (rest row-data)))

(defn change-type-food
  [food]
  (map #(assoc % :ID (read-string(:ID %))
                 :Calories (read-string(:Calories %))
                 :Fat (read-string(:Fat %))
                 :Protein (read-string(:Protein %))
                 :Carbohydrate (read-string(:Carbohydrate %))) food))

(def food (change-type-food (get-data (read-csv "MyFoodData1.csv"))))

(defn keywordize-data-ID
  [data]
  (reduce (fn [acc f]
            (assoc acc (:ID f) f))
          {} data))

;(get (keywordize-data-ID food) 5780)

#_(defn keywordize-data1
  [param data]
  (zipmap (map param data) data))

;(apply hash-map (zipmap (map :ID food) food))
(time (count (reduce (fn [acc f]
          (assoc acc (:ID f) f))
        {} food)))

;(defn cast-calories [map]
;  (assoc map :Calories (read-string (:Calories map)))
;  )
;
;(defn get-csv-data-cast [data]
;  (mapv %(cast-calories) data)
;  (first data))

(for [i food][:tr
              [:td (:name i)]
              [:td (:FoodGroup i)]
              [:td (:Calories i) ]
              [:td (:Protein i)]
              [:td (:Carbohydrate i) ]
              [:td (:Fat i) ]])


