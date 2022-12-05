(ns countingdown.core
  (:require [compojure.core :refer :all]
            [hiccup.core :refer [html]]))


(defn home-view []
  (html
    [:p {:style "text-align: center;"} [:br]]
    [:p {:style "text-align: center;"} [:br]]
    [:p {:style "text-align: center;"} [:br]]
    [:p {:style "text-align: center;"} [:br]]
    [:hr]
    [:p
     {:style "text-align: center;"}
     [:span
      {:style
       "font-size: 48px; font-family: Arial, Helvetica, sans-serif; color: rgb(40, 50, 78);"}
      "-"]
     [:span
      {:style
       "font-size: 48px; font-family: \"Arial Black\", Gadget, sans-serif;"}
      "CountingDown"]
     [:span
      {:style
       "font-size: 48px; font-family: Arial, Helvetica, sans-serif; color: rgb(40, 50, 78);"}
      "-"]
     [:span
      {:style
       "font-size: 48px; font-family: \"Arial Black\", Gadget, sans-serif;"}]]
    [:p
     {:style "text-align: center;"}
     [:span
      {:style
       "font-size: 30px; font-family: Arial, Helvetica, sans-serif;"}
      [:em
       [:span {:style "color: rgb(40, 50, 78);"} "My fitness journey"]]]]
    [:p
     {:style "text-align: center;"}
     [:button
      {:onclick "window.location.href = '/groceries';",
       :style
       "align-items: center;\n  background-color: #FFFFFF;\n  border: 1px solid rgba(0, 0, 0, 0.1);\n  border-radius: .25rem;\n  box-shadow: rgba(0, 0, 0, 0.02) 0 1px 3px 0;\n  box-sizing: border-box;\n  color: rgba(0, 0, 0, 0.85);\n  display: inline-flex;\n  font-size: 16px;\n  font-weight: 600;\n  justify-content: center;\n      width:30%;"}
      "Start"]]
    [:hr]
    [:p [:br]]))