(ns logic.pdf-logic
  (:require [clj-pdf.core :as pdf]
            ))

(pdf/pdf
  [{}
   [:list {:roman true}
    [:chunk {:style :bold} "a bold item"]
    "another item"
    "yet another item"]
   [:phrase "some text"]
   [:phrase "some more text"]
   [:paragraph "yet more text"]]
  "doc.pdf")

(defn pdf [report]
  (pdf/*pdf-writer*
    [{}
     [:phrase {:style :bold} "A report"]
     [:paragraph report]]
    "report.pdf"))




