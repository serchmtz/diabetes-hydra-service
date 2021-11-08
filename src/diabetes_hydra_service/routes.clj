(ns diabetes-hydra-service.routes
  (:require [diabetes-hydra-service.api :as api]
            [diabetes-hydra-service.config :as config]
            [levanzo.routing :as routing]
            [levanzo.payload :as payload]))

(defn get-personas
  [args body request]
  (-> (payload/jsonld
       (payload/id {:model (api/vocab "get-personas-link")})
       (payload/type api/persona-PersonaCollection)
       (payload/members [])
       (payload/total-items 0))
      (payload/compact {:context true})))

(defn get-entrypoint
  [args body request]
  (-> (payload/jsonld
       (payload/id {:model api/EntryPoint})
       (payload/type api/EntryPoint)
       (payload/supported-property {:property "hydra:title"
                                    :value "Punto de entrada de la API Diabetes"})

       (payload/supported-link {:property api/personas-link
                                :model (api/vocab "get-personas-link")}))
      (payload/compact)))

(def routes (routing/api-routes
             {:path [""]
              :model api/EntryPoint
              :handlers {:get get-entrypoint}
              :nested [{:path ["personas"]
                        :model (api/vocab "get-personas-link")
                        :handlers {:get get-personas}}]}))

