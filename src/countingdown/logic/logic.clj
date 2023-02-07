(ns logic.logic
  (:require [logic.csv-logic :as csv]))


;Men: BMR = 66 + (13.7 x wt in kg) + (5 x ht in cm) - (6.8 x age in years)
;Women: BMR = 655 + (9.6 x wt in kg) + (1.8 x ht in cm) - (4.7 x age in years)
;
;Sedentary = BMR x 1.2 (little or no exercise, desk job)
;Lightly active = BMR x 1.375 (light exercise/ sports 1-3 days/week)
;Moderately active = BMR x 1.55 (moderate exercise/ sports 6-7 days/week)
;Very active = BMR x 1.725 (hard exercise every day, or exercising 2 xs/day)
;Extra active = BMR x 1.9 (hard exercise 2 or more times per day, or training for
;                               marathon, or triathlon, etc.

(defn calculate-bmi [age gender height weight]
  (if (= gender "men") (int (- (+ 66 (* 13.7 weight) (* 5 height)) (* 6.8 age)))
                       (int (- (+ 655 (* 9.6 weight) (* 1.8 height)) (* 4.7 age))))
  )

(defn calculate-calorie-intake [age gender height weight activity]
  (let [bmi (logic.logic/calculate-bmi age gender height weight)]
    (case activity
      1 (* bmi 1.2)
      2 (* bmi 1.375)
      3 (* bmi 1.55)
      4 (* bmi 1.725)
      5 (* bmi 1.9)))
  )


;40UH 30Protein 30Fats
;1g uh/p=4kcal  1g f=9kcal
(defn calculate-percentage [cal]
  {:uh (int (/ (* cal 0.4) 4)) :p (int (/ (* cal 0.3) 4)) :f (int (/ (* cal 0.3) 9))})

;filter, sorting, random functions
(defn filter-by-group [data group]
  (filter #(= (:FoodGroup %) group) data))

(defn filter-by-calorie-range [data min max]
  (filter #(and (> (read-string (:Calories %)) min) (< (read-string (:Calories %)) max)) data))

(defn filter-by-calorie-max [data max]
  (filter #(< (read-string (:Calories %)) max) data))


(defn filter-by-group-kcal-range [data group kcal-min kcal-max]
  (filter-by-calorie-range (filter-by-food-group data group) kcal-min kcal-max))


(defn get-random-by-group [group]
  (take 1 (random-sample 0.1 (filter-by-group (csv/get-csv-data) group)))
  )

(defn sort-desc [data]
  (sort-by :Calories data))



;parsing id to integer
(defn parse-int [s]
  (Integer. (re-find  #"\d+" s )))

(defn make-key-integer [data]
  (update data :ID (5)))

(defn get-all-data []
  (csv/get-csv-data) )

(defn get-data []
  (take 5 (csv/get-csv-data) ))




;(defn get-random-by-group-01 [group]
;(take 1 (random-sample 0.01 (filter-by-group (csv/get-csv-data) group)))
;)

;(time (get-random-by-group "Egg"))
;(time (get-random-by-group-01 "Egg"))                       ;small difference in time - results are different each time

(defn make-breakfast [calories]
(let [egg (get-random-by-group "Egg") baked-foods (get-random-by-group "Baked Foods")]

  )
  )









(filter-by-group-kcal-range (csv/get-csv-data) "Prepared Meals" 300 600)
(calculate-percentage (* 0.4 2000))


;If you eat three meals a day, you should consume:
;
;30-35% of daily calories for breakfast (0.3-0.35)->0.3
;35-40% of daily calories for lunch (0.35-0.4) ->0.4
;25-35% of daily calories for dinner (0.25-0.35) ->0.3
;If you eat four meals a day, you should consume:
;
;25-30% of daily calories for breakfast ->0.25
;5-10% of daily calories for morning snack ->0.05
;35-40% of daily calories for lunch ->0.40
;25-30% of daily calories for dinner ->0.30
;If you eat five meals a day, you should consume:
;
;25-30% of daily calories for breakfast ->0.3
;5-10% of daily calories for morning snack ->0.1
;35-40% of daily calories for lunch ->0.35
;5-10% of daily calories for an afternoon snack ->0.05
;15-20% of daily calories for dinner ->0.2




