(ns db.users-db
  (:require [clojure.java.jdbc :as sql]))

(def sqlite-db {
             :classname   "org.sqlite.JDBC"
             :subprotocol "sqlite"
             :subname     "resources/users.db"})

(defn get-next-user-id
  []
  (+ 1 (:m (nth (sql/query sqlite-db ["SELECT MAX(id) as m FROM users"]) 0))))

(defn add-user
  [user]
  (sql/execute! sqlite-db ["INSERT INTO users (id, name, username, password) VALUES (?,?, ?, ?) " (db.users-db/get-next-user-id) (:name user) (:username user) (:password user)]))

(defn user-exists?
  [user]
  (sql/query sqlite-db ["SELECT * FROM users WHERE username=? AND password=?" (:username user) (:password user)]))


(defn get-user-id-by-username-and-password
  [username password]
  (:id (nth (sql/query sqlite-db ["SELECT id FROM users WHERE username=? and password=?" username password]) 0)))

(defn get-name-by-id-session [id]
  (:name (nth (sql/query sqlite-db ["SELECT name FROM users where id=?" id]) 0)))

(defn get-username-by-id-session [id]
  (:username (nth (sql/query sqlite-db ["SELECT username FROM users where id=?" id]) 0)))

;(get-next-user-id)
;(add-user {:name "Ana" :username "test1" :password "test1"})
;(user-exists? {:username  "anak" :password "anak99"})
;(user-exists? {:username  "anak" :password "anak999"})      ;return ()
;(get-user-id-by-username-and-password "anak" "anak99")