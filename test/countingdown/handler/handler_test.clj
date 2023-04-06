(ns countingdown.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [midje.sweet :refer [facts =>]]
            [ring.mock.request :as mock]
            [logic.users-logic :as uad]
            [handler.handler :refer :all]
            [db.users-db :as udb]))


(facts "prepare-admin"
       (prepare-admin  "username=admin&password=12345" ) => {:username "admin" :password "12345"})

(deftest right-route-main
  (testing "main route"
    (let [response (handler.handler/my-app (request :get "/"))]
      (is (= 200 (:status response))))))

(deftest test-data-route
  "test if it will return data page is user is not logged /data route"
  (testing "data route"
    (let [response (handler.handler/all-routes (mock/request :get "/data"))]
      (is (= nil (:status response))))))

(deftest wrong-route
  (testing "route not found"
    (let [response (handler.handler/my-app (request :get "/wrong"))]
      (is (= 404 (:status response))))))

(deftest test-admin-login-route
  "test if it will return /recipes page after /login route"
  (testing "admin route"
    (let [response (handler.handler/all-routes (mock/request :get "/login"))]
      (is (= 200 (:status response))))))

(deftest test-register-route
  "Test if it will 200 in /register."
  (testing "register route"
    (let [response (handler.handler/all-routes (mock/request :get "/register/"))]
      (is (= 200 (:status response))))))

(deftest test-admin-login
  "test if admin can login"
  (testing "admin login"
    (let [logged? (uad/admin-exist? {:username "admin" :password "12345"})]
      (is (= logged? true)))))

(deftest test-name-session
  "When user is logged, should return name with id stored in session-it will be 1"
  (testing "name-session"
    (let [exists? (udb/get-name-by-id-session 1)]
      (is (string? exists?)))))

(run-test test-admin-login-route)
(run-test test-admin-login)