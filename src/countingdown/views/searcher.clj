(ns view.searcher
  (:require
            [view.view-utility :as vutil]
            [compojure.core :refer :all]
            [hiccup.page :as h]
            [hiccup.form :as form]
            ))



(defn get-data-page [data]
  "table - search page"
  (vutil/common "Food database"
                (vutil/navbar)
                [:div {:class "btn-group btn-group-justified"}
                 [:a {:href "http://localhost:3002/food/Egg", :class "btn btn-default"} "Egg"]
                 [:a {:href "http://localhost:3002/food/Fish", :class "btn btn-default"} "Fish"]
                 [:a {:href "http://localhost:3002/food/Fruits", :class "btn btn-default"} "Fruits"]
                 [:a {:href "http://localhost:3002/food/Meats", :class "btn btn-default"} "Meats"]
                 [:a {:href "http://localhost:3002/food/Snacks", :class "btn btn-default"} "Snacks"]
                 [:a {:href "http://localhost:3002/food/Vegetables", :class "btn btn-default"} "Vegetables"]
                 ]

                [:div {:class "m-5 pb-5 container", :style "margin-top:2em;"}
                 [:table {:class "table table-bordered"}
                  [:thead
                   [:tr
                    [:th "Name"]
                    [:th "FoodGroup"]
                    [:th "Calories"]
                    [:th "Protein"]
                    [:th "Carbs"]
                    [:th "Fat"]]]
                  [:tbody

                   (for [i data][:tr
                                 [:td (:name i)]
                                 [:td (:FoodGroup i)]
                                 [:td (:Calories i) ]
                                 [:td (:Protein i)]
                                 [:td (:Carbohydrate i) ]
                                 [:td (:Fat i) ]])
                   ]]]
                ))




