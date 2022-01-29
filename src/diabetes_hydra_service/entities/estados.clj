(ns diabetes-hydra-service.entities.estados
  (:require 
   [levanzo.hydra :as hydra]
   [levanzo.xsd :as xsd]
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
      ::hydra/title "Obtener la localización"
      ::hydra/description "Obtener la localización"
      ::hydra/returns (estados "Localizacion_Geografica")})
    (hydra/delete-operation
     {::hydra/id (estados "delete-localizacion")
      ::hydra/title "Eliminar la localización"
      ::hydra/description "Eliminar la localización"})]))
(register-class LocalizacionGeografica)

(def LocalizacionGeograficaCollection
  (hydra/collection
   {::hydra/id (estados "LocalizacionGeograficaCollection")
    ::hydra/title "LocalizacionGeograficaCollection"
    ::hydra/description "Colección de todas las localizaciones geográficas"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id LocalizacionGeografica)
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/id (estados "get-localizaciones")
       ::hydra/title "Obtener las localizaciones"
       ::hydra/returns (estados "LocalizacionGeograficaCollection")})
     (hydra/post-operation
      {::hydra/id (estados "post-localizacion")
       ::hydra/title "Registar localización"
       ::hydra/expects (hydra/id LocalizacionGeografica)
       ::hydra/returns (hydra/id LocalizacionGeografica)})]}))
(register-class LocalizacionGeograficaCollection)
