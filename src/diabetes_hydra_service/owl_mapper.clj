(ns diabetes-hydra-service.owl-mapper
  (:require
   [clojure.java.io :as io]
   [tawny.lookup :as l]
   [tawny.owl :as owl]
   [tawny.query :as q]
   [tawny.reasoner :as rs]
   [tawny.read :as twr]
   [levanzo.hydra :as hydra])
  (:import
   [org.semanticweb.owlapi.model OWLDataOneOf]
   org.semanticweb.owlapi.util.AutoIRIMapper))


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


(twr/defread red-ont
  :location (owl/iri (io/resource "red_nodos_RDF.owl"))
  :prefix "red"
  :iri "http://www.diabetes-mexico.org/red"
  :viri "http://www.diabetes-mexico.org/red"
  :mapper (AutoIRIMapper. (io/file "resources") true))

(rs/reasoner-silent)
(rs/reasoner-factory :hermit)
;;(rs/consistent? red)

(owl/defno get-prefix
  [onto]
  (-> (owl/owl-ontology-manager)
      (.getOntologyFormat onto)
      .asPrefixOWLOntologyFormat))

(owl/defno get-prefixed-class
  "Return a class of ontology o with prefixed name"
  [onto name]
  (let [prefixm (get-prefix onto)]
    (.getOWLClass (owl/owl-data-factory) name prefixm)))

(owl/defno get-prefixed-individual
  "Return a individual of ontology o with prefixed name"
  [onto name]
  (let [prefixm (get-prefix onto)]
    (.getOWLNamedIndividual (owl/owl-data-factory) name prefixm)))

(owl/defno get-prefixed-data-prop
  "Return a DataPropety of ontology o with prefixed name"
  [onto name]
  (let [prefixm (get-prefix onto)]
    (.getOWLDataProperty (owl/owl-data-factory) name prefixm)))

(owl/defno get-prefixed-obj-prop
  "Return a ObjectPropety of ontology o with prefixed name"
  [onto name]
  (let [prefixm (get-prefix onto)]
    (.getOWLObjectProperty (owl/owl-data-factory) name prefixm)))

(owl/defno data-prop-to-map
  [onto prop]
  {:prop (l/named-entity-as-string prop)
   :prop-name (-> (l/named-entity-as-string prop) owl/iri .getFragment)
   :prop-type :data-prop
   :range (->> (q/map-imports #(.getDataPropertyRangeAxioms % prop) onto)
               (map
                (fn [ax]
                  (let [range (.getRange ax)]
                    (if (instance? OWLDataOneOf range)
                      (let [literals (.getValues range)
                            values (mapv #(.getLiteral %) literals)
                            dtype (-> owl/owl2datatypes
                                      :XSD_STRING
                                      .toStringID)]
                        {:type dtype
                         :values values})
                      {:type (.toStringID range)}))))
               first)})

(owl/defno get-data-props
  [onto owlclass]
  (let [parents (clojure.set/union #{owlclass} (rs/isuperclasses owlclass))]
    (->>
     (for [prop (q/map-imports q/data-props onto)
           :let [domain (->> (.getDataPropertyDomains (rs/reasoner) prop false)
                             rs/entities
                             rs/no-top-bottom)]]
       (when (clojure.set/subset? domain parents)
         (data-prop-to-map onto prop)))
     (remove nil?)
     vec)))


(owl/defno object-prop-to-map
  [_ prop]
  {:prop (l/named-entity-as-string prop)
   :prop-name (-> (l/named-entity-as-string prop) owl/iri .getFragment)
   :prop-type :object-prop
   :range (->>
           (.getObjectPropertyRanges (rs/reasoner) prop true)
           rs/entities
           (map (fn [e] (.toStringID e)))
           first
           (assoc {} :type ))})

(owl/defno get-object-props
  [onto owlclass]
  (let [parents (clojure.set/union #{owlclass} (rs/isuperclasses owlclass))]
    (->>
     (for [prop (q/map-imports q/obj-props onto)
           :let [domain (->> (.getObjectPropertyDomains (rs/reasoner) prop false)
                             rs/entities
                             rs/no-top-bottom)]]
       (when (clojure.set/subset? domain parents)
         (object-prop-to-map prop)))
     (remove nil?)
     vec)))

(defn prop-map-to-hydra
  [{:keys [prop prop-name prop-type range]}]
  (let [prop-fn (get {:object-prop hydra/link
                      :data-prop hydra/property} prop-type)]
    (prop-fn
     {::hydra/id prop
      ::hydra/range (:type range)
      ::hydra/title prop-name})))

(owl/defno owl-class-to-hydra
  ([onto classname]
   (owl-class-to-hydra onto classname nil))
  ([onto classname operations]
   (let [owlclass (get-prefixed-class onto classname)
         operations (or operations [])
         dprops (get-data-props onto owlclass)
         oprops (get-object-props onto owlclass)
         sprops (->> (concat dprops oprops)
                     (map #(hydra/supported-property
                            {::hydra/property (prop-map-to-hydra %)
                             ::hydra/required false})))]
     (hydra/class
      {::hydra/id (l/named-entity-as-string owlclass)
       ::hydra/title (-> (l/named-entity-as-string owlclass) owl/iri .getFragment)
       ::hydra/supported-properties
       (vec sprops)
       ::hydra/operations
       operations}))))
