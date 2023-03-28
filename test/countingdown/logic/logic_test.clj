(ns countingdown.logic-test
  (:require [clojure.test :refer :all]
            [logic.logic :refer :all]))


(deftest bmi-test
  (testing "If bmi function is valid."
    (is (= (clojure.math/round (calculate-bmi 23 "male" 189 99)) 1838))))

(deftest calorie-intake-test
  (testing "If calorie intake function is valid."
    (is (= (clojure.math/round (calculate-calorie-intake 23 "male" 189 99 4)) 3170))))





(run-tests)