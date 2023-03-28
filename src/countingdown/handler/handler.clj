(ns handler.handler
  (:require [compojure.core :refer :all]
            [view.start-input-data :as sid]
            [view.searcher :as searcher]
            [view.recipes :as recipe]
            [ring.util.response :as response]
            [logic.logic :as logic]
            [ring.middleware.params :refer :all]
            [ring.util.codec :as codec]
            [logic.csv-logic :as csvl]
            [view.login :as plogin]
            [logic.users-logic :as ul]
            [clojure.walk :as walk]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth.backends.session :refer [session-backend]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [compojure.core :refer [defroutes context GET POST]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.params :refer [wrap-params]]))

(defn parse-url
  [url]
  (walk/keywordize-keys (codec/form-decode (str (slurp (:body url))))))

(defn post-login
  [{{username "username" password "password"} :form-params
                   session :session :as req}]
  (if-let [user (ul/get-user-by-username-and-password username password)]
    ; If authenticated
    (assoc (response/redirect "/admin") :session (assoc session :identity (:id user)))
    ; Otherwise
    (response/redirect "/login/")))

(ul/get-user-by-username-and-password "admin" "12345")


(defn post-logout
  [{session :session}]
  (assoc (response/redirect "/login")
    :session (dissoc session :identity)))

(defn authenticated? [{user :user :as req}]
  (not (nil? user)))

(defn wrap-user [handler]
  (fn [{user-id :identity :as req}]
    (handler (assoc req :user (ul/get-user user-id)))))

(defroutes admin-routes
           (GET "/" [] (plogin/get-login-page "successfully login") )
           (POST "/addmeal" request (logic.logic/add-new-meal (handler.handler/parse-url request))
                                    (response/redirect "/recipes"))
           (GET "/addmeal" [] (recipe/get-add-recipe-page ))
           )

(defroutes all-routes
           (context "/admin" []
             (restrict admin-routes {:handler authenticated?}))
           (GET "/" [] (sid/get-data-page))
           (GET "/food" [] (searcher/get-data-page csvl/food))
           (GET "/food/:group" [group] (searcher/get-data-page (logic/filter-by-group group)))
           (POST "/search/max" request (searcher/get-data-page (logic/filter-by-calorie-max-min > (read-string (:maxCal (handler.handler/parse-url request))))))
           (POST "/search/name" request (searcher/get-data-page (logic/filter-by-name (:name (handler.handler/parse-url request)))))
           (POST "/report" request
             (let [res (handler.handler/parse-url request)]
               (sid/print-report (logic.logic/get-report (read-string (:age res)) (str :gender res) (read-string (:height res)) (read-string (:weight res)) (read-string (:activity res))))))
           (GET "/recipes" [] (recipe/get-data-page (logic.logic/get-data-recipes)))
           (GET "/login" [] (plogin/get-login-page ""))
           (POST "/login" [] post-login)
           (POST "/logout" [] post-logout))


(def backend (session-backend))
(def my-app
  (-> #'all-routes
      (wrap-user)
      (wrap-authentication backend)
      (wrap-authorization backend)
      (wrap-session)
      (wrap-params)))

(my-app {:request-method :get :uri "/admin/"})
(my-app {:request-method :post :uri "/login" :body "username=admin&password=12345"})

(my-app {:request-method :get :uri "/admin/"
         :headers {"cookie" "ring-session=e78db96d-eb99-4c80-88dd-9cf9dcd91558"}})