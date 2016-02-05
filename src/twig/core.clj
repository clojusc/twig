(ns twig.core
  (:require [clojure.tools.logging :as log]
            [clojure.tools.logging.impl :as log-impl]
            [clojure.core.match :refer [match]])
  (:import [ch.qos.logback.classic Level]
           [ch.qos.logback.classic.joran JoranConfigurator]))

(defn get-factory []
  (log-impl/find-factory))

(defn get-factory-name []
  (log-impl/name (get-factory)))

(defn get-logger [namespace]
  (log-impl/get-logger (get-factory) namespace))

(defn get-logger-name [namespace]
  (.getName (get-logger namespace)))

(defn get-logger-level [namespace]
  (.getLevel (get-logger namespace)))

(defn get-logger-context [namespace]
  (.getLoggerContext (get-logger namespace)))

(defn get-config [namespace]
  (let [cfg (JoranConfigurator.)]
    (.setContext cfg (get-logger-context namespace))
    cfg))

(def ->level
  {:off Level/OFF
   ;:fatal Level/FATAL
   :error Level/ERROR
   :warn Level/WARN
   :info Level/INFO
   :debug Level/DEBUG
   :trace Level/TRACE
   :all Level/ALL})

(defmulti set-level! (fn [namesp level] (mapv class [namesp level])))

(defmethod set-level! [clojure.lang.Symbol clojure.lang.Keyword]
                      [namesp level]
  (.setLevel (get-logger namesp) (convert-level level)))

(defmethod set-level! [clojure.lang.PersistentVector clojure.lang.Keyword]
                      [namesps level]
  (dorun (map #(set-level! % level) namesps)))

;; Aliases

(def convert-level #'->level)
