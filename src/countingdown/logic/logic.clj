(ns logic.logic
  (:require [logic.csv-logic :as csv]
            [clojure.math :as math]
            [clojure.string :as s]))


;Men: BMR = 66 + (13.7 x wt in kg) + (5 x ht in cm) - (6.8 x age in years)
;Women: BMR = 655 + (9.6 x wt in kg) + (1.8 x ht in cm) - (4.7 x age in years)
;
;Sedentary = BMR x 1.2 (little or no exercise, desk job)
;Lightly active = BMR x 1.375 (light exercise/ sports 1-3 days/week)
;Moderately active = BMR x 1.55 (moderate exercise/ sports 6-7 days/week)
;Very active = BMR x 1.725 (hard exercise every day, or exercising 2 xs/day)
;Extra active = BMR x 1.9 (hard exercise 2 or more times per day, or training for
;                               marathon, or triathlon, etc.

(defn only-digits
  [string]
  (every? #(Character/isDigit %) string))
(defn is-blank
  [string]
  (every? #(Character/isWhitespace %)  string))

(defn validate-input [age gender height weight]
  (str
    (when (or (is-blank age) (is-blank gender) (is-blank (str height)) (is-blank (str weight))) "You must fill all fields. ")
    (when-not (only-digits (clojure.string/replace (str age)  #"\." "")) "Age field must contains only digits.")
    (when (nil? age) "Age field must contain only digits. ")
    (when-not (only-digits (clojure.string/replace (str height)  #"\." "")) "Height field must contains only digits.")
    (when-not (only-digits (clojure.string/replace (str weight)  #"\." "")) "Weight field must contains only digits.")
    (when-not (or (= gender "male") (= "female" gender)) "Gender must be male or female")))

(defn calculate-bmi
  [age gender height weight]
  (if (= gender "men") (- (+ 66 (* 13.7 weight) (* 5 height)) (* 6.8 age))
                       (- (+ 655 (* 9.6 weight) (* 1.8 height)) (* 4.7 age))))

(defn calculate-calorie-intake
  [age gender height weight activity]
  (let [bmi (logic.logic/calculate-bmi age gender height weight)]
    (case activity
      1 (* bmi 1.2)
      2 (* bmi 1.375)
      3 (* bmi 1.55)
      4 (* bmi 1.725)
      5 (* bmi 1.9))))

;40UH 30Protein 30Fats
;1g uh/p=4kcal  1g f=9kcal
(defn calculate-percentage
  [cal]
  {:uh (int (/ (* cal 0.4) 4)) :p (int (/ (* cal 0.3) 4)) :f (int (/ (* cal 0.3) 9))})

;filter, sorting, random functions
;data kao ulazni argument
(defn filter-by-group
  [group]
  (filter #(= (:FoodGroup %) group) csv/food))

(defn filter-by-calorie-range
  [data min max]
  (filter #(and (> (:Calories %) min) (< (:Calories %) max)) data))

(defn filter-by-calorie-max-min
  [operator number]
  (filter #(operator (:Calories %) number) csv/food))

(defn filter-by-name
  [name]
  (filter #(true? (s/includes? (:name %) name) ) csv/food))

(defn get-random-by-group
  [group]
  (rand-nth (filter-by-group group)))

(defn sort-desc
  [data]
  (sort-by :Calories data))

;parsing id to integer
(defn parse-int
  [s]
  (Integer. (re-find  #"\d+" s )))

(defn get-all-data
  []
  (csv/food))

(defn get-data-recipes
  []
  (csv/get-data (csv/read-csv "Recepies.csv")))

;(time (get-random-by-group "Egg"))
;(time (get-random-by-group-01 "Egg"))                       ;small difference in time - results are different each time

;(filter-by-group-kcal-range (csv/get-csv-data) "Prepared Meals" 300 600)
;(calculate-percentage (* 0.4 2000))
;(time (csv/get-csv-data)) ;
;(time csv/data)           ;manje vremena treba da se ucita
;(time (get-random-by-group-15 "Egg") )                      ;12.123msec
; manjam poziv funkcije za ATOM
;(time (get-random-by-group-15 "Egg"))                       ;2.43msec
;(get-random-by-group "Egg")                                 ;Definisanjem data u logic ubrzalo se ucitavanje tabela


;*****************CREATION PERSONAL MEALS FROM DATABASE FOOD******************************
(defn add-new-meal
  [data]
  (let [category (case (:category data)
                   "1" "breakfast"
                   "2" "lunch"
                   "3" "dinner")]
    (let [row (str (:name data) "," category "," (:ing data))]
      (csv/write-csv "Recepies.csv" row))))

;****************GENERATION MENU**********************************************************

(defn make-breakfast [calories]
  (let [egg (get-random-by-group "Egg") baked-foods (get-random-by-group "Baked Foods")]
    {:egg {:name (:name egg)
           :amount (math/round (/ (* calories 0.6 100) (:Calories egg)))},
     :bread {:name (:name baked-foods)
             :amount (math/round(/ (* calories 0.4 100) (:Calories baked-foods)))}}))

(defn make-lunch
  [calories]
  (let [meats (get-random-by-group "Meats") fruit (get-random-by-group "Fruits")]
    {:meats {:name (:name meats)
           :amount (math/round (/ (* calories 0.7 100) (:Calories meats)))},
     :fruits {:name (:name fruit)
             :amount (math/round(/ (* calories 0.3 100) (:Calories fruit)))}}))

(defn make-dinner [calories]
  (let [meal (get-random-by-group "Prepared Meals") snacks (get-random-by-group "Snacks")]
    {:meal {:name (:name meal)
            :amount (math/round (/ (* calories 0.7 100) (:Calories meal)))},
     :snacks {:name (:name snacks)
              :amount (math/round(/ (* calories 0.3 100) (:Calories snacks)))}}))

(defn get-report [age gender height weight activity]
  (let [cal (calculate-calorie-intake age gender height weight activity) ]
    {:total-calories cal
     :nutrient-percentage (calculate-percentage cal)
     :breakfast (make-breakfast (* cal 0.3))
     :lunch (make-lunch (* cal 0.4))
     :dinner (make-dinner (* cal 0.3))
     })
  )

;25-30% of daily calories for breakfast ->0.25
;35-40% of daily calories for lunch ->0.40
;25-35% of daily calories for dinner ->0.35

;If you eat five meals a day, you should consume:
;
;25-30% of daily calories for breakfast ->0.3
;5-10% of daily calories for morning snack ->0.1
;35-40% of daily calories for lunch ->0.35
;5-10% of daily calories for an afternoon snack ->0.05
;15-20% of daily calories for dinner ->0.2

;mapa sa kljucevima food groupa, pa ce svaka da ima listu keyeva-id

