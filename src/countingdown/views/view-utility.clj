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
                    "/style.css"  )
     ]

    [:body {:style "height:100%"}
     [:div.wrapper
      [:div.middle
       body]]]))


(defn navbar []
  "navbar"
  [:nav.navbar.navbar-default
   [:div.container-fluid
    [:div.navbar-header
     [:a.navbar-brand {:href "#"} "My meal planner"]
     ]
    [:ul.nav.navbar-nav
     [:li.active [:a {:href "/"} "Home" ]]
     ;another links
     ]
    ;...
      (list
        [:ul.nav.navbar-nav.navbar-right
         [:li
          ;(form/form-to  {:role "form" :id "search-form" :class "form-inline"}[:post "/"]
          ;
          ;               (form/text-field {:class "form-control" :placeholder "search"} :search "")
          ;               (form/submit-button {:class "btn" }
          ;                                   "Search"
          ;                                   ))
          ]
         [:li [:a {:href "/register"}
               [:span.glyphicon.glyphicon-user]
               "Sign up"]]
         [:li [:a {:href "/login"}
               [:span.glyphicon.glyphicon-log-in]
               "Login"]]
         ])
      ;)
    ]]

  )


;...


(defn footer []
  "footer"
  [:footer.fixed-footer
   [:div {:id "off-padding"}
    [:h4#footer-txt "Keep me fit"]
    ]
   ]
  )