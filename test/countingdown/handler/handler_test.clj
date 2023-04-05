(ns countingdown.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [midje.sweet :refer [facts =>]]
            [handler.handler :refer :all]))


(facts "prepare-admin"
       (prepare-admin  "username=admin&password=12345" ) => {:username "admin" :password "12345"})

(deftest right-route-main
  (testing "main route"
    (let [response (handler.handler/my-app (request :get "/"))]
      (is (= 200 (:status response))))))

(deftest wrong-route
  (testing "route not found"
    (let [response (handler.handler/my-app (request :get "/wrong"))]
      (is (= 404 (:status response))))))

(run-test wrong-route)