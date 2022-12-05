(ns countingdown.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [hiccup.core :refer [html]]))


 (defn make-row [grocery]
  (html [:tr [:td (:id grocery)] [:td (:name grocery)] [:td (:calPer100g grocery)] [:td [:a {:href (str "/groceries/" (:id grocery))} [:button {:style "background-color:#ffcc00; width:100%;"} "View"]]]]))

(defn groceries-view []
  (html [:style
         "table.GeneratedTable {\n  width: 70%;\n margin-left: auto;\n  margin-right: auto; background-color: #ffffff;\nborder-collapse: separate;\n  border-width: 1px;\n  border-color: #ffcc00;\n  border-style: groove;\n  color: #000000;\n}\n\ntable.GeneratedTable td, table.GeneratedTable th {\n  border-width: 1px;\n  border-color: #ffcc00;\n  border-style: groove;\n  padding: 3px;\n}\n\ntable.GeneratedTable thead {\n  background-color: #ffcc00;\n}"]
        [:h2 {:style "text-align: center;"} [:strong "Groceries"]]
        [:table
         {:class "GeneratedTable"}
         [:thead [:tr [:th "Id"] [:th "Name"] [:th "Calories/100g"] [:th "View"]]]
         [:tbody {:class "btnView"} nil
(map #(make-row %) groceries)
         ]]
       ))
(defn grocery-view [grocery]
  (html [:h4 {:style "text-align: center;"} [:strong (:name grocery)]]
        [:table
         {:style
          "width: 61.6926%; border-collapse: collapse; border-style: solid; border-color: yellow; height: 20px;",
          :border "1"}
         [:tbody
          [:tr
           [:td {:style "width: 45.681%;"} "Calories"]
           [:td {:style "width: 54.319%; text-align: left;"} (:calPer100g grocery)]]]]))

(defroutes handler
           (GET "/groceries" [] (groceries-view))
           (GET "/groceries/:id" [id] (grocery-view (groceries (read-string id))))
           )

(jetty/run-jetty (fn [req] (handler req)) {:port 3002 :join? false})





