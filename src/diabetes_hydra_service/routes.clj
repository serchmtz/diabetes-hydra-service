(ns diabetes-hydra-service.routes
  (:require [diabetes-hydra-service.entities.entrypoint :as entrypoint]
            [diabetes-hydra-service.entities.persona :as persona]
            [diabetes-hydra-service.config :as config]
            [diabetes-hydra-service.db.core :as db]
            [levanzo.routing :as routing]
            [levanzo.payload :as payload]
            [diabetes-hydra-service.namespaces :as dns]
            [levanzo.hydra :as hydra]))

(payload/context (str config/base "context.jsonld"))

(defn make-absolute-path
  [m]
  (let [path (get m "@id")
        path (clojure.string/replace path  #"^([/]*)(.*)" "/$2")]
    (assoc m "@id" path)))


(defn get-collection
  [collection model]
  (fn [args body request]
    (let [coll (db/find-all collection)]
    (-> (payload/instance
         model
         (make-absolute-path
          (payload/id {:model model}))
         (payload/members coll)
         (payload/total-items (count coll)))
        payload/compact))))

(defn get-entity
  [collection model]
  (fn [args body request]
    (let [id (-> (payload/id
                  {:model model
                   :args args})
                 make-absolute-path
                 (get "@id"))
          data (db/find-by-id collection id)]
      (if data
        {:status 200
         :body data}
        {:status 404
         :body (str "No se encontró el objeto en la colección" collection)}))))

(defn post-entity
  [collection model id-kw]
  (fn [args body request]
    (let [id (inc (db/last-id collection))
          newentity (-> body
                         (merge
                          (make-absolute-path
                           (payload/id
                            {:model model
                             :args {id-kw id}}))))]
      (db/save collection newentity)
      (payload/compact newentity))))

(defn delete-entity
  [collection model]
  (fn [args body request]    
    (let [id (payload/link-for {:model model
                                :args args})
          id (str "/" id)]
        (db/delete-by-id collection id))))

(defn get-personas
  [args body request]
  (let [personas (db/find-all "personas")]
    (-> (payload/instance
         persona/PersonaCollection
         (make-absolute-path
          (payload/id {:model persona/PersonaCollection}))
         (payload/members personas)
         (payload/total-items (count personas)))
        payload/compact)))


(defn get-persona
  [args body request]
  (let [id (-> (payload/id
                {:model persona/Persona
                 :args args})
               make-absolute-path
               (get "@id"))]
    (or (db/find-by-id "personas" id) {:status 404 :body "No se encontró a la persona"})))

(defn post-persona
  [args body request]
  (clojure.pprint/pprint body)
  (let [id (inc (db/last-id "personas"))
        newpersona (-> body
                       (merge
                        (make-absolute-path
                         (payload/id
                          {:model persona/Persona
                           :args {:persona-id id}}))))]
    (db/save "personas" newpersona)
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
      payload/compact))

(def routes (routing/api-routes
             {:path "api"
              :model entrypoint/EntryPoint
              :handlers {:get get-entrypoint}
              :nested [{:path ["/" "personas"]
                        :model persona/PersonaCollection
                        :handlers {:get (get-collection "personas" persona/PersonaCollection)
                                   :post (post-entity "personas" persona/Persona :persona-id)}
                        :nested [{:path ["/" :persona-id]
                                  :model persona/Persona
                                  :handlers {:get (get-entity "personas" persona/Persona)
                                             :delete (delete-entity "personas" persona/Persona)}}]}]}))

