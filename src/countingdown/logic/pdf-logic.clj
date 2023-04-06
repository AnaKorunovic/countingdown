(ns logic.pdf-logic
  (:require [clj-pdf.core :as pdf]))

(defn pdf
  [report]
  (pdf/*pdf-writer*
    [{}
     [:phrase {:style :bold} "A report"]
     [:paragraph report]]
    "report.pdf"))




