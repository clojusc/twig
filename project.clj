(defproject clojusc/twig "0.2.4"
  :description "A little logging helper for Clojure projects"
  :url "https://github.com/clojusc/twig"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.7" :exclusions [
                   org.slf4j/slf4j-log4j12
                   log4j/log4j]]
                 [janino "2.5.15"]])
