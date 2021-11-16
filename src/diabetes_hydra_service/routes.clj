(ns diabetes-hydra-service.routes
  (:require [diabetes-hydra-service.entities.entrypoint :as entrypoint]
            [diabetes-hydra-service.entities.persona :as persona]
            [diabetes-hydra-service.config :as config]
            [levanzo.routing :as routing]
            [levanzo.payload :as payload]
            [levanzo.hydra :as hydra]))

(payload/context (str config/base "context.jsonld"))
(def persona-db (atom {}))

(defn make-absolute-path
  [m]
  (let [path (get m "@id")
        path (clojure.string/replace path  #"^([/]*)(.*)" "/$2")]
    (assoc m "@id" path)))


(clojure.string/replace "s/s/as" #"^([/]*)(.*)" "/$2")
(defn get-personas
  [args body request]
  (-> (payload/instance
       persona/PersonaCollection
       (make-absolute-path
        (payload/id {:model persona/PersonaCollection}))
       (payload/members (vals @persona-db)))
      payload/compact))


(defn get-persona
  [args body request]
  (let [persona (get @persona-db
                     (make-absolute-path
                      (payload/id
                       {:model persona/Persona
                        :args args})))]
    (or persona {:status 404 :body "No se encontró a la persona"})))

(defn post-persona
  [args body request]
  (let [id (inc (count @persona-db))
        newpersona (-> body
                       (merge
                        (make-absolute-path
                         (payload/id
                          {:model persona/Persona
                           :args {:persona-id id}}))))]
    (swap! persona-db #(assoc % (get newpersona "@id") (payload/expand newpersona)))
    (payload/compact newpersona)))


(defn get-entrypoint
  [args body request]
  (-> (payload/jsonld
       (make-absolute-path
        (payload/id {:model entrypoint/EntryPoint}))
       (payload/type entrypoint/EntryPoint)
       (payload/supported-property {:property "hydra:title"
                                    :value "Punto de entrada de la API Diabetes"})
       (payload/supported-link {:property entrypoint/personas-link
                                :model persona/PersonaCollection}))

       ;; (payload/supported-link {:property api/vocab-personas-link
       ;;                          :model (api/vocab "PersonaCollection")}))
      payload/compact))

(def routes (routing/api-routes
             {:path "api"
              :model entrypoint/EntryPoint
              :handlers {:get get-entrypoint}
              :nested [{:path ["/" "personas"]
                        :model persona/PersonaCollection
                        :handlers {:get get-personas
                                   :post post-persona}
                        :nested [{:path ["/" :persona-id]
                                  :model persona/Persona
                                  :handlers {:get get-persona}}]}]}))

