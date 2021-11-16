(ns diabetes-hydra-service.entities.entrypoint
  (:require [levanzo.hydra :as hydra]
            [diabetes-hydra-service.namespaces :refer [vocab]]
            [diabetes-hydra-service.entities.persona :as persona]))

;; Entry Point
(def personas-link
  (hydra/link
   {::hydra/id (vocab "personas")
    ::hydra/title "Enlace a  la colección de personas."
    ::hydra/domain (vocab "EntryPoint")
    ::hydra/range (hydra/id persona/PersonaCollection)}))
;; (def vocab-personas-link
;;   (hydra/link
;;    {::hydra/id (vocab "personas-link")
;;     ::hydra/title "personas-link"
;;     ::hydra/description "Enlace a la colección de personas"
;;     ::hydra/range (vocab "PersonaCollection")}))

;; (def vocab-personas
;;   (hydra/supported-property
;;    {::hydra/id (vocab "personas")
;;     ::hydra/property vocab-personas-link
;;     ::hydra/operations
;;     [(hydra/get-operation {::hydra/id (vocab "personas-get-operation")
;;                            ::hydra/returns (vocab "PersonaCollection")})
;;      (hydra/post-operation
;;       {::hydra/id (vocab "personas-post-operation")
;;        ::hydra/expects (persona "Persona")
;;        ::hydra/returns (persona "Persona")})]}))

(def personas-prop
  (hydra/supported-property
   {::hydra/id (vocab "EntryPoint/personas")
    ::hydra/title "personas"
    ::hydra/description "All Personas"
    ::hydra/property personas-link
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/returns (hydra/id persona/PersonaCollection)})
     (hydra/post-operation
      {::hydra/expects (hydra/id persona/Persona)
       ::hydra/returns (hydra/id persona/Persona)})]
    }))



(def EntryPoint
  (hydra/class
   {::hydra/id (vocab "EntryPoint")
    ::hydra/title "EntryPoint"
    ::hydra/description "The main entry point of the API"
    ::hydra/supported-properties
    [personas-prop]
    ;; [vocab-personas]
    ;; [(hydra/supported-property
    ;;   {::hydra/id (vocab "personas-link")
    ;;    ::hydra/property personas-link
    ;;    ::hydra/operations
    ;;    [(hydra/get-operation {::hydra/returns (get-in personas-link [:rdf-props ::hydra/range])})
    ;;     (hydra/post-operation {::hydra/expects (persona "Persona")
    ;;                            ::hydra/returns (persona "Persona")})]})]
    ::hydra/operations
    [(hydra/get-operation
      {::hydra/returns (vocab "EntryPoint")
       ::hydra/title "EntryPoint"
       ::hydra/description "The API main entry point"})]}))
