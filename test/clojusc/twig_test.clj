(ns clojusc.twig-test
  (:require [clojure.test :refer :all]
            [clojusc.twig :as twig])
  (:import [ch.qos.logback.classic Level]))

(deftest keyword->level-test
  (is (= (twig/->level :off) Level/OFF))
  (is (= (twig/->level :error) Level/ERROR))
  (is (= (twig/->level :warn) Level/WARN))
  (is (= (twig/->level :info) Level/INFO))
  (is (= (twig/->level :debug) Level/DEBUG))
  (is (= (twig/->level :trace) Level/TRACE))
  (is (= (twig/->level :all) Level/ALL)))

(deftest symbol->level-test
  (is (= (twig/->level 'off) Level/OFF))
  (is (= (twig/->level 'error) Level/ERROR))
  (is (= (twig/->level 'warn) Level/WARN))
  (is (= (twig/->level 'info) Level/INFO))
  (is (= (twig/->level 'debug) Level/DEBUG))
  (is (= (twig/->level 'trace) Level/TRACE))
  (is (= (twig/->level 'all) Level/ALL)))

(deftest string->level-test
  (is (= (twig/->level "off") Level/OFF))
  (is (= (twig/->level "error") Level/ERROR))
  (is (= (twig/->level "warn") Level/WARN))
  (is (= (twig/->level "info") Level/INFO))
  (is (= (twig/->level "debug") Level/DEBUG))
  (is (= (twig/->level "trace") Level/TRACE))
  (is (= (twig/->level "all") Level/ALL)))

;; This next test is kept to test backwards compatibility with the old API
(deftest convert-level-test
  (is (= (twig/convert-level :off) Level/OFF))
  (is (= (twig/convert-level :error) Level/ERROR))
  (is (= (twig/convert-level :warn) Level/WARN))
  (is (= (twig/convert-level :info) Level/INFO))
  (is (= (twig/convert-level :debug) Level/DEBUG))
  (is (= (twig/convert-level :trace) Level/TRACE))
  (is (= (twig/convert-level :all) Level/ALL)))
