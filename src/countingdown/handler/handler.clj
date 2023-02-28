(ns handler.handler
  (:require [compojure.core :refer :all]
            [view.start-input-data :as sid]
            [view.view-utility :as vutil]
            [view.searcher :as searcher]
            [ring.util.response :as response]
            [logic.logic :as logic])
         )

  (defroutes handler
             (GET "/" [] (sid/get-data-page ))
             (GET "/food" [] (searcher/get-data-page (logic.logic/get-all-data)))
             (GET "/food/:group" [group] (searcher/get-data-page (logic/filter-by-group  group)))
             (POST "/report" [age gender height weight activity]


               (response/redirect (str "/producers/" gender "/ss" ))))
;(GET "/report" [age gender height weight activity] (sid/print-report (logic.logic/get-report age gender height weight activity)))

