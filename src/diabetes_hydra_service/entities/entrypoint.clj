(ns diabetes-hydra-service.entities.entrypoint
  (:require [levanzo.hydra :as hydra]
            [diabetes-hydra-service.entities.core :refer [register-class]]
            [diabetes-hydra-service.namespaces :refer [vocab]]
            [diabetes-hydra-service.entities.persona :as persona]
            [diabetes-hydra-service.entities.estados :as estados]
            [diabetes-hydra-service.entities.datos :as datos]))
;; Properties

(def personas-link
  (hydra/link
   {::hydra/id (vocab "personas")
    ::hydra/title "personas"
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




(def pacientes-link
  (hydra/link
   {::hydra/id (vocab "pacientes")
    ::hydra/title "pacientes"
    ::hydra/description "Enlace a la colección de personas."
    ::hydra/domain (vocab "EntryPoint")
    ::hydra/range (hydra/id persona/PacienteCollection)}))

(def pacientes-prop
  (hydra/supported-property
   {::hydra/title "pacientes"
    ::hydra/description "Colección de todos los pacientes."
    ::hydra/required true
    ::hydra/property pacientes-link}))



(def medicos-link
  (hydra/link
   {::hydra/id (vocab "medicos")
    ::hydra/title "medicos"
    ::hydra/description "Enlace a la colección de médicos."
    ::hydra/domain (vocab "EntryPoint")
    ::hydra/range (hydra/id persona/MedicoCollection)}))

(def medicos-prop
  (hydra/supported-property
   {::hydra/title "medicos"
    ::hydra/description "Colección de todos los médicos."
    ::hydra/required true
    ::hydra/property medicos-link}))



(def localizaciones-link
  (hydra/link
   {::hydra/id (vocab "localizaciones")
    ::hydra/title "localizaciones"
    ::hydra/description "Enlace a la colección de localizaciones geográficas."
    ::hydra/domain (vocab "EntryPoint")
    ::hydra/range (hydra/id estados/LocalizacionGeograficaCollection)}))

(def localizaciones-prop
  (hydra/supported-property
   {::hydra/title "localizaciones"
    ::hydra/description "Colección de todas las localizaciones geográficas."
    ::hydra/required true  
    ::hydra/property localizaciones-link}))


(def expedientes-link
  (hydra/link
   {::hydra/id (vocab "expedientes")
    ::hydra/title "expedientes"
    ::hydra/description "Enlace a la colección de expedientes clínicos."
    ::hydra/domain (vocab "EntryPoint")
    ::hydra/range (hydra/id datos/ExpedienteClinicoCollection)}))

(def expedientes-prop
  (hydra/supported-property
   {::hydra/title "expedientes"
    ::hydra/description "Colección de todos los expedientes clínicos."
    ::hydra/required true  
    ::hydra/property expedientes-link}))


(def historias-link
  (hydra/link
   {::hydra/id (vocab "historias")
    ::hydra/title "historias"
    ::hydra/description "Enlace a la colección de historias clínicas."
    ::hydra/domain (vocab "EntryPoint")
    ::hydra/range (hydra/id datos/HistoriaClinicaCollection)}))

(def historias-prop
  (hydra/supported-property
   {::hydra/title "historias"
    ::hydra/description "Colección de todas las historias clínicas."
    ::hydra/required true  
    ::hydra/property historias-link}))

(def notas-link
  (hydra/link
   {::hydra/id (vocab "notas")
    ::hydra/title "notas"
    ::hydra/description "Enlace a la colección de notas médicas."
    ::hydra/domain (vocab "EntryPoint")
    ::hydra/range (hydra/id datos/NotaMedicaCollection)}))

(def notas-prop
  (hydra/supported-property
   {::hydra/title "notas"
    ::hydra/description "Colección de todas las notas médicas."
    ::hydra/required true  
    ::hydra/property notas-link}))


;; Classes

(def EntryPoint
  (hydra/class
   {::hydra/id (vocab "EntryPoint")
    ::hydra/title "EntryPoint"
    ::hydra/description "El punto de entrada principal de la API"
    ::hydra/supported-properties
    [medicos-prop
     pacientes-prop
     localizaciones-prop
     expedientes-prop
     historias-prop
     notas-prop]
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/id (vocab "get-entrypoint")
       ::hydra/returns (vocab "EntryPoint")
       ::hydra/title "Obtiene el punto de entrada"})]}))

(register-class EntryPoint)
