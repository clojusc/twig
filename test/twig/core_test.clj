(ns twig.core-test
  (:require [clojure.test :refer :all]
            [twig.core :as twig])
  (:import [ch.qos.logback.classic Level]))

(deftest convert-level-test
  (is (= (twig/convert-level :off) Level/OFF))
  (is (= (twig/convert-level :error) Level/ERROR))
  (is (= (twig/convert-level :warn) Level/WARN))
  (is (= (twig/convert-level :info) Level/INFO))
  (is (= (twig/convert-level :debug) Level/DEBUG))
  (is (= (twig/convert-level :trace) Level/TRACE))
  (is (= (twig/convert-level :all) Level/ALL)))
