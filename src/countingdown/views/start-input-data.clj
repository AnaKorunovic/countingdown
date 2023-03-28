(ns view.start-input-data
  (:require [hiccup.form :as form]
            [view.view-utility :as vutil]
            ))


(def activities [["Sedentary" 1]["Lightly active" 2 ]["Moderately active" 3]["Very active" 4]["Extra active" 5]])

(defn get-data-page []
  "get data and print report"
  (vutil/common "Home page"
                 (vutil/navbar)
                [:form { :style "margin-top:5em;margin-left:25%;max-width:50%;", :class "form-horizontal " ,:action "/report", :method "POST"}
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "age" } "Age:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :value 30 :class "form-control",:required "required", :name "age", :placeholder "Enter your age"} ]]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "gender"} "Gender:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :class "form-control",:required "required",:value "male", :name "gender",  :placeholder "Enter your gender [male/female]"}]]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "height"} "Height:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "number", :class "form-control",:required "required", :name "height",:value 188 :placeholder "Enter your height"} ]]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "weight"} "Weight:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "number", :class "form-control",:required "required", :name "weight",:value 88 :placeholder "Enter your weight"} ]]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "activity"} "Activity:"]
                  [:div {:class "col-sm-10"}
                   (form/drop-down {:class "form-class btn-sm btn-primary dropdown-toggle dropdown-toggle-split"} "activity" activities)]]
                 [:div {:class "form-group"}
                  [:div {:class "col-sm-offset-2 col-sm-10"}
                   [:button {:type "submit", :class "btn btn-default"} "Submit"]]]]))

(defn print-report [report]
  (vutil/common "Home page"
                (vutil/navbar)
                [:div {:class "text-center "}
                 [:p [:b "Total calories:  "](Math/round (:total-calories report))]
                 [:table {:class "table table-bordered table-responsive",:style "width:50%; margin-left:25%;" }
                  [:thead
                   [:tr
                    [:th "Carbohydrates"]
                    [:th "Protein"]
                    [:th "Fats"]]]
                  [:tbody
                   [:tr
                    [:td (:uh (:nutrient-percentage report))]
                    [:td (:p (:nutrient-percentage report))]
                    [:td (:f (:nutrient-percentage report))]]]]
                 [:h3 "Breakfast  "
                   [:span {:class "label label-default"} (str "Egg - " ( :egg (:breakfast report)) "g\n")] [:br]
                   [:span {:class "label label-default"} (str "Baked food - " (:bread (:breakfast report)) "g")]]
                 [:h3 "Lunch  "
                  [:span {:class "label label-default"} (str "Main dish - " ( :meats (:lunch report)) "g\n")][:br]
                  [:span {:class "label label-default"} (str "Dessert - " (:fruits (:lunch report)) "g")]]
                 [:h3 "Dinner  "
                  [:span {:class "label label-default"} (str "Main dish - " ( :meal (:dinner report)) "g")] [:br]
                  [:span {:class "label label-default"} (str "Snacks - " (:snacks (:dinner report)) "g")] ]]))
