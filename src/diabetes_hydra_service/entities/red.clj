(ns diabetes-hydra-service.entities.red
  (:require
   [levanzo.hydra :as hydra]
   [diabetes-hydra-service.owl-mapper :as owlm]
   [diabetes-hydra-service.namespaces :refer [red]]
   [diabetes-hydra-service.entities.core :refer [register-class]]))

;; Set the default ontology
(tawny.owl/ontology-to-namespace owlm/red-ont)

(def DosisEnReceta
  (owlm/owl-class-to-hydra
   "red:Dosis_En_Receta"
   [(hydra/get-operation
     {::hydra/id (red "get-dosis-en-receta")
      ::hydra/title "Obtiene un objeto Dosis_En_Receta"
      ::hydra/description "Obtiene un objeto Dosis_En_Receta"
      ::hydra/returns (red "Dosis_En_Receta")})
    (hydra/delete-operation
     {::hydra/id (red "delete-dosis-en-receta")
      ::hydra/title "Elimina un objeto Dosis_En_Receta"
      ::hydra/description "Elimina un objeto Dosis_En_Receta"})]))

(register-class DosisEnReceta)
