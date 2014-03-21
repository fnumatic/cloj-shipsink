(defproject cloj-shipsink "0.1.0-SNAPSHOT"
  :description "simple battleship game"
  :url "http://github.com/fnumatic/cloj-shipsink"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot cloj-shipsink.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
