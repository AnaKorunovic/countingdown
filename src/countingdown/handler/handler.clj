(ns handler.handler
  (:require [compojure.core :refer :all]
            [view.start-input-data :as sid]
            [view.searcher :as searcher]
            [view.recipes :as recipe]
            [view.index :as index]
            [db.users-db :as udb]
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

(defn register
  [string]
  (let [map {:name (clojure.string/replace (get (clojure.string/split string #"&") 0) "name=" "")
             :username (clojure.string/replace (get (clojure.string/split string #"&") 1) "username=" "")
             :password (clojure.string/replace (get (clojure.string/split string #"&") 2) "password=" "")}] map))


(defn prepare-admin [string]
  (let [map {:username  (clojure.string/replace (get (clojure.string/split string #"&") 0) "username=" "")
             :password (clojure.string/replace (get (clojure.string/split string #"&") 1) "password=" "")}] map))

(defn wrap-admin-only
  [handler]
  (fn [req]
    (if (-> req :session :admin)
      (handler req)
      (response/redirect "/login"))))

(defn wrap-user-only [handler]
  (fn [req]
    (if (-> req :session :role)
      (handler req)
      (response/redirect "/login"))))

(defroutes admin-routes
           (GET "/recipes" [] (recipe/get-data-page (logic.logic/get-data-recipes)))
           (POST "/addmeal" request (logic.logic/add-new-meal (handler.handler/parse-url request))
                                    (response/redirect "/recipes"))
           (GET "/addmeal" [] (recipe/get-add-recipe-page )))

(defroutes user-routes
           (GET "/data" [] (sid/get-data-page ))
           (POST "/report" request
             (let [res (handler.handler/parse-url request) age (read-string (:age res))
                   gender (str :gender res) height (read-string (:height res))
                   weight (read-string (:weight res)) activity (read-string (:activity res))]
               (if (= (logic/validate-input age gender height weight) "")
                 (sid/print-report (logic.logic/get-report age gender height weight activity))
                 (sid/get-data-page age gender height weight)))))

(defroutes all-routes
           (GET "/" [] (index/get-index-page))
           ;(GET "/index" [] (sid/get-data-page ))
           (GET "/food" [] (searcher/get-data-page csvl/food))
           (GET "/food/:group" [group] (searcher/get-data-page (logic/filter-by-group group)))
           (POST "/search/max" request (searcher/get-data-page (logic/filter-by-calorie-max-min > (read-string (:maxCal (handler.handler/parse-url request))))))
           (POST "/search/name" request (searcher/get-data-page (logic/filter-by-name (:name (handler.handler/parse-url request)))))
           #_(POST "/report" request
             (let [res (handler.handler/parse-url request) age (read-string (:age res))
                   gender (str :gender res) height (read-string (:height res))
                   weight (read-string (:weight res)) activity (read-string (:activity res))]
               (if (= (logic/validate-input age gender height weight) "")
                 (sid/print-report (logic.logic/get-report age gender height weight activity))
                 (sid/get-data-page age gender height weight))))
           (GET "/register" [] (plogin/get-register-page))
           (POST "/register/:id" req (do (let [user (register (slurp (:body req)))]
                                                (let [exist (udb/user-exists? user)]
                                                  (if (= (empty? exist) true)
                                                    (do
                                                      (udb/add-user user)
                                                      (response/redirect "/login"))
                                                    (response/redirect "/error/registration"))))))
           (GET "/error/registration" [] (plogin/get-register-page "Error while registration. Username already exists. Try again."))
           (GET "/login" [:as {session :session}]
             ; if admin is logged in then go to recepies page
             (if (or (= (:role session) "user") (:admin session))
               (response/redirect "/data")
               (plogin/get-login-page)))
           (POST "/login" req
             (let [user (prepare-admin (slurp (:body req)))]
               (if (ul/admin-exist? user)
                 (-> (response/redirect "/recipes")
                     (assoc-in [:session :admin] true)) ;in :session we add {:admin true}
                 (if (= (try (udb/user-exists? user) (catch Exception e (plogin/get-login-page "Error login. Invalid username or password"))) user)
                   (plogin/get-login-page "Error login. Invalid username or password")
                   (-> (response/redirect "/data")
                       (assoc-in [:session :role] "user")
                       (assoc-in [:session :id] (udb/get-user-id-by-username-and-password (:username user) (:password user))))
                   ))))
           #_(POST "/logout" []
             (-> (response/redirect "/")
                 (assoc-in [:session :admin] false)))
           (GET "/logout" []
             (-> (response/redirect "/")
                 (assoc :session {}))))

(def my-app
  (-> (routes (wrap-routes admin-routes wrap-admin-only)
              (wrap-routes user-routes wrap-user-only)
              all-routes)
      (wrap-multipart-params)
      session/wrap-session))

(comment (my-app {:request-method :get :uri "/recipes/"})
         (my-app {:request-method :post :uri "/admin/login" :body "username=admin&password=12345"})

         (my-app {:request-method :get :uri "/recipes/"
                  :headers {"cookie" "ring-session=e78db96d-eb99-4c80-88dd-9cf9dcd91558"}}))

