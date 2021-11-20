(ns diabetes-hydra-service.entities.datos
  (:require
   [levanzo.hydra :as hydra]
   [diabetes-hydra-service.owl-mapper :as owlm]
   [diabetes-hydra-service.namespaces :refer [datos]]
   [diabetes-hydra-service.entities.core :refer [register-class]]))

(tawny.owl/ontology-to-namespace owlm/red-ont)

(def Biotipo (owlm/owl-class-to-hydra "datos:Biotipo"))
(register-class Biotipo)

(def NotaMedica (owlm/owl-class-to-hydra "datos:Nota_Medica"))
(register-class NotaMedica)

(def ExpedienteClinico
  (owlm/owl-class-to-hydra
   "datos:Expediente_Clinico"
   [(hydra/get-operation
     {::hydra/id (datos "get-expediente")
      ::hydra/title "Obtiene un expediente clínico"
      ::hydra/description "Obtiene un expediente clínico"
      ::hydra/returns (datos "Expediente_Clinico")})
    (hydra/delete-operation
     {::hydra/id (datos "delete-expediente")
      ::hydra/title "Elimina un expediente clínico"
      ::hydra/description "Elimina un expediente clínico"})]))
(register-class ExpedienteClinico)

(def HistoriaClinica (owlm/owl-class-to-hydra "datos:Historia_Clinica"))
(register-class HistoriaClinica)

