(ns twig.core
  (:require [clojure.tools.logging.impl :as log-impl])
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

(defn ->level [level]
  (Level/toLevel (name level)))

;; set-level!

(defmulti set-level! (fn [namesp level] (mapv class [namesp level])))

(defmethod set-level! [clojure.lang.Symbol clojure.lang.Keyword]
                      [namesp level]
  (.setLevel (get-logger namesp) (->level level)))

(defmethod set-level! [clojure.lang.PersistentVector clojure.lang.Keyword]
                      [namesps level]
  (doseq [ns namesps]
    (set-level! ns level)))

;; Aliases

(def convert-level #'->level)
