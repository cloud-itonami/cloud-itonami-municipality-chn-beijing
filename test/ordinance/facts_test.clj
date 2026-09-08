(ns ordinance.facts-test
  (:require [clojure.edn :as edn]
            [kotoba.lang.text :as str]
            [clojure.test :refer [deftest is testing]]
            [ordinance.facts :as facts]))

(deftest beijing-has-spec-basis
  (let [sb (facts/spec-basis "beijing")]
    (is (= 2 (count sb)))
    (is (every? #(str/starts-with? (:ordinance/url %) "https://www.beijing.gov.cn/") sb)
        "every citation is an official beijing.gov.cn URL")
    (is (every? #(= :official-beijing-gov-cn (:ordinance/url-provenance %)) sb))
    (is (every? #(= "CHN" (:ordinance/country %)) sb))
    (is (every? #(= "2026-07-27" (:ordinance/retrieved-at %)) sb))))

(deftest unknown-municipality-has-no-spec-basis
  (is (nil? (facts/spec-basis "shanghai")))
  (is (nil? (facts/spec-basis "zzz"))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["beijing" "shanghai"])]
    (is (= 2 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["shanghai"] (:missing-municipalities c)))))

(deftest the-waste-entry-is-labelled-as-an-amendment-decision-not-the-ordinance
  (testing "the page actually read is the 修改决定, so the entry must not pose as the consolidated ordinance"
    (let [waste (first (facts/by-topic "beijing" :waste-management))]
      (is (= :amendment-decision (:ordinance/kind waste)))
      (is (str/includes? (:ordinance/title waste) "关于修改"))
      (is (str/starts-with? (:ordinance/number waste) "北京市人民代表大会常务委员会公告"))
      (is (str/includes? (:ordinance/number waste) "2020年5月1日施行")
          "the decision's own 实施日期, carried inside :ordinance/number as the siblings do")))
  (testing "and the coverage note says so, so removing the caveat breaks the build"
    (is (str/includes? (:note (facts/coverage)) "AMENDMENT DECISION"))))

(deftest the-smoking-entry-records-both-the-enactment-and-the-2021-revision
  (let [smoking (first (facts/by-topic "beijing" :smoking-control))]
    (is (= "2014-11-28" (:ordinance/enacted-date smoking)))
    (is (= "2021-09-24" (:ordinance/last-revised-date smoking))
        "from the 修正 clause in the page's own header line")))

(deftest by-topic-filters
  (is (= ["beijing.kongzhi-xiyan-tiaoli-2014"]
         (mapv :ordinance/id (facts/by-topic "beijing" :public-health))))
  (is (empty? (facts/by-topic "beijing" :housing)))
  (is (empty? (facts/by-topic "shanghai" :waste-management))))

(deftest tx-file-matches-catalog
  (let [tx (edn/read-string (slurp "data/datascript-tx.edn"))
        flat (mapcat val (sort-by key facts/catalog))]
    (is (= (vec flat) (vec tx)))))

(deftest every-attribute-used-is-declared-in-the-schema
  (testing "schema/ordinance.edn is deliberately IDENTICAL across every municipality-* sibling -- that uniformity is what lets the federated query join across them, so a new attribute here would be a silent divergence"
    (let [declared (set (keys (edn/read-string (slurp "schema/ordinance.edn"))))
          used (set (mapcat keys (mapcat val facts/catalog)))]
      (is (empty? (remove declared used))
          (str "undeclared: " (vec (remove declared used)))))))
