(defproject twig "0.1.0-SNAPSHOT"
  :description "A little logging helper for Clojure projects"
  :url "https://github.com/oubiwann/twig"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.3"]]
  ;; List the namespaces whose log levels we want to control; note that if we
  ;; add more dependencies that are chatty in the logs, we'll want to add them
  ;; here.

  ; XXX put these examples into the README under "Usage"

  ; :logging-namespaces [lcmap-rest
  ;                      lcmap-client
  ;                      com.datastax.driver
  ;                      co.paralleluniverse]

  ;; In you profiles, add an :env key and in :env, add a :log-level

  ; :profiles {
  ;   :dev {
  ;     :env
  ;       {:db {...}
  ;        :http {...}
  ;        ...
  ;        :log-level :debug
  ;        ...}}}
  )
