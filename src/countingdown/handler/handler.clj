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
            [ring.middleware.session :as session]
            [buddy.auth.accessrules :refer [restrict]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]))

(defn parse-url
  [url]
  (walk/keywordize-keys (codec/form-decode (str (slurp (:body url))))))

(defn prepare-admin [string]
  (let [map {:username  (clojure.string/replace (get (clojure.string/split string #"&") 0) "username=" "")
             :password (clojure.string/replace (get (clojure.string/split string #"&") 1) "password=" "")}] map))

(defn wrap-admin-only
  [handler]
  (fn [req]
    (if (-> req :session :admin)
      (handler req)
      (response/redirect "/admin/login"))))

(defroutes admin-routes
           (GET "/recipes" [] (recipe/get-data-page (logic.logic/get-data-recipes)))
           (POST "/addmeal" request (logic.logic/add-new-meal (handler.handler/parse-url request))
                                    (response/redirect "/recipes"))
           (GET "/addmeal" [] (recipe/get-add-recipe-page )))

(defroutes all-routes
           #_(context "/admin" []
               (restrict admin-routes {:handler authenticated?}))
           (GET "/" [] (sid/get-data-page ))
           (GET "/food" [] (searcher/get-data-page csvl/food))
           (GET "/food/:group" [group] (searcher/get-data-page (logic/filter-by-group group)))
           (POST "/search/max" request (searcher/get-data-page (logic/filter-by-calorie-max-min > (read-string (:maxCal (handler.handler/parse-url request))))))
           (POST "/search/name" request (searcher/get-data-page (logic/filter-by-name (:name (handler.handler/parse-url request)))))
           (POST "/report" request
             (let [res (handler.handler/parse-url request) age (read-string (:age res))
                   gender (str :gender res) height (read-string (:height res))
                   weight (read-string (:weight res)) activity (read-string (:activity res))]
               (if (= (logic/validate-input age gender height weight) "")
                 (sid/print-report (logic.logic/get-report age gender height weight activity))
                 (sid/get-data-page age gender height weight))))
           (GET "/admin/login" [:as {session :session}]
             ; if admin is logged in then go to recepies page
             (if (:admin session)
               (response/redirect "/recipes")
               (plogin/get-login-page)))
           (POST "/admin/login" req
             (let [admin (prepare-admin (slurp (:body req)))]
               (if (ul/admin-exist? admin)
                 (-> (response/redirect "/recipes")
                     (assoc-in [:session :admin] true)) ;in :session we add {:admin true}
                 (plogin/get-login-page "Error login. Invalid username or password"))))
           (POST "/admin/logout" []
             (-> (response/redirect "/")
                 (assoc-in [:session :admin] false))))

(def my-app
  (-> (routes (wrap-routes admin-routes wrap-admin-only)
              all-routes)
      (wrap-multipart-params)
      session/wrap-session))

(comment (my-app {:request-method :get :uri "/recipes/"})
         (my-app {:request-method :post :uri "/admin/login" :body "username=admin&password=12345"})

         (my-app {:request-method :get :uri "/recipes/"
                  :headers {"cookie" "ring-session=e78db96d-eb99-4c80-88dd-9cf9dcd91558"}}))

