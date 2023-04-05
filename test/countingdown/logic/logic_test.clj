(ns countingdown.logic-test
  (:require [clojure.test :refer :all]
            [logic.logic :refer :all]
            [midje.sweet :refer [facts =>]]))

(facts "only-digits"
       (logic.logic/only-digits "123")=> true)

(facts "validate-age-gender-weight-height"
       (validate-input "23" "female" "170" "66") => ""
       (validate-input "23l" "female" "170" "66") => "Age field must contains only digits. "
       (validate-input "23l" "female" "66l" "80") => "Age field must contains only digits. Height field must contains only digits. "
       (validate-input "23" "femal" "170" "66") => "Gender must be male or female"
       (validate-input "ll23" "female" "170" "66l") => "Age field must contains only digits. Weight field must contains only digits. "
       (validate-input "" "male" "170" "") => "You must fill all fields. ")

(deftest bmi-test
  (testing "If bmi function is valid."
    (is (= (clojure.math/round (calculate-bmi 23 "male" 189 99)) 1838))))

(deftest calorie-intake-test
  (testing "If calorie intake function is valid."
    (is (= (clojure.math/round (calculate-calorie-intake 23 "male" 189 99 4)) 3170))))

(deftest group-not-exist-test
  (testing "If group name is invalid."
    (is (= (filter-by-group "invalid") ()))))






(run-tests)