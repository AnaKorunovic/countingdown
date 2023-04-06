(defproject countingdown "0.1.0-SNAPSHOT"
  :description "clojure project for subject Alati i metode softverskog inzinjerstva"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.7.0"]
                 [ring/ring-jetty-adapter "1.9.6"]
                 [ring "1.9.6"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.clojure/data.csv "1.0.1"]
                 [csv-map "0.1.1"]
                 [clj-pdf "2.6.2"]
                 [buddy/buddy-auth "3.0.323"]
                 [buddy/buddy-hashers "1.4.0"]
                 [ring/ring-mock "0.4.0"]
                 [ring/ring-anti-forgery "1.3.0"]
                 [me.grison/cljwebauthn "0.1.2"]
                 [org.xerial/sqlite-jdbc "3.8.7"]
                 [mysql/mysql-connector-java "8.0.30"]]
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.2"]
                                  [midje "1.10.9"]]
                   :plugins [[lein-midje "3.2.1"]
                             [criterium "0.4.6"]
                             ]}}
  ;:repl-options {:init-ns countingdown.core}
  :resource-paths ["resources"]
  ;:main countingdown.main
  )
