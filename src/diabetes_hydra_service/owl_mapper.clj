(ns diabetes-hydra-service.owl-mapper
  (:require
   [tawny.lookup :as l]
   [tawny.owl :as owl]
   [tawny.query :as q]
   [tawny.reasoner :as rs]))


;; (clojure.java.javadoc/add-remote-javadoc "org.semanticweb." "http://owlcs.github.io/owlapi/apidocs_4/")
;; (clojure.java.javadoc/add-remote-javadoc "net.sourceforge." "http://owlcs.github.io/owlapi/apidocs_4/")
;; (clojure.java.javadoc/add-remote-javadoc "uk.ac.manchester." "http://owlcs.github.io/owlapi/apidocs_4/")
;; (r/defread red
;;   :location (owl/iri (io/resource "red_nodos_RDF.owl"))
;;   :prefix "red"
;;   :iri "http://www.diabetes-mexico.org/red"
;;   :viri "http://www.diabetes-mexico.org/red"
;;   :mapper (AutoIRIMapper. (io/file "resources") true))

;; (r/defread wine
;;  :location (owl/iri (io/resource "wine.rdf"))
;;  :prefix "wine#"
;;  :iri "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#"
;;  :viri "http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#"
;;  :mapper (AutoIRIMapper. (io/file "resources") true))



(rs/reasoner-factory :hermit)
;;(rs/consistent? red)

(owl/defno get-prefix
  [o]
  (-> (owl/owl-ontology-manager)
      (.getOntologyFormat o)
      .asPrefixOWLOntologyFormat))

(owl/defno get-prefixed-class
  "Return a class of ontology o with prefixed name"
  [o name]
  (let [prefixm (get-prefix o)]
    (.getOWLClass (owl/owl-data-factory) name prefixm)))

(owl/defno get-prefixed-individual
  "Return a individual of ontology o with prefixed name"
  [o name]
  (let [prefixm (get-prefix o)]
    (.getOWLNamedIndividual (owl/owl-data-factory) name prefixm)))

(owl/defno get-prefixed-data-prop
  "Return a DataPropety of ontology o with prefixed name"
  [o name]
  (let [prefixm (get-prefix o)]
    (.getOWLDataProperty (owl/owl-data-factory) name prefixm)))

(owl/defno get-prefixed-obj-prop
  "Return a ObjectPropety of ontology o with prefixed name"
  [o name]
  (let [prefixm (get-prefix o)]
    (.getOWLObjectProperty (owl/owl-data-factory) name prefixm)))

