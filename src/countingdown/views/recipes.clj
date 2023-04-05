(ns view.recipes
  (:require
    [view.view-utility :as vutil]
    [compojure.core :refer :all]
    [hiccup.form :as form]))

(defn get-data-page [data]
  "table - recipes"
  (vutil/common "Recipes"
                (vutil/navbar)
                [:div {:class "btn-group btn-group-justified"}
                 [:a {:href "http://localhost:3002/admin/addmeal", :class "btn btn-default"} "Add new recipe"]]
                [:div {:class "m-5 pb-5 container", :style "margin-top:2em;"}
                 [:table {:class "table table-bordered"}
                  [:thead
                   [:tr
                    [:th "Name"]
                    [:th "Category"]
                    [:th "Ingredients"]
                    ]]
                  [:tbody
                   (for [i data][:tr
                                 [:td (:name i)]
                                 [:td (:category i)]
                                 [:td (:ingredients i)]])]]]))

(def categories [["Breakfast" 1]["Lunch" 2 ]["Dinner" 3]])

(defn get-add-recipe-page []
  "add new recipe"
  (vutil/common "Add new recipe"
                (vutil/navbar)
                [:form { :style "margin-top:5em;margin-left:25%;max-width:50%;" :class "form-horizontal " :action "/addmeal" :method "POST"}
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "name"} "Name:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :class "form-control", :name "name", :placeholder "Enter meal name"}]]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "category"} "Category:"]
                  [:div {:class "col-sm-10"}
                   (form/drop-down {:class "form-class btn-sm btn-primary dropdown-toggle dropdown-toggle-split"} "category" categories)]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "Ingredients"} "ingredients:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :class "form-control input-lg", :name "ing", :id "ing", :placeholder "Enter your recipe"}]]]
                 [:div {:class "form-group"}
                  [:div {:class "col-sm-offset-2 col-sm-10"}
                   [:button {:type "submit", :class "btn btn-default"} "Submit"]]]]))





