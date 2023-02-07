(ns view.start-input-data
  (:require [hiccup.form :as form]
            [view.view-utility :as vutil]
            ))

(defn get-data-page []
  "get data and print report"
  (vutil/common "Home page"
                 (vutil/navbar)
                 (form/form-to  {:role "form" :id "first-form" :class "form-horizontal"}[:GET "/new"]
                                [:div {:class "form-group"}
                                 (form/label {:class "reg-label control-label col-md-2"} "title" "Age:")
                                 [:div.col-md-9
                                  (form/text-field  {:class "form-control" :required "required" } :title "")]]
                                [:br]
                                [:div {:class "form-group"}
                                 (form/label {:class "reg-label control-label col-md-2"} "text" "Gender:")
                                 [:div.col-md-9
                                  (form/text-area  { :id "txt-area" :class "form-control"  :required "required" } :text )]]
                                [:br]
                                (form/submit-button {:class "btn btn-info col-md-offset-6"} "Generate"))
                ;..

                 ))

;..