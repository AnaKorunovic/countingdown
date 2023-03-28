(ns logic.users-logic
  (:require [buddy.hashers :as hashers]))

(defn uuid
  []
  (java.util.UUID/randomUUID))
(def user-storage (atom {}))

;user {:username "admin" :password "admin"}
(defn create-user
  [user]
  (let [password (:password user)
        user-id (uuid)]
    (swap! user-storage
           assoc :user-id (dissoc (assoc user :id user-id :password-hash (hashers/encrypt password)) :password))))

(defn get-user
  [user-id]
  (get @user-storage user-id))

(defn get-user-by-username-and-password [username password]
  (prn username password)
  (reduce (fn [_ user]
            (if (and (= (:username user) username)
                     (hashers/check password (:password-hash user)))
              (reduced user))) (vals @user-storage)))