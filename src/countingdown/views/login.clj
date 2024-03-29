(ns view.login
  (:require [hiccup.form :as form]
            [db.users-db :ad udb]
            [view.view-utility :as vutil]
            [ring.util.anti-forgery :refer (anti-forgery-field)]
            [db.users-db :as udb]))


(defn get-login-page
  [& [message]]
  "login page"
  (vutil/common "Login page"
                (vutil/navbar)
                (when message [:div.alert.alert-danger message])
                [:form { :style "margin-top:5em;margin-left:25%;max-width:50%;", :class "form-horizontal " ,:action "/login", :method "POST"}
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "username"} "Username:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :class "form-control",:required "required",:name "username"}]]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "password"} "Password:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :class "form-control",:required "required",:name "password"}]]]
                 [:div {:class "form-group"}
                  [:div {:class "col-sm-offset-2 col-sm-10"}
                   [:button {:type "submit", :class "btn btn-default"} "Submit"]]]]))

(defn get-register-page
  [& [message]]
  "register page"
  (vutil/common "Register page"
                (vutil/navbar)
                (when message [:div.alert.alert-danger message])
                [:form {:style "margin-top:5em;margin-left:25%;max-width:50%;", :class "form-horizontal ", :action (str "/register/" (udb/get-next-user-id)), :method "POST"}
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "name"} "Name:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :class "form-control",:required "required",:name "name"}]]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "username"} "Username:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :class "form-control",:required "required",:name "username"}]]]
                 [:div {:class "form-group"}
                  [:label {:class "control-label col-sm-2", :for "password"} "Password:"]
                  [:div {:class "col-sm-10"}
                   [:input {:type "text", :class "form-control",:required "required",:name "password"}]]]
                 (anti-forgery-field)
                 [:div {:class "form-group"}
                  [:div {:class "col-sm-offset-2 col-sm-10"}
                   [:button {:type "submit", :class "btn btn-default"} "Submit"]]]]))
