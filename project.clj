(defproject countingdown "0.1.0-SNAPSHOT"
  :description "clojure project for subject Alati i metode softverskog inzinjerstva"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.7.0"]
                 [ring/ring-jetty-adapter "1.9.6"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.clojure/data.csv "1.0.1"]
                 [csv-map "0.1.1"]
                 [mysql/mysql-connector-java "8.0.30"]]
  :repl-options {:init-ns countingdown.core}
  :main countingdown.main)
