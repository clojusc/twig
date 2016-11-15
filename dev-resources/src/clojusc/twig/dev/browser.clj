(ns clojusc.twig.dev.browser
  (:require [cljs.pprint :refer [print-table]]
            [clojure.browser.repl :as repl]
            [clojure.string :as string]
            [clojusc.twig :as twig]
            [taoensso.timbre :as log]))

(repl/connect "http://localhost:9000/repl")

(enable-console-print!)

(println "Hello world!")
