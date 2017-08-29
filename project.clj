(defproject clojusc/twig "0.3.2-SNAPSHOT"
  :description "A little logging helper for Clojure projects"
  :url "https://github.com/clojusc/twig"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    [ch.qos.logback/logback-classic "1.2.3" :exclusions [
      org.slf4j/slf4j-log4j12
      org.slf4j/slf4j-api
      log4j/log4j]]
    [clansi "1.0.0"]
    [clojusc/cljs-tools "0.2.0-SNAPSHOT"]
    [com.taoensso/timbre "4.10.0"]
    [org.clojure/clojure "1.8.0"]
    [org.clojure/clojurescript "1.9.908"]
    [org.clojure/tools.logging "0.4.0"]
    [org.slf4j/slf4j-api "1.7.25"]]
  :plugins [
    [lein-cljsbuild "1.1.6" :exclusions [org.clojure/clojure]]
    [lein-npm "0.6.2" :exclusions [org.clojure/clojure]]]
  :npm {
    :dependencies [
      [colors "1.1.2"]]}
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
  :profiles {
    :dev {
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns clojusc.twig.dev}
      :dependencies [
           [org.clojure/tools.nrepl "0.2.13"]]}
    :test {
      :exclusions [org.clojure/clojure]
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]]
      :plugins [
        [jonase/eastwood "0.2.4"]
        [lein-ancient "0.6.10"]
        [lein-bikeshed "0.4.1"]
        [lein-kibit "0.1.5"]
        [lein-shell "0.5.0"]
        [venantius/yagni "0.1.4"]]}}
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
    "check-deps" [
      "with-profile" "+test" "ancient" "check" "all"]
    "kibit" [
      "with-profile" "+test" "do"
        ["shell" "echo" "== Kibit =="]
        ["kibit"]]
    "outlaw" [
      "with-profile" "+test"
      "eastwood" "{:namespaces [:source-paths] :source-paths [\"src\"]}"]
    "lint" [
      "with-profile" "+test" "do"
        ["check"] ["kibit"] ["outlaw"]]})
