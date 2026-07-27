(ns culture.facts
  "Regional-culture catalog for Beijing (北京市) -- local dishes, heritage
  sites and performing arts, piggybacked onto this municipality compliance
  repo per ADR-2607171400 (cloud-itonami-municipality-culture-catalog, in
  com-junkawasaki/root), sibling namespace to `ordinance.facts`
  (ADR-2607141700).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.

  TWO ABSENCES ARE DELIBERATE, and are absences rather than guesses:

  - No :festival entry. The generic 庙会 (temple fair) source that was
    fetched does not mention Beijing by name, and this catalog is
    municipality-scoped, so nothing was recorded. Beijing plainly has
    famous temple fairs; none has been verified here yet.
  - No :craft entry. 景泰蓝 (jingtailan / Chinese cloisonné) was
    investigated and dropped: the cloisonné source that was fetched
    attributes the name to the Jingtai Emperor but says nothing about
    Beijing specifically, so recording it here would have asserted a
    municipal connection the source does not support.

  Kyoto's sibling catalog has both kinds; Beijing's does not yet, and
  `by-kind` correctly returns empty for them rather than a plausible
  filler.")

(def catalog
  "municipality-slug -> vector of culture entries."
  {"beijing"
   [{:culture/id "beijing.dish.peking-duck"
     :culture/name "Peking duck"
     :culture/name-local "北京烤鸭"
     :culture/municipality "beijing"
     :culture/country "CHN"
     :culture/kind :dish
     :culture/summary "A dish from Beijing prepared since the Imperial era, characterised by thin, crispy skin; traditionally carved in front of diners and served in three stages, the skin first with sweet bean sauce, then meat wrapped in pancakes with cucumber and spring onion."
     :culture/url "https://en.wikipedia.org/wiki/Peking_duck"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "beijing.dish.zhajiangmian"
     :culture/name "Zhajiangmian"
     :culture/name-local "炸酱面"
     :culture/municipality "beijing"
     :culture/country "CHN"
     :culture/kind :dish
     :culture/summary "Thick wheat noodles topped with zhajiang, a fermented soybean-based sauce; the dish originates in Shandong, and the Beijing variation is its best-known regional adaptation, recognised as one of the Ten Great Noodles of China."
     :culture/url "https://en.wikipedia.org/wiki/Zhajiangmian"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "beijing.dish.douzhi"
     :culture/name "Douzhi"
     :culture/name-local "豆汁"
     :culture/municipality "beijing"
     :culture/country "CHN"
     :culture/kind :dish
     :culture/summary "A traditional fermented mung-bean drink in Beijing cuisine with a recorded history of over 300 years, taken as a breakfast beverage alongside fried dough and pickled vegetables."
     :culture/url "https://en.wikipedia.org/wiki/Douzhi"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "beijing.performing-art.peking-opera"
     :culture/name "Peking opera"
     :culture/name-local "京剧"
     :culture/municipality "beijing"
     :culture/country "CHN"
     :culture/kind :performing-art
     :culture/summary "The dominant form of Chinese opera, combining instrumental music, vocal performance, mime, martial arts, dance and acrobatics; it arose in Beijing in the mid-Qing dynasty and was fully developed by the mid-19th century, and was inscribed as UNESCO Intangible Cultural Heritage in 2010."
     :culture/url "https://en.wikipedia.org/wiki/Peking_opera"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "beijing.heritage.forbidden-city"
     :culture/name "Forbidden City"
     :culture/name-local "紫禁城"
     :culture/municipality "beijing"
     :culture/country "CHN"
     :culture/kind :heritage
     :culture/summary "The imperial palace complex at the centre of the Imperial City in Beijing; residence of 24 Ming and Qing emperors and the centre of political power in China for over 500 years from 1420 to 1924, designated a UNESCO World Heritage Site in 1987."
     :culture/url "https://en.wikipedia.org/wiki/Forbidden_City"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "beijing.heritage.temple-of-heaven"
     :culture/name "Temple of Heaven"
     :culture/name-local "天坛"
     :culture/municipality "beijing"
     :culture/country "CHN"
     :culture/kind :heritage
     :culture/summary "A complex of imperial religious Confucian buildings in the south-eastern part of central Beijing, built between 1406 and 1420 under the Ming dynasty and inscribed as a UNESCO World Heritage Site in 1998."
     :culture/url "https://en.wikipedia.org/wiki/Temple_of_Heaven"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "beijing.heritage.summer-palace"
     :culture/name "Summer Palace"
     :culture/name-local "颐和园"
     :culture/municipality "beijing"
     :culture/country "CHN"
     :culture/kind :heritage
     :culture/summary "A vast ensemble of lakes, gardens and palaces in Beijing, recognised as a masterpiece of Chinese landscape garden design and inscribed as a UNESCO World Heritage Site in 1998."
     :culture/url "https://en.wikipedia.org/wiki/Summer_Palace"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}
    {:culture/id "beijing.heritage.badaling"
     :culture/name "Badaling"
     :culture/name-local "八达岭"
     :culture/municipality "beijing"
     :culture/country "CHN"
     :culture/kind :heritage
     :culture/summary "The most visited section of the Great Wall of China, roughly 80 km north-west of central Beijing in Yanqing District; the portion of wall running through the site was built in 1504 during the Ming dynasty. The fetched source does NOT state a UNESCO designation for this section, so none is recorded here."
     :culture/url "https://en.wikipedia.org/wiki/Badaling"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-27"}]})

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
      :note (str "cloud-itonami-municipality-chn-beijing culture catalog "
                 "(ADR-2607171400): " (count (get catalog "beijing"))
                 " Beijing entries, each with a fetched-and-read citation. "
                 "No :festival and no :craft entry yet -- both were "
                 "investigated and dropped because the sources fetched did "
                 "not tie them to Beijing specifically (see ns docstring). "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [muni kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis muni)))
