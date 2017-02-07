(ns clojure-filtering.core
  (:gen-class)
  (:require [clojure.string :as str])
  (:require [clojure.data.json :as json]))

(defn -main []
  (println "Choose a Category")
  (println "Alcohol")
  (println "Furniture")
  (println "Toiletries")
  (println "Jewelry")
  (println "Shoes")
  (println "Food")

  (let [purchases (slurp "purchases.csv")
        purchases (str/split-lines purchases)
        purchases (map (fn [line]
                         (str/split line #","))
                       purchases)
        header (first purchases)
        purchases (rest purchases)
        purchases (map (fn[line]
                         (zipmap header line))
                       purchases)
        category (read-line)
        purchases (filter (fn [line]
                            (= (get line "category") category))
                          purchases)
        file-json (json/write-str purchases)]
    (spit (str "filtered_purchases_" category ".json") file-json)category)
  )