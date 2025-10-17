(defproject clojusc/twig "0.4.2"
  :description "A little logging helper for Clojure/ClojureScript projects"
  :url "https://github.com/clojusc/twig"
  :license {
    :name "Eclipse Public License"
    :url "http://www.eclipse.org/legal/epl-v10.html"}
  :exclusions [
    [com.taoensso/encore]
    [org.clojure/clojure]]
  :dependencies [
    [ch.qos.logback/logback-classic "1.5.19"]
    [clansi "1.0.0"]
    [clojusc/cljs-tools "0.2.2"]
    [com.taoensso/encore "2.105.0"]
    [com.taoensso/timbre "4.10.0"]
    [org.clojure/clojure "1.11.2"]
    [org.clojure/clojurescript "1.11.132"]
    [org.clojure/tools.logging "0.4.1"]
    [org.slf4j/slf4j-api "2.0.13"]]
  :plugins [
    [lein-cljsbuild "1.1.7" :exclusions [org.clojure/clojure]]
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
    :ubercompile {
      :aot :all}
    :lint {
      :source-paths ^:replace ["src"]
      :test-paths ^:replace []
      :exclusions [
        [org.clojure/clojure]]
      :plugins [
        [jonase/eastwood "0.3.5"]
        [lein-ancient "0.6.15"]
        [lein-kibit "0.1.6"]]}
    :dev {
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns clojusc.twig.dev}
      :dependencies [
        [org.clojure/tools.nrepl "0.2.13"]]}
    :test {
      :aot :all
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]]
      :plugins [
        [lein-ltest "0.3.0"]]
      :source-paths ["test"]
      :test-selectors {
        :default :unit
        :unit :unit
        :system :system
        :integration :integration}}}
  :aliases {
    ;; CLJS aliases
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
    ;; CLJ Aliases
    "repl" ["do"
      ["clean"]
      ["repl"]]
    "ubercompile" ["with-profile" "+ubercompile" "compile"]
    ;; Linting and tests
    "check-vers" ["with-profile" "+lint" "ancient" "check" ":all"]
    "check-jars" ["with-profile" "+lint" "do"
      ["deps" ":tree"]
      ;["deps" ":plugin-tree"]
      ]
    "check-deps" ["do"
      ["check-jars"]
      ["check-vers"]]
    "kibit" ["with-profile" "+lint" "do"
      ["kibit"]]
    "eastwood" ["with-profile" "+lint" "eastwood" "{:namespaces [:source-paths]}"]
    "lint" ["do"
      ["kibit"]
      ;["eastwood"]
      ]
    "ltest" ["with-profile" "+test" "ltest"]
    ;; Build
    "cljs" ["cljsbuild" "once" "twig"]
    "cljs-node" ["cljsbuild" "once" "node"]
    "build" ^{:doc "Perform build steps."} ["do"
      ["clean"]
      ["ubercompile"]
      ["check-vers"]
      ["lint"]
      ["ltest"]
      ["uberjar"]
      ["cljs"]
      ["cljs-node"]]})
