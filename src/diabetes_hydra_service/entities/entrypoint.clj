(ns diabetes-hydra-service.entities.entrypoint
  (:require [levanzo.hydra :as hydra]
            [diabetes-hydra-service.entities.core :refer [register-class]]
            [diabetes-hydra-service.namespaces :refer [vocab]]
            [diabetes-hydra-service.entities.persona :as persona]))
;; Properties

(def personas-link
  (hydra/link
   {::hydra/id (vocab "personas")
    ::hydra/title "Enlace a  la colección de personas."
    ::hydra/domain (vocab "EntryPoint")
    ::hydra/range (hydra/id persona/PersonaCollection)}))

(def personas-prop
  (hydra/supported-property
   {::hydra/title "personas"
    ::hydra/description "Colección de todas las personas."
    ::hydra/required true
    ::hydra/property personas-link
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/id (vocab "get-personas")
       ::hydra/expexts nil
       ::hydra/title "Obtiene todas las personas"
       ::hydra/returns (hydra/id persona/PersonaCollection)})
     (hydra/post-operation
      {::hydra/id (vocab "post-persona")
       ::hydra/title "Registra una persona"
       ::hydra/expects (hydra/id persona/Persona)
       ::hydra/returns (hydra/id persona/Persona)})]}))

;; Classes

(def EntryPoint
  (hydra/class
   {::hydra/id (vocab "EntryPoint")
    ::hydra/title "EntryPoint"
    ::hydra/description "The main entry point of the API"
    ::hydra/supported-properties
    [personas-prop]
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/id (vocab "get-entrypoint")
       ::hydra/returns (vocab "EntryPoint")
       ::hydra/title "Obtiene el punto de entrada"
       ::hydra/description "El punto de entrada principal de la API"})]}))

(register-class EntryPoint)
