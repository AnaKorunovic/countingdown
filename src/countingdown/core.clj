(ns countingdown.core
  (:require
    [compojure.core :refer :all]
    [ring.adapter.jetty :as jetty]
    [logic.users-logic :as ul])
  (:gen-class))

(defn -main [& args]
  (ul/create-user {:username "admin" :password "12345"})
    (jetty/run-jetty handler.handler/my-app {:port 3005}))

(countingdown.main/-main)









