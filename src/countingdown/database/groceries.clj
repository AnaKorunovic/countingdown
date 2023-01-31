(ns countingdown.core
  (:require
            [clojure.java.jdbc :as jdbc]))

;***********************************************************
;corma--
;***************************************************8

(defrecord Grocery [id name calPer100g])

(def groceries [
                {:id 1 :name "ovsene pahuljice" :calPer100g 337}
                {:id 2 :name "egg" :calPer100g 151}
                {:id 3 :name "rice" :calPer100g 350}
                {:id 4 :name "chicken file" :calPer100g 105}
                {:id 5 :name "milk" :calPer100g 55}
                {:id 6 :name "maple syrup" :calPer100g 260}
                ])
#_(def groceries [
                (Grocery. 1 "ovsene pahuljice" 337)
                (Grocery. 2 "egg"  151)
                (Grocery. 3 "rice" 350)
                (Grocery. 4 "chicken file" 105)
                (Grocery. 5 "milk" 55)])

#_(defn add-grocery [id name calPer100g]
  (cons groceries (->Grocery id name calPer100g))
 )
(defn order-groceries-by-id [x y]
 (let [c (compare (:id x) (:id y))]
   (if (not= c 0)
     c))
  )

(defn add-grocery [id name calPer100g]
  (sort order-groceries-by-id  (cons {:id id :name name :calPer100g calPer100g} groceries ))
  )

(defn find-by-name [name]
  (filter #(= name (:name %)) groceries))

(defn name-start-with-x [x]
  (filter #(str/starts-with? (:name %) x) groceries ))


( println groceries)
(add-grocery  6 "apple" 52)
(sort order-groceries-by-id groceries)
(find-by-name "milk")
(name-start-with-x "m")

#_*************************************************************************
(def db {:dbtype "mysql"
         :dbname "countingdown"
               :user "root"
               :password ""})
(defn get-groceries []
  (jdbc/query db
              ["select * from groceries"]
              ))

(defn insert-grocery [name,calper100g]
  (jdbc/insert! db :groceries {:name name :calper100g calper100g}))

(defn delete-grocery [id]
  (jdbc/delete! db :groceries ["id=?" id]))

(defn update-grocery [id name calper100g]
  (jdbc/update! db :groceries {:name name :calper100g calper100g} ["id=?" id]))



