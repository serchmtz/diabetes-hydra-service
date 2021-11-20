(ns diabetes-hydra-service.entities.persona
  (:require 
   [levanzo.hydra :as hydra]
   [diabetes-hydra-service.owl-mapper :as owlm]
   [diabetes-hydra-service.namespaces :refer [persona]]   
   [diabetes-hydra-service.entities.core :refer [register-class]]))

;; Sets the default ontology
(tawny.owl/ontology-to-namespace owlm/red-ont)

(def Persona
  (owlm/owl-class-to-hydra
   "persona:Persona"
   [(hydra/get-operation
     {::hydra/id (persona "get-persona")
      ::hydra/title "Obtiene una persona"
      ::hydra/description "Obtiene una persona"
      ::hydra/returns (persona "Persona")})
    (hydra/delete-operation
     {::hydra/id (persona "delete-persona")
      ::hydra/title "Elimina una persona"
      ::hydra/description "Elimina una persona"})]))
(register-class Persona)


(def Medico
  (owlm/owl-class-to-hydra
   "persona:Medico"
   [(hydra/get-operation
     {::hydra/id (persona "get-medico")
      ::hydra/title "Obtiene un médico"
      ::hydra/description "Obtiene un médico"
      ::hydra/returns (persona "Medico")})
    (hydra/delete-operation
     {::hydra/id (persona "delete-medico")
      ::hydra/title "Elimina un médico"
      ::hydra/description "Elimina un médico"})]))
(register-class Medico)

(def Paciente
  (owlm/owl-class-to-hydra
   "persona:Paciente"
   [(hydra/get-operation
     {::hydra/id (persona "get-paciente")
      ::hydra/title "Obtiene un paciente"
      ::hydra/description "Obtiene un paciente"
      ::hydra/returns (persona "Paciente")})
    (hydra/delete-operation
     {::hydra/id (persona "delete-paciente")
      ::hydra/title "Elimina un paciente"
      ::hydra/description "Elimina un paciente"})]))
(register-class Paciente)

;; Collections

(def PersonaCollection
  (hydra/collection
   {::hydra/id (persona "PersonaCollection")
    ::hydra/title "PersonaCollection"
    ::hydra/description "Coleción de todas las personas"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id Persona)}))
(register-class PersonaCollection)


(def MedicoCollection
  (hydra/collection
   {::hydra/id (persona "MedicoCollection")
    ::hydra/title "MedicoCollection"
    ::hydra/description "Coleción de todos los médicos"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id Medico)}))
(register-class MedicoCollection)


(def PacienteCollection
  (hydra/collection
   {::hydra/id (persona "PacienteCollection")
    ::hydra/title "PacienteCollection"
    ::hydra/description "Coleción de todos los pacientes"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id Paciente)}))
(register-class PacienteCollection)
