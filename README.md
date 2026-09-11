# cloud-itonami-municipality-chn-beijing

Municipal-ordinance compliance catalog for **Beijing** (北京市) — the
**first CHN member** of the `cloud-itonami-municipality-*` compliance-fact
family of ADR-2607141700 (`cloud-itonami-compliance-fact-federation`, in
`com-junkawasaki/root`; see e.g.
[`cloud-itonami-municipality-jpn-tokyo`](https://github.com/cloud-itonami/cloud-itonami-municipality-jpn-tokyo),
[`cloud-itonami-municipality-jpn-kyoto`](https://github.com/cloud-itonami/cloud-itonami-municipality-jpn-kyoto)
and
[`cloud-itonami-municipality-kor-seoul`](https://github.com/cloud-itonami/cloud-itonami-municipality-kor-seoul)).

## Why this repo exists

Before it, the 59 `cloud-itonami-municipality-*` repos covered no Chinese
city at all — recorded as a coverage gap in superproject ADR-2607277000
alongside the China marketing vertical and the CAAM association catalog.

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on Beijing Municipality's
behalf, and it is not the municipality.

Coverage is reported honestly (see `ordinance.facts/coverage` and
`culture.facts/coverage`): a municipality not in `catalog` has **no
spec-basis**, full stop — never fabricate one.

## Data

- `src/ordinance/facts.cljk` — 2 ordinance entries, source of truth.
- `src/culture/facts.cljk` — 8 culture entries, source of truth.
- `schema/ordinance.edn` / `schema/culture.edn` — DataScript schemas,
  **deliberately identical to every sibling**; that uniformity is what
  lets the federated query join across repos. A test asserts every
  attribute the catalogs use is declared in the schema, so a new attribute
  cannot diverge silently.
- `data/datascript-tx.edn` / `data/culture-tx.edn` — derived tx-data,
  generated from the catalogs (tests assert they match).

## What was verified, and what deliberately was not

Both ordinance entries were read from **beijing.gov.cn** on **2026-07-27**:

| Entry | Source section | What the page states |
|---|---|---|
| 北京市控制吸烟条例 | 地方性法规 | 2014-11-28 通过 (十四届人大常委会第十五次会议); 修正 by the 2021-09-24 《关于修改部分地方性法规的决定》; 第九条 indoor + public-transport ban, 第十条 four outdoor categories |
| 生活垃圾管理条例 **修改决定** | 政策文件 | 公告〔十五届〕第21号; 成文 2019-11-27; 实施 2020-05-01; 发布 2019-12-02 |

**Three deliberate absences, recorded rather than guessed:**

1. **The waste entry is the amendment *decision*, not the consolidated
   ordinance.** That is the page that was actually read, so the entry is
   `:kind :amendment-decision`, its title says 关于修改, and its
   `:ordinance/number` is that decision's announcement number — *not* a
   number for the underlying ordinance, which this repo has not read. A
   test asserts all of this, and asserts the coverage note says so.
2. **No `:festival` and no `:craft` culture entry.** The generic 庙会
   source fetched does not mention Beijing by name, and the cloisonné
   source attributes 景泰蓝 to the Jingtai Emperor but says nothing about
   Beijing. Recording either would have asserted a municipal connection
   the source does not support. A test asserts both kinds stay empty.
3. **Badaling records no UNESCO designation.** The three entries whose
   sources stated a World Heritage year (Forbidden City 1987, Temple of
   Heaven 1998, Summer Palace 1998) record it; the Badaling source did
   not, so its summary says so explicitly instead. A test asserts the
   distinction.

Ordinance citations are official `beijing.gov.cn` URLs
(`:official-beijing-gov-cn`); culture citations are Wikipedia
(`:wikipedia-en`), the same provenance the Japanese siblings use for
culture entries.

## License

AGPL-3.0-or-later (matches the `cloud-itonami-iso3166-*` /
`-municipality-*` / `-assoc-*` / `-lei-*` convention). Ordinance text
itself remains the municipality's; this repo stores only citation metadata.

## Running it

`clojure -M:test` (16 tests, 47 assertions) and `clojure -M:lint`.
