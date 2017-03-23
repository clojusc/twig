(ns clojusc.twig.dev
  (:require [clojure.pprint :refer [print-table]]
            [clojure.reflect :refer [reflect]]
            [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clojusc.twig :as twig :refer [demo]]
            [taoensso.timbre :as timbre]))

(twig/set-level! '[clojusc] :trace)

(defn no-color []
  (twig/set-level! '[clojusc] :trace twig/no-color-log-formatter))

(defn show-methods
  ""
  [obj]
  (print-table
    (sort-by :name
      (filter :exception-types (:members (reflect obj))))))
