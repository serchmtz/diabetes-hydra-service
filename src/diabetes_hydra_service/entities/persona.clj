(ns diabetes-hydra-service.entities.persona
  (:require 
   [levanzo.hydra :as hydra]
   [levanzo.xsd :as xsd]
   [diabetes-hydra-service.namespaces :refer [persona]]))


;; Porperties
(def tieneCedulaProfesional
  (hydra/property
   {::hydra/id (persona "tieneCedulaProfesional")
    ::hydra/title "tieneCedulaProfesional"
    ::hydra/description "La cédula profesional de un médico"
    ::hydra/range xsd/string}))

(def tieneFechaDeNacimiento
  (hydra/property
   {::hydra/id (persona "tieneFechaDeNacimiento")
    ::hydra/title "tieneFechaDeNacimiento"
    ::hydra/description "La fecha de nacimiento de la persona"
    ::hydra/range xsd/date-time}))

(def tieneSexo
  (hydra/property
   {::hydra/id (persona "tieneSexo")
    ::hydra/title "tieneSexo"
    ::hydra/description "El género o sexo de la persona"
    ::hydra/range xsd/string}))

(def tienePrimerApellido
  (hydra/property
   {::hydra/id (persona "tienePrimerApellido")
    ::hydra/title "tienePrimerApellido"
    ::hydra/description "El primer apellido de la persona"
    ::hydra/range xsd/string}))

(def tieneSegundoApellido
  (hydra/property
   {::hydra/id (persona "tieneSegundoApellido")
    ::hydra/title "tieneSegundoApellido"
    ::hydra/description "El segundo apellido de la persona"
    ::hydra/range xsd/string}))

(def tieneNombre
  (hydra/property
   {::hydra/id (persona "tieneNombre")
    ::hydra/title "tieneNombre"
    ::hydra/description "El nombre de la persona"
    ::hydra/range xsd/string}))

(def Persona
  (hydra/class
   {::hydra/id (persona "Persona")
    ::hydra/title "Persona"
    ::hydra/description "Una persona"
    ::hydra/supported-properties                                   
    [(hydra/supported-property
      {::hydra/property tieneSexo
       ::hydra/required true})
     (hydra/supported-property
      {::hydra/property tieneNombre
       ::hydra/required true})
     (hydra/supported-property
      {::hydra/property tieneSegundoApellido
       ::hydra/required false})
     (hydra/supported-property
      {::hydra/property tienePrimerApellido
       ::hydra/required true})]
    ::hydra/operations
    [(hydra/get-operation {::hydra/returns (persona "Persona")})
     ;; (hydra/delete-operation {})
     ]}))

(def info-basica
  (hydra/link
   {::hydra/id (persona "info-basica")
    ::hydra/title "info-basica"
    ::hydra/description "Información básica de una persona"
    ::hydra/range (hydra/id Persona)}))

(def info-basica-link
  (hydra/supported-property
   {::hydra/id (persona "info-basica-link")
    ::hydra/property info-basica
    ::hydra/readonly true
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/returns (hydra/id Persona)})
     (hydra/post-operation
      {::hydra/expects (hydra/id Persona)
       ::hydra/returns (hydra/id Persona)})]}))

(def Paciente
  (hydra/class
   {::hydra/id (persona "Paciente")
    ::hydra/title "Paciente"
    ::hydra/description "Un paciente"
    ::hydra/supported-properties                                   
    [info-basica-link
     (hydra/supported-property
      {::hydra/property tieneFechaDeNacimiento
       ::hydra/required true})]
    ::hydra/operations
    [(hydra/get-operation {::hydra/returns (persona "Paciente")})
     (hydra/delete-operation {})]}))


(def Medico
  (hydra/class
   {::hydra/id (persona "Medico")
    ::hydra/title "Medico"
    ::hydra/description "Un médico"
    ::hydra/supported-properties                                   
    [info-basica-link
     (hydra/supported-property
      {::hydra/property tieneCedulaProfesional
       ::hydra/required true})]
    ::hydra/operations
    [(hydra/get-operation {::hydra/returns (persona "Medico")})
     (hydra/delete-operation {})]}))

;; Collections

(def PersonaCollection
  (hydra/collection
   {::hydra/id (persona "PersonaCollection")
    ::hydra/title "PersonaCollection"
    ::hydra/description "Colleción de todas las personas"
    ::hydra/is-paginated false
    ::hydra/member-class (hydra/id Persona)
    ;; ::hydra/operations
    ;; [(hydra/get-operation
    ;;   {::hydra/returns (persona "PersonaCollection")})
    ;;  (hydra/post-operation
    ;;   {::hydra/expects (hydra/id Persona)
    ;;    ::hydra/returns (hydra/id Persona)})]
    }))
