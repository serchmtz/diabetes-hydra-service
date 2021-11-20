(ns diabetes-hydra-service.entities.estados
  (:require 
   [levanzo.hydra :as hydra]
   [diabetes-hydra-service.owl-mapper :as owlm]
   [diabetes-hydra-service.namespaces :refer [estados]]   
   [diabetes-hydra-service.entities.core :refer [register-class]]))

;; Sets the default ontology
(tawny.owl/ontology-to-namespace owlm/red-ont)

(def LocalizacionGeografica
  (owlm/owl-class-to-hydra
   "estados:Localizacion_Geografica"
   [(hydra/get-operation
     {::hydra/id (estados "get-localizacion")
      ::hydra/title "Obtiene una localización"
      ::hydra/description "Obtiene una localización"
      ::hydra/returns (estados "Localizacion_Geografica")})
    (hydra/delete-operation
     {::hydra/id (estados "delete-persona")
      ::hydra/title "Elimina una localización"
      ::hydra/description "Elimina una localización"})]))
(register-class LocalizacionGeografica)

(def LocalizacionGeograficaCollection
  (hydra/collection
   {::hydra/id (estados "LocalizacionGeograficaCollection")
    ::hydra/title "LocalizacionGeograficaCollection"
    ::hydra/description "Coleción de todas las localizaciones geográficas"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id LocalizacionGeografica)}))
(register-class LocalizacionGeograficaCollection)
