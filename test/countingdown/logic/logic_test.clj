(ns countingdown.logic-test
  (:require [clojure.test :refer :all]
            [logic.logic :refer :all]
            [db.users-db :as udb]
            [logic.users-logic :as ul]
            [midje.sweet :refer [facts =>]]
            [logic.users-logic :as uad]))

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

(deftest test-check-credentials-user
  "if user credential are true then it will return that user."
  (testing "Check user"
    (let [exists? (udb/user-exists? {:username "anak" :password "anak99"})]
      (is (= exists? ({:id 2, :name "Ana", :username "anak", :password "anak99"}))))))

(deftest test-check-credentials-admin
  "if user credential are then it will return true"
  (testing "check admin"
    (let [exists? (ul/admin-exist? {:username "admin" :password "12345" })]
      (is (= exists? true)))))

(deftest test-get-next-id-user
  "test should return next int id, that is > 0"
  (testing "new id"
    (let [next (udb/get-next-user-id)]
      (is (= (integer? (udb/get-next-user-id)) true))
      (is (> next 0)))))

(deftest test-get-user-id-by-username-and-password
  "Test if it will return user id greater than 0"
  (testing "user-id-by-username-and-password"
    (let [res (udb/get-user-id-by-username-and-password "anak" "anak99")]
      (is (= (integer? (udb/get-user-id-by-username-and-password "anak" "anak99")) true))
      (is (> res 0)))))

;(run-tests)