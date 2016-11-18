(ns clojusc.twig-test
  (:require [clojure.test :refer :all]
            [clojusc.twig :as twig])
  (:import [ch.qos.logback.classic Level]))

(deftest keywordlevel->java-test
  (is (= (twig/level->java :off) Level/OFF))
  (is (= (twig/level->java :error) Level/ERROR))
  (is (= (twig/level->java :warn) Level/WARN))
  (is (= (twig/level->java :info) Level/INFO))
  (is (= (twig/level->java :debug) Level/DEBUG))
  (is (= (twig/level->java :trace) Level/TRACE))
  (is (= (twig/level->java :all) Level/ALL)))

(deftest symbollevel->java-test
  (is (= (twig/level->java 'off) Level/OFF))
  (is (= (twig/level->java 'error) Level/ERROR))
  (is (= (twig/level->java 'warn) Level/WARN))
  (is (= (twig/level->java 'info) Level/INFO))
  (is (= (twig/level->java 'debug) Level/DEBUG))
  (is (= (twig/level->java 'trace) Level/TRACE))
  (is (= (twig/level->java 'all) Level/ALL)))

(deftest stringlevel->java-test
  (is (= (twig/level->java "off") Level/OFF))
  (is (= (twig/level->java "error") Level/ERROR))
  (is (= (twig/level->java "warn") Level/WARN))
  (is (= (twig/level->java "info") Level/INFO))
  (is (= (twig/level->java "debug") Level/DEBUG))
  (is (= (twig/level->java "trace") Level/TRACE))
  (is (= (twig/level->java "all") Level/ALL)))

(deftest keyword->level-test
  (is (= (twig/->level :off) "OFF"))
  (is (= (twig/->level :error) "ERROR"))
  (is (= (twig/->level :warn) "WARN"))
  (is (= (twig/->level :info) "INFO"))
  (is (= (twig/->level :debug) "DEBUG"))
  (is (= (twig/->level :trace) "TRACE"))
  (is (= (twig/->level :all) "ALL")))


;; This next test is kept to test backwards compatibility with the old API
(deftest convert-level-test
  (is (= (twig/convert-level :off) Level/OFF))
  (is (= (twig/convert-level :error) Level/ERROR))
  (is (= (twig/convert-level :warn) Level/WARN))
  (is (= (twig/convert-level :info) Level/INFO))
  (is (= (twig/convert-level :debug) Level/DEBUG))
  (is (= (twig/convert-level :trace) Level/TRACE))
  (is (= (twig/convert-level :all) Level/ALL)))
