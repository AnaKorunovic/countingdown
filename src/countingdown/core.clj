(ns countingdown.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [hiccup.core :refer [html]]))

; stara verzija - nije validno

 (defn make-row [grocery]
  (html [:tr [:td (:id grocery)] [:td (:name grocery)] [:td (:calper100g grocery)] [:td [:a {:href (str "/groceries/" (:id grocery))} [:button {:style "background-color:#ffcc00; width:100%;"} "View"]]]]))

(defn groceries-view []
  (html [:style
         "table.GeneratedTable {\n  width: 70%;\n margin-left: auto;\n  margin-right: auto; background-color: #ffffff;\nborder-collapse: separate;\n  border-width: 1px;\n  border-color: #ffcc00;\n  border-style: groove;\n  color: #000000;\n}\n\ntable.GeneratedTable td, table.GeneratedTable th {\n  border-width: 1px;\n  border-color: #ffcc00;\n  border-style: groove;\n  padding: 3px;\n}\n\ntable.GeneratedTable thead {\n  background-color: #ffcc00;\n}"]
        [:h2 {:style "text-align: center;"} [:strong "Groceries"]]
        [:table
         {:class "GeneratedTable"}
         [:thead [:tr [:th "Id"] [:th "Name"] [:th "Calories/100g"] [:th "View"]]]
         [:tbody {:class "btnView"} nil
(map #(make-row %) (get-groceries))
         ]]
        [:button
         {:onclick "window.location.href = '/grocery/add';",
          :style
          "align-items: center;\n  background-color: #FFFFFF;\n  border: 1px solid rgba(0, 0, 0, 0.1);\n  border-radius: .25rem;\n  box-shadow: rgba(0, 0, 0, 0.02) 0 1px 3px 0;\n  box-sizing: border-box;\n  color: rgba(0, 0, 0, 0.85);\n  display: inline-flex;\n  font-size: 16px;\n  font-weight: 600;\n  justify-content: center;\n      width:30%;"}
         "ADD NEW"]
       ))


(defn grocery-view [grocery]
  (html [:p
         {:style "text-align: center;"}
         [:span
          {:style
           "text-align: center; text-shadow: rgba(136, 136, 136, 0.4) 4px 3px 1px; font-family: \"Arial Black\", Gadget, sans-serif;"}
          "NAME"]]
        [:table
         {:style
          "width: 80%; background-color: rgb(240, 240, 240); color: rgb(0, 0, 0); border-collapse: collapse; border: 1px groove rgb(217, 217, 217); padding: 2px; margin: 0px auto;"}
         [:tbody
          [:tr
           [:td
            {:style
             "width: 50%; border: 1px groove rgb(217, 217, 217); text-align: center; padding: 2px;"}
            "NAME"]
           [:td
            {:style
             "width: 50%; border: 1px groove rgb(217, 217, 217); text-align: center; padding: 2px;"}
            "CALORIES/100g"]]
          [:tr
           [:td
            {:style
             "width: 50%; border: 1px groove rgb(217, 217, 217); padding: 2px;"}
            (:name grocery)]
           [:td
            {:style
             "width: 50%; border: 1px groove rgb(217, 217, 217); padding: 2px;"}
            (:calper100g grocery)]]]]
        [:p [:br]]
        [:h1
         [:button
          {:onclick "window.location.href = '/groceries';",
           :style
           "margin-left: 48em; width:20%;justify-content: center;align-items: center;\n  padding: 6px 14px;\n  font-family: -apple-system, BlinkMacSystemFont, 'Roboto', sans-serif;\n  border-radius: 6px;\n  border: none;\n  background: #6E6D70;\n  box-shadow: 0px 0.5px 1px rgba(0, 0, 0, 0.1), inset 0px 0.5px 0.5px rgba(255, 255, 255, 0.5), 0px 0px 0px 0.5px rgba(0, 0, 0, 0.12);\n  color: #DFDEDF;\n  user-select: none;"}
          "DELETE"]]
        [:p [:br]]))
(

(defroutes handler

           (GET "/" [] (home-view))
           (GET "/groceries" [] (groceries-view))
           (GET "/groceries/add" [] (grocery-add-new-view))
           (GET "/groceries/:id" [id] (grocery-view (nth (get-groceries) (- (read-string id) 1)))))

(jetty/run-jetty (fn [req] (handler req)) {:port 3002 :join? false})






