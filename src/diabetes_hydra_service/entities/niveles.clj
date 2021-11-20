(ns diabetes-hydra-service.entities.niveles
  (:require
   [levanzo.hydra :as hydra]
   [diabetes-hydra-service.owl-mapper :as owlm]
   [diabetes-hydra-service.namespaces :refer [niveles]]
   [diabetes-hydra-service.entities.core :refer [register-class]]))

;; Sets the default ontology
(tawny.owl/ontology-to-namespace owlm/red-ont)

(def Escolaridad
  (owlm/owl-class-to-hydra
   "niveles:Escolaridad"
   [(hydra/get-operation
     {::hydra/id (niveles "get-escolaridad")
      ::hydra/title "Obtiene una escolaridad"
      ::hydra/description "Obtiene una escolaridad"
      ::hydra/returns (niveles "Escolaridad")})
    (hydra/delete-operation
     {::hydra/id (niveles "delete-escolaridad")
      ::hydra/title "Elimina una escolaridad"
      ::hydra/description "Elimina una escolaridad"})]))
(register-class Escolaridad)
