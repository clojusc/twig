(defproject clojusc/twig "0.3.2-SNAPSHOT"
  :description "A little logging helper for Clojure projects"
  :url "https://github.com/clojusc/twig"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[ch.qos.logback/logback-classic "1.2.1" :exclusions [
                   org.slf4j/slf4j-log4j12
                   org.slf4j/slf4j-api
                   log4j/log4j]]
                 [clansi "1.0.0"]
                 [clojusc/cljs-tools "0.1.2"]
                 [com.taoensso/timbre "4.8.0"]
                 [janino "2.5.15"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.495"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.slf4j/slf4j-api "1.7.24"]]
  :plugins
    [[lein-cljsbuild "1.1.5"]
     [lein-npm "0.6.2"]]
  :npm
    {:dependencies
      [[colors "1.1.2"]]}
  :cljsbuild {
    :builds [
      {:id "twig"
       :compiler
         {:main "clojusc.twig"
          :output-to "resources/public/js/twig.js"
          :output-dir "resources/public/js"}}
      {:id "node"
       :compiler
         {:target :nodejs
          :output-to "target/node/twig.js"
          :output-dir "target/node"}}]}
  :aliases {
    "rhino-repl"
      ^{:doc "Start a Rhino-based Clojurescript REPL"}
      ["trampoline" "run" "-m" "clojure.main"
       "dev-resources/scripts/rhino-repl.clj"]
    "node-repl"
      ^{:doc "Start a Node.js-based Clojurescript REPL"}
      ["trampoline" "run" "-m" "clojure.main"
       "dev-resources/scripts/node-repl.clj"]
    "browser-repl"
      ^{:doc "Start a browser-based Clojurescript REPL"}
      ["trampoline" "run" "-m" "clojure.main"
       "dev-resources/scripts/browser-repl.clj"]
     }
  :profiles {
    :dev {
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns clojusc.twig.dev
        :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
      :dependencies
          [[com.cemerick/piggieback "0.2.1"]
           [org.clojure/tools.nrepl "0.2.12"]]}})
