(ns clojusc.twig
  (:require [clojure.string :as string]
            [clojusc.cljs-tools :as tools]
            #?@(:clj [
              [clojure.pprint :as pp]
              [clojure.tools.logging :as log]
              [clojure.tools.logging.impl :as log-impl]])
            #?@(:cljs [
              [cljs.nodejs :as nodejs]
              [cljs.pprint :as pp]
              [taoensso.timbre :as log]]))
  #?(:clj
    (:import [ch.qos.logback.classic Level]
             [ch.qos.logback.classic.joran JoranConfigurator]
             [clojure.lang Keyword PersistentVector Symbol])))

#?(:cljs
  (defonce color (nodejs/require "colors")))

#?(:clj
  (do
    (defn get-factory []
      log/*logger-factory*)

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
      (Level/toLevel (name level)))))

#?(:cljs
  (do
    (defn ->level [level]
      (string/upper-case (name level)))

    (defn highlight-level [level]
      (let [level-upper (->level level)]
        (case level
          :trace (.green color level-upper)
          :debug (.bold color (.green color level-upper))
          :info (.blue color level-upper)
          :warn (.bold color (.yellow color level-upper))
          :error (.red color level-upper)
          :fatal (.bold color (.red color level-upper)))))

    (defn log-formatter
      "Custom log output function.
      Use`(partial log-formatter <opts-map>)` to modify default opts."
      ([data]
        (log-formatter nil data))
      ([opts data] ; For partials
       (let [{:keys [no-stacktrace? stacktrace-fonts]} opts
             {:keys [level ?err #_vargs msg_ ?ns-str hostname_
                     timestamp_ ?line]} data]
         (str
           (.green color (tools/now-iso))
           " "
           (.green color "[pid:")
           (.cyan color (str (aget js/process "pid")))
           (.green color "]")
           " "
           (.bold color (.green color "["))
           (highlight-level level)
           (.bold color (.green color "]"))
           " "
           (.yellow color (str (or ?ns-str "?") ":" (or ?line "?")))
           " "
           (.green color
             (str (force msg_)
                  (when-not no-stacktrace?
                    (when-let [err ?err]
                      (str "\n" (log/stacktrace err opts))))))))))))

;; set-level!

(defmulti set-level! (fn [namesp level] (mapv type [namesp level])))

(defmethod set-level! [Symbol Keyword]
                      [namesp level]
  #?(:clj (.setLevel (get-logger namesp) (->level level)))
  #?(:cljs
    (log/merge-config!
      {:level level
       :ns-whitelist [namesp]})))

(defmethod set-level! [PersistentVector Keyword]
                      [namesps level]
  #?(:clj
      (doseq [ns namesps]
        (set-level! ns level)))
  #?(:cljs
      (log/merge-config!
        {:level level
         :ns-whitelist namesps
         :output-fn log-formatter})))

;; utilities

(defn pprint
  [& args]
  (str "\n"
       (with-out-str
         (apply pp/pprint args))))

(defn demo []
  (let [msg "Hej! This is a message ..."]
    (log/trace msg)
    (log/debug msg)
    (log/info msg)
    (log/warn msg)
    (log/error msg)))

;; Aliases

(def convert-level #'->level)
