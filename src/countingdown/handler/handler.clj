(ns handler.handler
  (:require [compojure.core :refer :all]
            [view.start-input-data :as sid]
            [view.view-utility :as vutil]
            [view.searcher :as searcher]
            [logic.logic :as logic])
         )

  (defroutes handler
             (GET "/" [] (sid/get-data-page ))
             (GET "/food" [] (searcher/get-data-page (logic.logic/get-data)))
             (GET "/food/:group" [group] (searcher/get-data-page (logic/filter-by-group (logic/get-all-data) group)))
             (GET "/new" [age gender height weight activity] (handler.handler/print-pdf))
             )

(defn print-pdf []
  (vutil/common "First page"
                (vutil/navbar)


                )))