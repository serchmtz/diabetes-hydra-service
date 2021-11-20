(ns diabetes-hydra-service.namespaces
  (:require [levanzo.namespaces :refer [define-rdf-ns default-ns] :as lns]
            [diabetes-hydra-service.config :as config]))

;; (reset! lns/*ns-register* {})
;; (reset! lns/*inverse-ns-register* {})

(define-rdf-ns vocab (str config/base "api/vocab#"))
(define-rdf-ns lvz "http://levanzo.org/vocab#")
(define-rdf-ns sh "http://www.w3.org/ns/shacl#")
;;(define-rdf-ns diabetes "http://www.diabetes-mexico.org/red")
(define-rdf-ns red "http://www.diabetes-mexico.org/red#")
(define-rdf-ns xml "http://www.w3.org/XML/1998/namespace")
(define-rdf-ns swrl "http://www.w3.org/2003/11/swrl#")
(define-rdf-ns datos "http://www.modelo.org/datos#")
(define-rdf-ns swrla "http://swrl.stanford.edu/ontologies/3.3/swrla.owl#")
(define-rdf-ns swrlb "http://www.w3.org/2003/11/swrlb#")
(define-rdf-ns estados "http://www.estados-mexico.org/estados#")
(define-rdf-ns niveles "http://www.niveleseducativos-mexico.org/niveles#")
(define-rdf-ns persona "http://www.personas-mexico.org/persona#")
(define-rdf-ns enfermedad "http://www.padecimientos-mexico.org/enfermedad#")
(define-rdf-ns tratamiento "http://www.medida-control.com/tratamiento#")
(define-rdf-ns medicamentos "http://www.owl-ontologies.com/medicamentos.owl#")
(define-rdf-ns schema "http://schema.org/")
