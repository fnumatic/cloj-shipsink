(ns cloj-shipsink.core
  (:gen-class)
  (:use [clojure.string :only (split)]))

(def field (ref {}))

(defn parse-input [s]
  (let [y (- (->> s (map int) first ) 96)
        x (Integer/parseInt (subs s 1) )]
 [x y]))

(defn fill-field []
  (for [x (range 1 10) y (range 1 10)]   [x y]))

(defn reset-field []
  (into {} (map #(vec [% "."])
         (fill-field))))

(defn gui-field [shots f]
  (merge f shots))

(defn alter-pos [p d]
  (update-in p
             [(if (= d :h) 0 1 )]
             inc))

(defn ship [p l d]
  (if (= l 0)
    nil
    (cons p (ship (alter-pos p d) (dec l) d)  )  ))

(def ships
  (mapcat identity [(ship [1 1] 2 :h) (ship [1 3] 2 :v)]))

(defn place-ships [s f]
  (reduce #(assoc %1 %2 "S") f s))

(defn format-matrix [game]
  (let [field (reset-field)
        shots (:shots game)
        field (gui-field shots field)]
  (clojure.string/join (for [y (range 1 10) x (range 1 10)] (str (get field [x y]) (if (= x 9) "\n" )))))
  )

(defn coordinates [s]
  (parse-input s))

(defn check-hit [coord ships]
  (if (some #{coord} ships ) "x" "~"))

(defn fire [coord game]
  (let [ships (:ships game)
        shots (:shots game)
        shots (assoc shots coord (check-hit coord ships) )]
    (assoc game :shots shots)))

(defn play [input g]
    (let [coord (coordinates input)
          game (fire coord g)
          display (format-matrix game)]
      (do
        (println display)
        game)))

(defn ask [s]
  (println s)
 (read-line) )

(def menu "q:uit! || coordinates?")

(defn init-game []
    {:ships ships})

(defn -main
  [& args]

    (loop [ game (init-game) input (ask menu)]
      (if-not (= input "q")  (recur  (play input game) (ask menu)) nil))
  )

