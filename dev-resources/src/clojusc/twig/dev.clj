(ns clojusc.twig.dev
  (:require [clojure.pprint :refer [print-table]]
            [clojure.reflect :refer [reflect]]
            [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clojusc.twig :as logger :refer [demo]]))

(defn show-methods
  ""
  [obj]
  (print-table
    (sort-by :name
      (filter :exception-types (:members (reflect obj))))))
