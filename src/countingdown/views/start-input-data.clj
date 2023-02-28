(ns view.start-input-data
  (:require [hiccup.form :as form]
            [view.view-utility :as vutil]
            ))

(defn get-data-page []
  "get data and print report"
  (vutil/common "Home page"
                 (vutil/navbar)
                 (form/form-to  {:role "form" :id "first-form" :class "form-horizontal"}[:POST "/report"]
                                [:div {:class "form-group"}
                                 (form/label {:class "reg-label control-label col-md-2"} "age" "Age:")
                                 [:div.col-md-9
                                  (form/text-field  {:class "form-control" :required "required" } :age "33")]]
                                [:br]
                                [:div {:class "form-group"}
                                 (form/label {:class "reg-label control-label col-md-2"} "gender" "Gender:")
                                 [:div.col-md-9
                                  (form/text-field { :id "txt-area" :class "form-control"  :required "required" } :gender "men")]]
                                [:br]
                                [:div {:class "form-group"}
                                 (form/label {:class "reg-label control-label col-md-2"} "height" "Height:")
                                 [:div.col-md-9
                                  (form/text-field  {:class "form-control" :required "required" } :height "188")]]
                                [:br]
                                [:div {:class "form-group"}
                                 (form/label {:class "reg-label control-label col-md-2"} "weight" "weight:")
                                 [:div.col-md-9
                                  (form/text-field  {:class "form-control" :required "required" } :weight "97")]]
                                [:br]
                                [:div {:class "form-group"}
                                 (form/label {:class "reg-label control-label col-md-2"} "activity" "activity:")
                                 [:div.col-md-9
                                  (form/text-field  {:class "form-control" :required "required" } :activity "1")]]
                                [:br]
                                (form/submit-button {:class "btn btn-info col-md-offset-6"} "Generate"))
                ;..

                 ))

(defn print-report [report]
  (vutil/common "Home page"
                (vutil/navbar)
([:label {:class "control-label col-sm-2", :for "pwd"} "Password:"])
                ))
;..