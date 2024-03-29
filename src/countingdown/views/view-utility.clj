(ns view.view-utility
  (:require [hiccup.page :as h]
            [hiccup.form :as form]
            ))


(defn common [title & body]
  "Html page"
  (h/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
     [:meta {:name "viewport" :content
             "width=device-width, initial-scale=1, maximum-scale=1"}]
     [:title title]
     (h/include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
                    "../style.css"  )]
    [:body {:style "height:100%"}
     [:div.wrapper
      [:div.middle
       body]]]))


(defn navbar []
  "navbar"
  [:nav.navbar.navbar-default
   [:div.container-fluid
    [:div.navbar-header
     [:a.navbar-brand {:href "/"} "PlanCookEat"]
     ]
    [:ul.nav.navbar-nav
     [:li.active [:a {:href "/food"} "FoodBase" ]]
     [:li.active [:a {:href "/data"} "Generate menu" ]]
     [:li.active [:a {:href "/recipes"} "Recipes" ]]
     (list
       [:ul.nav.navbar-nav.navbar-right
        [:li [:a {:href "/login"}[:span.glyphicon.glyphicon-log-in] "Login"]]
        [:li [:a {:href "/logout"}[:span.glyphicon.glyphicon-log-out] "Logout"]]
        [:li [:a {:href "/register"}[:span.glyphicon.glyphicon-pencil] "Register"]]
        #_[:form {:action "/admin/logout", :method "post"}
         [:input {:type "submit", :value "Logout"}]]])]]])
