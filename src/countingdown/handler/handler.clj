(ns handler.handler
  (:require [compojure.core :refer :all]
            [view.start-input-data :as sid]
            [view.view-utility :as vutil])
         )

  (defroutes handler
             (GET "/" [] (sid/get-data-page ))
             (GET "/new" [age gender height weight activity] (handler.handler/print-pdf))
             )

(defn print-pdf []
  (vutil/common "First page"
                (vutil/navbar)

                (vutil/footer)
                )))