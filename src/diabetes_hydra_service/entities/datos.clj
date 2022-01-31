(ns diabetes-hydra-service.entities.datos
  (:require
   [levanzo.hydra :as hydra]
   [diabetes-hydra-service.owl-mapper :as owlm]
   [diabetes-hydra-service.namespaces :refer [datos]]
   [diabetes-hydra-service.entities.core :refer [register-class]]
   [tawny.owl :as owl]))

(tawny.owl/ontology-to-namespace owlm/red-ont)

(def Biotipo (owlm/owl-class-to-hydra "datos:Biotipo"))
(register-class Biotipo)

(def NotaMedica
  (owlm/owl-class-to-hydra
   "datos:Nota_Medica"
   [(hydra/get-operation
     {::hydra/id (datos "get-nota")
      ::hydra/title "Obtiene una nota médica"
      ::hydra/description "Obtiene una nota médica"
      ::hydra/returns (datos "Nota_Medica")})
    (hydra/delete-operation
     {::hydra/id (datos "delete-nota")
      ::hydra/title "Elimina una nota médica"
      ::hydra/description "Elimina una nota médica"})
    (hydra/put-operation
     {::hydra/id (datos "put-nota")
      ::hydra/title "Crea o actualiza una nota médica"
      ::hydra/expects (datos "Nota_Medica")
      ::hydra/returns (datos "Nota_Medica")
      ::hydra/description "Crea o actualiza una nota médica"})]))
(register-class NotaMedica)

(def NotaMedicaCollection
  (hydra/collection
   {::hydra/id (datos "NotaMedicaCollection")
    ::hydra/title "NotaMedicaCollection"
    ::hydra/description "Colección de notas médicas"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id NotaMedica)
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/title "Obtener notas médicas"
       ::hydra/returns (datos "NotaMedicaCollection")})
     (hydra/post-operation
      {::hydra/title "Registrar nota médica"
       ::hydra/expects (hydra/id NotaMedica)
       ::hydra/returns (hydra/id NotaMedica)})]}))

(register-class NotaMedicaCollection)

;; (def notas-medicas-prop
;;   (hydra/supported-property
;;    {::hydra/property
;;     (hydra/property
;;      {::hydra/id (datos "tieneNotaMedica")
;;       ::hydra/title "tieneNotaMedica"
;;       ::hydra/range (hydra/id NotaMedicaCollection)})
;;     ::hydra/required false}))

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
      ::hydra/description "Elimina un expediente clínico"})
    (hydra/put-operation
     {::hydra/id (datos "put-expediente")
      ::hydra/title "Crea o actualiza un expediente clínico"
      ::hydra/expects (datos "Expediente_Clinico")
      ::hydra/returns (datos "Expediente_Clinico")
      ::hydra/description "Crea o actualiza un expediente clínico"})]))

;; (def ExpedienteClinico (owlm/update-supported-property
;;                         ExpedienteClinico
;;                         (datos "tieneNotaMedica")
;;                         notas-medicas-prop))

(register-class ExpedienteClinico)

(def ExpedienteClinicoCollection
  (hydra/collection
   {::hydra/id (datos "ExpedienteClinicoCollection")
    ::hydra/title "ExpedienteClinicoCollection"
    ::hydra/description "Colección de expedientes clínicos"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id ExpedienteClinico)
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/title "Obtener expedientes"
       ::hydra/returns (datos "ExpedienteClinicoCollection")})
     (hydra/post-operation
      {::hydra/title "Registrar expediente"
       ::hydra/expects (hydra/id ExpedienteClinico)
       ::hydra/returns (hydra/id ExpedienteClinico)})]}))

(register-class ExpedienteClinicoCollection)

(def HistoriaClinica
  (owlm/owl-class-to-hydra
   "datos:Historia_Clinica"
   [(hydra/get-operation
     {::hydra/id (datos "get-historia")
      ::hydra/title "Obtiene una historia clínica"
      ::hydra/description "Obtiene una historia clínica"
      ::hydra/returns (datos "Historia_Clinica")})
    (hydra/delete-operation
     {::hydra/id (datos "delete-historia")
      ::hydra/title "Elimina una historia clínica"
      ::hydra/description "Elimina una historia clínica"})
    (hydra/put-operation
     {::hydra/id (datos "put-historia")
      ::hydra/title "Crea o actualiza una historia clínica"
      ::hydra/expects (datos "Historia_Clinica")
      ::hydra/returns (datos "Historia_Clinica")
      ::hydra/description "Crea o actualiza una historia clínica"})]))
(register-class HistoriaClinica)

(def HistoriaClinicaCollection
  (hydra/collection
   {::hydra/id (datos "HistoriaClinicaCollection")
    ::hydra/title "HistoriaClinicaCollection"
    ::hydra/description "Colección de historias clínicas"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id HistoriaClinica)
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/title "Obtener historias"
       ::hydra/returns (datos "HistoriaClinicaCollection")})
     (hydra/post-operation
      {::hydra/title "Registrar historia"
       ::hydra/expects (hydra/id HistoriaClinica)
       ::hydra/returns (hydra/id HistoriaClinica)})]}))

(register-class HistoriaClinicaCollection)
