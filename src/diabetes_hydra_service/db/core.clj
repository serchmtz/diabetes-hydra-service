(ns diabetes-hydra-service.db.core
  (:require
   [monger.conversion :as mconv]
   [monger.core :as mg]
   [monger.collection :as mc]
   [monger.query :as mquery]
   [levanzo.payload :as payload]
   [diabetes-hydra-service.entities.persona :as persona]
   [diabetes-hydra-service.config :as config]
   [levanzo.hydra :as hydra])
  
  (:import org.bson.types.ObjectId))


(def connection-map (mg/connect-via-uri config/db-uri))
(def connection (:conn connection-map))
(def db (:db connection-map))

(def page-size (atom 5))
(defn set-page-size! [size] (reset! page-size size))

(defn jsonld->bson [json]
  (let [compacted (payload/compact json {:context false})]
    (-> compacted
        (clojure.walk/keywordize-keys))))


(defn bson->jsonld [obj]
  (let [res (-> obj
                (mconv/from-db-object false)
                (clojure.walk/stringify-keys))]
    (if (some? res)
      (-> res
          (dissoc "_id")
          ;; (assoc "@context" (payload/context))
          )
      res)))


(defn find-by-id
  [collection id]
  (-> (mc/find-one db collection {"@id" id})
      bson->jsonld))

(defn save
  [collection json-ld]
  (let [id (get json-ld "@id")]
    (mc/update db collection {"@id" id} (jsonld->bson json-ld) {:multi false :upsert true})
    json-ld))

(defn delete-by-id
  [collection id]
  (mc/remove db collection {"@id" id}))

(defn last-id
  [collection]
  (mc/count db collection))

(defn find-all-paged
  ([collection condition page]
   (->> (mquery/with-collection db collection
          (mquery/find condition)
          (mquery/skip (* (dec page) @page-size))
          (mquery/limit @page-size))
        (map bson->jsonld)))
  ([collection page] (find-all-paged collection {} page)))


(defn find-all
  ([collection condition]
   (->> (mc/find db collection condition)
        (map bson->jsonld)))
  ([collection]
   (find-all collection {})))

(defn find-all-ids
  ([collection condition]
   (->> (mc/find db collection condition {"@id" 1 "@type" 1})
        (map bson->jsonld)))
  ([collection]
   (find-all-ids collection {})))

