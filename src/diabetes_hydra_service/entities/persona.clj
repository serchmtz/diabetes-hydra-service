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
      ::hydra/title "Obtener persona"
      ::hydra/description "Obtiene una persona"
      ::hydra/returns (persona "Persona")})
    (hydra/delete-operation
     {::hydra/id (persona "delete-persona")
      ::hydra/title "Eliminar persona"
      ::hydra/description "Elimina una persona"})
    (hydra/put-operation
     {::hydra/id (persona "put-persona")
      ::hydra/title "Actualizar persona"
      ::hydra/expects (persona "Persona")
      ::hydra/returns (persona "Persona")
      ::hydra/description "Actualiza un persona"})]))
(register-class Persona)


(def Medico
  (owlm/owl-class-to-hydra
   "persona:Medico"
   [(hydra/get-operation
     {::hydra/id (persona "get-medico")
      ::hydra/title "Obtener médico"
      ::hydra/description "Obtiene un médico"
      ::hydra/returns (persona "Medico")})
    (hydra/delete-operation
     {::hydra/id (persona "delete-medico")
      ::hydra/title "Eliminar médico"
      ::hydra/description "Elimina un médico"})
    (hydra/put-operation
     {::hydra/id (persona "put-medico")
      ::hydra/title "Actualizar médico"
      ::hydra/description "Actualiza un médico"
      ::hydra/expects (persona "Medico")
      ::hydra/returns (persona "Medico")})]))
(register-class Medico)

(def Paciente
  (owlm/owl-class-to-hydra
   "persona:Paciente"
   [(hydra/get-operation
     {::hydra/id (persona "get-paciente")
      ::hydra/title "Obtener paciente"
      ::hydra/description "Obtiene un paciente"
      ::hydra/returns (persona "Paciente")})
    (hydra/delete-operation
     {::hydra/id (persona "delete-paciente")
      ::hydra/title "Eliminar paciente"
      ::hydra/description "Elimina un paciente"})
    (hydra/put-operation
     {::hydra/id (persona "put-paciente")
      ::hydra/title "Actualizar paciente"
      ::hydra/description "Actualiza un paciente"
      ::hydra/expects (persona "Paciente")
      ::hydra/returns (persona "Paciente")})]))
(register-class Paciente)

;; Collections

(def PersonaCollection
  (hydra/collection
   {::hydra/id (persona "PersonaCollection")
    ::hydra/title "PersonaCollection"
    ::hydra/description "Colección de todas las personas"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id Persona)
    ;; ::hydra/operations
    ;; [(hydra/post-operation
    ;;   {::hydra/id (persona "post-persona")
    ;;    ::hydra/title "Registra una nueva persona"
    ;;    ::hydra/description "Registra una nueva persona"
    ;;    ::hydra/expects (hydra/id Persona)
    ;;    ::hydra/returns (persona "PersonaCollection")})]
    }))
(register-class PersonaCollection)


(def MedicoCollection
  (hydra/collection
   {::hydra/id (persona "MedicoCollection")
    ::hydra/title "MedicoCollection"
    ::hydra/description "Colección de todos los médicos"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id Medico)
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/title "Obtener todos los médicos"
       ::hydra/returns (persona "MedicoCollection")})
     (hydra/post-operation
      {::hydra/title "Registrar médico"
       ::hydra/expects (hydra/id Medico)
       ::hydra/returns (hydra/id Medico)})]}))
(register-class MedicoCollection)


(def PacienteCollection
  (hydra/collection
   {::hydra/id (persona "PacienteCollection")
    ::hydra/title "PacienteCollection"
    ::hydra/description "Colección de todos los pacientes"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id Paciente)
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/title "Obtener todos los pacientes"
       ::hydra/returns (persona "PacienteCollection")})
     (hydra/post-operation
      {::hydra/title "Registrar paciente"
       ::hydra/expects (hydra/id Paciente)
       ::hydra/returns (hydra/id Paciente)})]}))
(register-class PacienteCollection)
