(ns view.recipes
  (:require
    [view.view-utility :as vutil]
    [compojure.core :refer :all]
    ))



(defn get-data-page []
  "table - recipes"
  (vutil/common "Recipes"
                (vutil/navbar)
                [:div {:class "btn-group btn-group-justified"}
                 [:a {:href "http://localhost:3002/addmeal", :class "btn btn-default"} "Add new recipe"]
                 ]
                ;...
                ))

(defn get-add-recipe-page []
  "add new recipe"
  (vutil/common "Add new recipe"
                (vutil/navbar)
                [:form { :style "margin-top:5em;margin-left:25%;max-width:50%;" :class "form-horizontal " :action "/addmeal" :method "POST"}

                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "name"} "Name:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :class "form-control", :id "name", :placeholder "Enter meal name"}]]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "category"} "Category:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "number", :class "form-control", :id "category", :placeholder "Enter meal category [breakfast/lunch/dinner/snacks]"}]]]
                 [:div {:class "form-group"}
                  [:div {:class "col-sm-offset-2 col-sm-10"}
                   [:button {:type "submit", :class "btn btn-default"} "Submit"]]]
                 ]
                ))

(defn get-data-add-page []
  "add new recipe"
  (vutil/common "Add new recipe"
                (vutil/navbar)
                [:h1 "Add a Location"]
                [:form {:action "/addmeal" :method "POST"}

                 [:p "x value: " [:input {:type "text" :name "x"}]]
                 [:p "y value: " [:input {:type "text" :name "y"}]]
                 [:p [:input {:type "submit" :value "submit location"}]]]
                ))



(defn page [{:keys [x y]}]
  "XXXXXXX"
  (vutil/common "XXXX"
                (vutil/navbar)
                [:label (str (str x) "-----" y)]
                ;...
                ))


