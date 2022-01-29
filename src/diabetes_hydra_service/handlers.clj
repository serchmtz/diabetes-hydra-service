(ns diabetes-hydra-service.handlers
  (:require [diabetes-hydra-service.config :as config]
            [diabetes-hydra-service.db.core :as db]
            [levanzo.payload :as payload]
            [levanzo.hydra :as hydra]))


(payload/context (str config/base "api/context.jsonld"))

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

(defn get-collection-with-ids
  [collection model]
  (fn [args body request]
    (let [coll (db/find-all-ids collection)]
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
          data (-> (db/find-by-id collection id)
                   payload/compact)]
      (or data
        {:status 404
         :body (str "No se encontró el objeto en la colección" collection)}))))

(defn post-entity
  [collection model id-kw]
  (fn [args body request]
    (let [id (inc (db/last-id collection))
          newentity
          (-> body
              (merge
               (make-absolute-path
                (payload/id
                 {:model model
                  :args {id-kw id}}))))]
      (->> newentity
           (db/save collection)
           payload/compact))))

(defn delete-entity
  [collection model]
  (fn [args body request]    
    (let [id (payload/link-for {:model model
                                :args args})
          id (str "/" id)]
        (db/delete-by-id collection id))))

(defn put-entity
  [collection model]
  (fn [args body request]
    (let [id (->> (payload/link-for {:model model :args args})
                  (str "/"))
          newdata (-> (db/find-by-id collection id)
                      payload/compact
                      (payload/merge-jsonld body))]
      (payload/compact (db/save collection newdata)))))
