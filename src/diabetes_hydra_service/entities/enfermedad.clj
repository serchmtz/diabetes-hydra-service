(ns diabetes-hydra-service.entities.enfermedad
  (:require
   [levanzo.hydra :as hydra]
   [diabetes-hydra-service.owl-mapper :as owlm]
   [diabetes-hydra-service.namespaces :refer [enfermedad]]
   [diabetes-hydra-service.entities.core :refer [register-class]]))

;; Set the default ontology
(tawny.owl/ontology-to-namespace owlm/red-ont)

(def Padecimiento
  (owlm/owl-class-to-hydra
   "enfermedad:Padecimiento"
   [(hydra/get-operation
     {::hydra/id (enfermedad "get-padecimiento")
      ::hydra/title "Obtiene un padecimiento"
      ::hydra/description "Obtiene un padecimiento"
      ::hydra/returns (enfermedad "Padecimiento")})
    (hydra/delete-operation
     {::hydra/id (enfermedad "delete-padecimiento")
      ::hydra/title "Elimina un padecimiento"
      ::hydra/description "Elimina un padecimiento"})]))

(register-class Padecimiento)


(def Discapacidad
  (owlm/owl-class-to-hydra
   "enfermedad:Discapacidad"
   [(hydra/get-operation
     {::hydra/id (enfermedad "get-discapacidad")
      ::hydra/title "Obtiene una discapacidad"
      ::hydra/description "Obtiene una discapacidad"
      ::hydra/returns (enfermedad "Discapacidad")})
    (hydra/delete-operation
     {::hydra/id (enfermedad "delete-discapacidad")
      ::hydra/title "Elimina una discapacidad"
      ::hydra/description "Elimina una discapacidad"})]))

(register-class Discapacidad)
