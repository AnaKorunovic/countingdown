(ns logic.logic
  (:require [clojure.data.csv :as csv]))


;Men: BMR = 66 + (13.7 x wt in kg) + (5 x ht in cm) - (6.8 x age in years)
;Women: BMR = 655 + (9.6 x wt in kg) + (1.8 x ht in cm) - (4.7 x age in years)
;
;Sedentary = BMR x 1.2 (little or no exercise, desk job)
;Lightly active = BMR x 1.375 (light exercise/ sports 1-3 days/week)
;Moderately active = BMR x 1.55 (moderate exercise/ sports 6-7 days/week)
;Very active = BMR x 1.725 (hard exercise every day, or exercising 2 xs/day)
;Extra active = BMR x 1.9 (hard exercise 2 or more times per day, or training for
;                               marathon, or triathlon, etc.

(defn calculate-calorie-intake [age gender height weight activity]
  (let [bmi (logic.logic/calculate-bmi age gender height weight)]
    (case activity
      1 (* bmi 1.2)
      2 (* bmi 1.375)
      3 (* bmi 1.55)
      4 (* bmi 1.725)
      5 (* bmi 1.9)))
  )

(defn calculate-bmi [age gender height weight]
  (if (= gender "men") (int (- (+ 66 (* 13.7 weight) (* 5 height)) (* 6.8 age)))
                     (int (- (+ 655 (* 9.6 weight) (* 1.8 height)) (* 4.7 age))))
  )

;40UH 30Protein 30Fats
;1g uh/p=4kcal  1g f=9kcal
(defn calculate-percentage [cal]
  {:uh  (int (/ (* cal 0.4) 4)) :p (int (/ (* cal 0.3) 4)) :f (int (/ (* cal 0.3) 9))})



