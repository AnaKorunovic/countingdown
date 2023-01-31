(ns countingdown.main
  (:require
    [compojure.core :refer :all]
    [ring.adapter.jetty :as jetty]
    [handler.handler :as handler])
  (:gen-class))

(defn -main [& args]
  (jetty/run-jetty (fn [req] (handler/handler req)) {:port 3002 :join? false}))



