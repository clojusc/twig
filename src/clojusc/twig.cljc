(ns clojusc.twig
  (:require [clojure.string :as string]
            [clojusc.cljs-tools :as tools]
            #?@(:clj [
              [clansi :as ansi]
              [clojure.pprint :as pp]
              [clojure.tools.logging :as log]
              [clojure.tools.logging.impl :as log-impl]
              [taoensso.timbre :as timbre :refer [log!]]])
            #?@(:cljs [
              [cljs.nodejs :as nodejs]
              [cljs.pprint :as pp]
              [taoensso.timbre :as timbre :refer-macros [log!]]]))
  #?(:clj
    (:import [ch.qos.logback.classic Level]
             [ch.qos.logback.classic.joran JoranConfigurator]
             [clojure.lang Keyword PersistentVector Symbol]))
  #?(:cljs
    (:require-macros [clojusc.twig :as twig-macros :refer ()])))

#?(:cljs
  (defonce color (nodejs/require "colors")))

(defn ->level [level]
  (string/upper-case (name level)))

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

    (defn java->level [level]
      (Level/toLevel (name level)))

    (def convert-level #'java->level)

    (defn highlight-level [level]
      (let [level-upper (->level level)]
        (case level
          :trace (ansi/style level-upper :magenta)
          :debug (ansi/style level-upper :blue)
          :info (ansi/style level-upper :green)
          :warn (ansi/style level-upper :yellow :bright)
          :error (ansi/style level-upper :red)
          :fatal (ansi/style level-upper :red :bright))))

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
           (ansi/style (tools/now-iso) :green)
           " "
           (ansi/style "[" :green)
           (ansi/style (.getName (Thread/currentThread)) :cyan)
           (ansi/style "]" :green)
           " "
           (highlight-level level)
           " "
           (ansi/style (str (or ?ns-str "?") ":" (or ?line "?")) :yellow)
           " - "
           (ansi/style
             (str (force msg_)
                  (when-not no-stacktrace?
                    (when-let [err ?err]
                      (str "\n" (timbre/stacktrace err opts))))) :green)))))))

#?(:cljs
  (do
    (defn highlight-level [level]
      (let [level-upper (->level level)]
        (case level
          :trace (.magenta color level-upper)
          :debug (.blue color level-upper)
          :info (.green color level-upper)
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
           (highlight-level level)
           " "
           (.yellow color (str (or ?ns-str "?") ":" (or ?line "?")))
           " - "
           (.green color
             (str (force msg_)
                  (when-not no-stacktrace?
                    (when-let [err ?err]
                      (str "\n" (timbre/stacktrace err opts))))))))))))

(defn ns->strs
  ""
  [namesp]
  (let [namesp-str (str namesp)]
    [namesp-str (str namesp-str ".*")]))

(defn nss->strs
  ""
  [namesps]
  (flatten (map ns->strs namesps)))

;; set-level!

(defmulti set-level! (fn [namesp level] (mapv type [namesp level])))

(defmethod set-level! [Symbol Keyword]
                      [namesp level]
  #?(:clj
    (do
      (.setLevel (get-logger namesp) (java->level level))
      (timbre/merge-config!
        {:level level
         :ns-whitelist (ns->strs namesp)
         :output-fn log-formatter})))
  #?(:cljs
    (timbre/merge-config!
      {:level level
       :ns-whitelist (ns->strs namesp)
       :output-fn log-formatter})))

(defmethod set-level! [PersistentVector Keyword]
                      [namesps level]
  #?(:clj
      (do
        (doseq [ns namesps]
          (set-level! ns level))
        (timbre/merge-config!
          {:level level
           :ns-whitelist (nss->strs namesps)
           :output-fn log-formatter})))
  #?(:cljs
      (timbre/merge-config!
        {:level level
         :ns-whitelist (nss->strs namesps)
         :output-fn log-formatter})))

;; utilities

(defn pprint
  [& args]
  (str "\n"
       (with-out-str
         (apply pp/pprint args))))

(defn demo []
  (let [msg "Hej! This is a message ..."]
    (timbre/trace msg)
    (timbre/debug msg)
    (timbre/info msg)
    (timbre/warn msg)
    (timbre/error msg)
    (timbre/fatal msg)))
