(ns diabetes-hydra-service.entities.datos
  (:require
   [diabetes-hydra-service.owl-mapper :as owlm]
   [diabetes-hydra-service.entities.core :refer [register-class]]))

(tawny.owl/ontology-to-namespace owlm/red-ont)

(def Biotipo (owlm/owl-class-to-hydra "datos:Biotipo"))
(register-class Biotipo)

(def NotaMedica (owlm/owl-class-to-hydra "datos:Nota_Medica"))
(register-class NotaMedica)
