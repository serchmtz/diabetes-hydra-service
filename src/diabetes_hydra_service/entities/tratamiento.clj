(ns diabetes-hydra-service.entities.tratamiento
  (:require
   [levanzo.hydra :as hydra]
   [diabetes-hydra-service.owl-mapper :as owlm]
   [diabetes-hydra-service.namespaces :refer [tratamiento]]
   [diabetes-hydra-service.entities.core :refer [register-class]]))

;; Set the default ontology
(tawny.owl/ontology-to-namespace owlm/red-ont)


(def TratamientoFarmacologico
  (owlm/owl-class-to-hydra
   "tratamiento:Tratamiento_Farmacologico"
   [(hydra/get-operation
     {::hydra/id (tratamiento "get-tratamiento")
      ::hydra/title "Obtiene un tratamiento"
      ::hydra/description "Obtiene un tratamiento"
      ::hydra/returns (tratamiento "Tratamiento_Farmacologico")})
    (hydra/delete-operation
     {::hydra/id (tratamiento "delete-tratamiento")
      ::hydra/title "Elimina un tratamiento"
      ::hydra/description "Elimina un tratamiento"})]))

(register-class TratamientoFarmacologico)

