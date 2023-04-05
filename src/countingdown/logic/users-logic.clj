(ns logic.users-logic
  (:require [buddy.hashers :as hashers]))

(defn uuid
  []
  (java.util.UUID/randomUUID))
(def user-storage (atom {}))

;user {:username "admin" :password "12345"}
(defn create-user
  [user]
  (let [password (:password user)
        user-id (uuid)]
    (swap! user-storage
           assoc :user-id (dissoc (assoc user :id user-id :password-hash (hashers/encrypt password)) :password))))

(defn get-user
  [username]
  (get @user-storage username))

(defn get-user-by-username-and-password [username password]
  (prn username password)
  (reduce (fn [_ user]
            (if (and (= (:username user) username)
                     (hashers/check password (:password-hash user)))
              (reduced user))) (vals @user-storage)))

(defn admin-exist?
  [{username :username password :password}]
  (prn username password)
  (and (= username "admin")
       (= password "12345")))
;(get-user-by-username-and-password "admin" "12345")
;(create-user {:username "admin" :password "12345"})

