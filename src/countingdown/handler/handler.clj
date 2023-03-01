(ns handler.handler
  (:require [compojure.core :refer :all]
            [view.start-input-data :as sid]
            [view.searcher :as searcher]
            [view.recipes :as recepie]
            [ring.util.response :as response]
            [logic.logic :as logic]
            [compojure.route :as route]
            [ring.middleware.params :refer :all]
            [ring.util.codec :as codec]
            [clojure.walk :as walk])
         )

  (defroutes handler
             (GET "/" [] (sid/get-data-page ))
             (GET "/food" [] (searcher/get-data-page (logic.logic/get-all-data)))
             (GET "/food/:group" [group] (searcher/get-data-page (logic/filter-by-group  group)))
             (POST "/search" request   (searcher/get-data-page (logic/filter-by-calorie-max (read-string(:maxCal(handler.handler/parse-url request))))  ))
             (POST "/addmeal" request (str (handler.handler/parse-url request)))
             (GET "/addmeal" [] (recepie/get-data-add-page ))
             (GET "/recipes" [] (recepie/get-data-page))
             (POST "/report" request
               (let [res (handler.handler/parse-url request)]
                 (sid/print-report (logic.logic/get-report (read-string (:age res)) (str :gender res) (read-string (:height res)) (read-string (:weight res)) (read-string (:activity res))) ))

               )
               )
(defn parse-url [url]
  (walk/keywordize-keys (codec/form-decode (str (slurp (:body url))))))

