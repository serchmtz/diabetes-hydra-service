(ns diabetes-hydra-service.api
  (:require
   [levanzo.hydra :as hydra]
   [diabetes-hydra-service.config :as config]
   [diabetes-hydra-service.entities.entrypoint :refer [EntryPoint]]
   [diabetes-hydra-service.entities.persona :as persona]))

(def API
  (hydra/api
   {::hydra/entrypoint (str config/base "api")
    ::hydra/id (str config/base "api/vocab")
    ::hydra/entrypoint-class (hydra/id EntryPoint)
    ::hydra/supported-classes
    [EntryPoint
     persona/Persona
     persona/PersonaCollection]}))
