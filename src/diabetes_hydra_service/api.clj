(ns diabetes-hydra-service.api
  (:require 
   [levanzo.hydra :as hydra]
   [tawny.read :as twr]
   [clojure.java.io :as io]
   [tawny.owl :as owl]
   [levanzo.xsd :as xsd]
   [levanzo.namespaces :refer [hydra define-rdf-ns]]
   ;; [diabetes-hydra-service.owl-mapper :as owlmapper]
   [diabetes-hydra-service.config :as config])
  (:import
   org.semanticweb.owlapi.util.AutoIRIMapper))

;; (twr/defread red-ont
;;   :location (owl/iri (io/resource "red_nodos_RDF.owl"))
;;   :prefix "red"
;;   :iri "http://www.diabetes-mexico.org/red"
;;   :viri "http://www.diabetes-mexico.org/red"
;;   :mapper (AutoIRIMapper. (io/file "resources") true))

(define-rdf-ns vocab (str config/base "vocab#"))
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

;; Porperties
(def persona-tieneCedulaProfesional
  (hydra/property {::hydra/id (persona "tieneCedulaProfesional")
                   ::hydra/title "Cédula profesional"
                   ::hydra/description "La cédula profesional de un médico"
                   ::hydra/range xsd/string}))

(def persona-tieneFechaDeNacimiento
  (hydra/property {::hydra/id (persona "tieneFechaDeNacimiento")
                   ::hydra/title "Fecha de nacimiento"
                   ::hydra/description "La fecha de nacimiento de la persona"
                   ::hydra/range xsd/date-time}))

(def persona-tieneSexo
  (hydra/property {::hydra/id (persona "tieneSexo")
                   ::hydra/title "Sexo"
                   ::hydra/description "El género o sexo de la persona"
                   ::hydra/range xsd/string}))

(def persona-tienePrimerApellido
  (hydra/property {::hydra/id (persona "tienePrimerApellido")
                   ::hydra/title "Primer apellido"
                   ::hydra/description "El primer apellido de la persona"
                   ::hydra/range xsd/string}))

(def persona-tieneSegundoApellido
  (hydra/property {::hydra/id (persona "tieneSegundoApellido")
                   ::hydra/title "Segundo apellido"
                   ::hydra/description "El segundo apellido de la persona"
                   ::hydra/range xsd/string}))

(def persona-tieneNombre
  (hydra/property {::hydra/id (persona "tieneNombre")
                   ::hydra/title "Nombre"
                   ::hydra/description "El nombre de la persona"
                   ::hydra/range xsd/string}))

(def persona-Persona (hydra/class {::hydra/id (persona "Persona")
                                   ::hydra/title "Persona"
                                   ::hydra/description "Una persona"
                                   ::hydra/supported-properties                                   
                                   [(hydra/supported-property
                                     {::hydra/property persona-tieneSexo
                                      ::hydra/required true})
                                    (hydra/supported-property
                                     {::hydra/property persona-tieneNombre
                                      ::hydra/required true})
                                    (hydra/supported-property
                                     {::hydra/property persona-tieneSegundoApellido
                                      ::hydra/required false})
                                    (hydra/supported-property
                                     {::hydra/property persona-tienePrimerApellido
                                      ::hydra/required true})]
                                   ::hydra/operations
                                   [(hydra/get-operation {::hydra/returns (persona "Persona")})
                                    (hydra/delete-operation {})]}))

(def persona-info-basica (hydra/link {::hydra/id (persona "info-basica")
                                      ::hydra/title "info-basica"
                                      ::hydra/description "Información básica de una persona"
                                      ::hydra/range (hydra/id persona-Persona)}))

(def persona-info-basica-link (hydra/supported-property {::hydra/id (persona "info-basica-link")
                                                         ::hydra/property persona-info-basica
                                                         ::hydra/readonly true
                                                         ::hydra/operations
                                                         [(hydra/get-operation
                                                           {::hydra/returns (hydra/id persona-Persona)})
                                                          (hydra/post-operation
                                                           {::hydra/expects (hydra/id persona-Persona)
                                                            ::hydra/returns (hydra/id persona-Persona)})]}))

(def persona-Paciente (hydra/class {::hydra/id (persona "Paciente")
                                    ::hydra/title "Paciente"
                                    ::hydra/description "Un paciente"
                                    ::hydra/supported-properties                                   
                                    [persona-info-basica-link
                                     (hydra/supported-property
                                      {::hydra/property persona-tieneFechaDeNacimiento
                                       ::hydra/required true})]
                                    ::hydra/operations
                                    [(hydra/get-operation {::hydra/returns (persona "Paciente")})
                                     (hydra/delete-operation {})]}))


(def persona-Medico (hydra/class {::hydra/id (persona "Medico")
                                  ::hydra/title "Medico"
                                  ::hydra/description "Un médico"
                                  ::hydra/supported-properties                                   
                                  [persona-info-basica-link
                                   (hydra/supported-property
                                    {::hydra/property persona-tieneCedulaProfesional
                                     ::hydra/required true})]
                                  ::hydra/operations
                                  [(hydra/get-operation {::hydra/returns (persona "Medico")})
                                   (hydra/delete-operation {})]}))

;; Collections

(def persona-PersonaCollection (hydra/collection {::hydra/id (vocab "PersonaCollection")
                                                  ::hydra/title "PersonaCollection"
                                                  ::hydra/description "Colleción de todas las personas"
                                                  ::hydra/is-paginated false
                                                  ::hydra/member-class (persona "Persona")}))
;; Entry Point

(def personas-link (hydra/link {::hydra/id (vocab "personas")
                                ::hydra/title "personas"
                                ::hydra/description "Regresa todas las personas"
                                ::hydra/range (hydra/id persona-PersonaCollection)}))


(def EntryPoint (hydra/class
                 {::hydra/id (vocab "EntryPoint")
                  ::hydra/title "EntryPoint"
                  ::hydra/description "The main entry point of the API"
                  ::hydra/supported-properties
                  [(hydra/supported-property
                    {::hydra/id (vocab "get-personas-link")
                     ::hydra/property personas-link
                     ::hydra/operations
                     [(hydra/get-operation {::hydra/returns (get-in personas-link [:rdf-props ::hydra/range])})]})]
                  ::hydra/operations [(hydra/get-operation {::hydra/returns (vocab "EntryPoint")
                                                            ::hydra/description "The API main entry point"})]}))

(def API (hydra/api {::hydra/entrypoint "/"
                     ::hydra/entrypoint-class (vocab "EntryPoint")
                     ::hydra/supported-classes [EntryPoint
                                                persona-PersonaCollection
                                                persona-Persona
                                                persona-Medico
                                                persona-Paciente]}))