;; (owl/defno get-class-obj-props
;;   "Return all object properties of class name"
;;   [o name]
;;   (let [cls (get-prefixed-class o name)
;;         parents (clojure.set/union #{cls} (rs/isuperclasses cls))]
;;     (remove nil?
;;             (for [prop (q/map-imports q/obj-props o)
;;                   :let [domains (->> (.getObjectPropertyDomains (rs/reasoner) prop true)
;;                                      rs/entities
;;                                      rs/no-top-bottom)]]
;;               (when (clojure.set/subset? domains parents)
;;                 {:prop prop
;;                  :range (rs/entities (.getObjectPropertyRanges (rs/reasoner) prop true))})))))

;; (owl/defno get-class-data-props
;;   "Return all data properties of class name"
;;   [o name]
;;   (let [cls (get-prefixed-class o name)
;;         parents (clojure.set/union #{cls} (rs/isuperclasses cls))]
;;     (remove nil?
;;             (for [prop (q/map-imports q/data-props o)
;;                   :let [domains (->> (.getDataPropertyDomains (rs/reasoner) prop true)
;;                                      rs/entities
;;                                      rs/no-top-bottom)]]
;;               (when (clojure.set/subset? domains parents)
;;                 {:prop prop
;;                  :range (->> (q/map-imports (fn [ont]
;;                                               (.getDataPropertyRangeAxioms ont prop)) o)
;;                              )})))))

;; (defn owl-data-props-map-to-clj
;;   [data-props]
;;   (mapv (fn [p] {:prop (.toStringID (:prop p))
;;                :range (->> (:range p)
;;                            (map (fn [ax]
;;                                   (let [range (.getRange ax)]
;;                                     (if (instance? OWLDataOneOf range)
;;                                       (->> (.getValues range)
;;                                            (mapv #(.. % getLiteral)))
;;                                       (.toStringID range)))))
;;                            first)})
;;         data-props))
;; (#{:foo :bar} :foo)

;; (defn unnamed-entity? [k]
;;   (#{:and :or :not :some :oneof} k))


;; (defn named-entity? [e]
;;   (cond
;;     (and (map? e) (:iri e)) true
;;     (and (seq? e) (some #{:iri} e)) true
;;     :else false))

;; (owl/defno form-to-recur-map
;;   [o form]
;;   (->> form
;;        (clojure.walk/postwalk
;;         (fn [v]
;;           ;; (println "Current: " v)
;;           (cond
;;             ;; v is a seq of maps so realize it to vector
;;             (and (seq? v) (> (count v) 1)(every? map? v))
;;             (vec v)
;;             ;; v is a sequence with even items and each item is not unammed entity
;;             ;; so realize it to a hash map
;;             (and (seq? v)                                       
;;                  (even? (count v))
;;                  (every? #(not (unnamed-entity? %))  v))
;;             (apply hash-map v)

;;             ;; v is a sequence where at least one is a unnamed entity
;;             ;; so transform v in a map with the unnamed entity as key and the rest
;;             ;; as vector value
;;             (and (seq? v) (some unnamed-entity? v))
;;             {(some unnamed-entity? v) (vec (remove unnamed-entity? v)) }
;;             ;; v is sequence of only one item
;;             ;; so unfolded
;;             (and (seq? v) (= (count v) 1))
;;             (first v)
;;             ;; general form of v being a sequence
;;             ;; so realize it to vector
;;             (seq? v)
;;             (vec v)

;;             ;; (and (seq? v) (= 2 (count v)))
;;             ;; v is a symbol, therefore it's a name in the current ontology
;;             ;; so resolve the name to {:iri "value"} maps
;;             (symbol? v)
;;             (apply hash-map (rdr/as-form (owl/iri-for-name o v) :keyword true))
;;             ;; anything else just return v
;;             :else v)))))

;; (owl/defno resolve-recur-map
;;   [o rmap]
;;   (->> rmap
;;        (clojure.walk/postwalk
;;         (fn [v]        
;;           (cond
;;             (map? v)
;;             (let [k (some (fn [[k _vs]] (unnamed-entity? k)) v)
;;                   props (get v k)]
;;               (->>
;;                (for[x props]
;;                  )
;;                doall
;;                ))
;;             :else v)
;;           v))))

;; (s/def ::iri-seq (s/cat :kw  #{:iri}
;;                         :value string?))

;; (s/def ::property (s/*
;;                    (s/cat :kws (s/alt :kw keyword? :sym symbol? :iri (s/and ::iri-seq))
;;                           :val (s/alt :kw keyword? :str string? :num number? :iri (s/and ::iri-seq)))))

;; (s/conform ::property '  (:some
;;                           (:iri "http://www.modelo.org/datos#tieneGlucosaPostPrandial")
;;                           :XSD_INTEGER))

(owl/defno get-data-props
  [o owlclass]
  (let [parents (clojure.set/union #{owlclass} (rs/isuperclasses owlclass))]
    (->>
     (for [prop (q/map-imports q/data-props o)
           :let [domain (->> (.getDataPropertyDomains (rs/reasoner) prop false)
                             rs/entities
                             rs/no-top-bottom)]]
       (when (clojure.set/subset? domain parents)
         {:data-prop (l/named-entity-as-string prop)
          :range (->> (q/map-imports #(.getDataPropertyRangeAxioms % prop) o)
                      (map #(->> (.getDatatypesInSignature %)
                                 (map (fn [dt] (.toStringID dt)))
                                 set))
                      first)}))
     (remove nil?)
     vec)))


(owl/defno get-object-props
  [o owlclass]
  (let [parents (clojure.set/union #{owlclass} (rs/isuperclasses owlclass))]
    (->>
     (for [prop (q/map-imports q/obj-props o)
           :let [domain (->> (.getObjectPropertyDomains (rs/reasoner) prop false)
                             rs/entities
                             rs/no-top-bottom)]]
       (when (clojure.set/subset? domain parents)
         {:data-prop (l/named-entity-as-string prop)
          :range (->>
                  (.getObjectPropertyRanges (rs/reasoner) prop true)
                  rs/entities
                  (map (fn [e] (.toStringID e)))
                  set)}))
     (remove nil?)
     vec)))


