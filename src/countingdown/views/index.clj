(ns view.index
  (:require [view.view-utility :as vutil]))

(defn get-index-page
  []
  "Index page"
  (vutil/common "Index page"
                (vutil/navbar)
                [:label {:class "control-label col-sm-2"} "Welcome"]))
