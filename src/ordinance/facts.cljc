(ns ordinance.facts
  "Municipal-ordinance compliance catalog for Beijing (北京市) -- the FIRST
  CHN member of the cloud-itonami-municipality-* compliance-fact family
  (ADR-2607141700, cloud-itonami-compliance-fact-federation; see
  cloud-itonami-municipality-jpn-tokyo / -jpn-osaka / -jpn-kyoto for the
  Japanese siblings). Recorded as a coverage gap in superproject
  ADR-2607277000: before this repo the 59 municipality-* repos covered no
  Chinese city at all.

  Every entry cites an OFFICIAL beijing.gov.cn URL -- never fabricated. An
  ordinance not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url/number.

  Both entries below were verified on 2026-07-27 by fetching the
  beijing.gov.cn page and reading its rendered text:

  - 控制吸烟条例: read from the 地方性法规 section. Its header line states
    verbatim 「2014年11月28日北京市第十四届人民代表大会常务委员会第十五次
    会议通过　根据2021年9月24日北京市第十五届人民代表大会常务委员会第三十三
    次会议通过的《关于修改部分地方性法规的决定》修正」, which is where both
    the enacted and last-revised dates come from. 第九条 (indoor public
    places, workplaces and public transport) and 第十条 (four categories of
    outdoor area) were read directly.
  - 生活垃圾管理条例: the page read is the 2019 AMENDMENT DECISION, not the
    consolidated ordinance text, and the entry is titled accordingly. Its
    metadata block states 发文机构 北京市人民代表大会常务委员会, 发文字号
    公告〔十五届〕第21号, 成文日期 2019-11-27, 实施日期 2020-05-01,
    发布日期 2019-12-02. The `:ordinance/number` field records the
    announcement number of that decision -- NOT a number for the underlying
    ordinance, which this repo has not read.")

(def catalog
  "municipality-slug -> vector of ordinance entries."
  {"beijing"
   [{:ordinance/id "beijing.kongzhi-xiyan-tiaoli-2014"
     :ordinance/title "北京市控制吸烟条例 (Beijing Municipal Regulations on Smoking Control)"
     :ordinance/municipality "beijing"
     :ordinance/country "CHN"
     :ordinance/kind :ordinance
     :ordinance/number "2014年11月28日北京市第十四届人民代表大会常务委员会第十五次会议通过（2021年9月24日《关于修改部分地方性法规的决定》修正）"
     :ordinance/url "https://www.beijing.gov.cn/zhengce/dfxfg/202111/t20211103_2528426.html"
     :ordinance/url-provenance :official-beijing-gov-cn
     :ordinance/enacted-date "2014-11-28"
     :ordinance/last-revised-date "2021-09-24"
     :ordinance/retrieved-at "2026-07-27"
     :ordinance/topic #{:public-health :smoking-control}}
    {:ordinance/id "beijing.shenghuo-laji-guanli-tiaoli-xiugai-jueding-2019"
     :ordinance/title "北京市人民代表大会常务委员会关于修改《北京市生活垃圾管理条例》的决定 (Decision amending the Beijing Municipal Regulations on Domestic Waste Management)"
     :ordinance/municipality "beijing"
     :ordinance/country "CHN"
     :ordinance/kind :amendment-decision
     ;; The 实施日期 (2020-05-01) is carried inside :ordinance/number rather
     ;; than as its own attribute, because schema/ordinance.edn is
     ;; deliberately IDENTICAL across every municipality-* sibling -- that
     ;; uniformity is what lets the federated query join across them. The
     ;; Japanese siblings encode 施行日 the same way (see
     ;; cloud-itonami-municipality-jpn-kyoto's 京都市条例第32号 entry).
     :ordinance/number "北京市人民代表大会常务委员会公告〔十五届〕第21号（2020年5月1日施行）"
     :ordinance/url "https://www.beijing.gov.cn/zhengce/zhengcefagui/201912/t20191204_834225.html"
     :ordinance/url-provenance :official-beijing-gov-cn
     :ordinance/enacted-date "2019-11-27"
     :ordinance/retrieved-at "2026-07-27"
     :ordinance/topic #{:waste-management :environment}}]})

(defn spec-basis [muni] (get catalog muni))

(defn coverage
  ([] (coverage (keys catalog)))
  ([munis]
   (let [have (filter catalog munis)
         missing (remove catalog munis)]
     {:requested (count munis)
      :covered (count have)
      :covered-municipalities (vec (sort have))
      :missing-municipalities (vec (sort missing))
      :note (str "cloud-itonami-municipality-chn-beijing (family ADR-2607141700, "
                 "gap recorded in ADR-2607277000): "
                 (count (get catalog "beijing")) " Beijing entries seeded with "
                 "official beijing.gov.cn citations read 2026-07-27. The waste "
                 "entry is the AMENDMENT DECISION, not the consolidated "
                 "ordinance text -- its :ordinance/number is that decision's "
                 "announcement number. Extend `ordinance.facts/catalog`, never "
                 "fabricate an id/url/number.")})))

(defn by-topic [muni topic]
  (filterv #(contains? (:ordinance/topic %) topic) (spec-basis muni)))
