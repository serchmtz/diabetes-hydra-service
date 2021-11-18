(ns diabetes-hydra-service.db
  (:require [monger.core :as mg]
            [diabetes-hydra-service.config :as config]))


(def connection-map (mg/connect-via-uri config/db-uri))
(def connection (:conn connection-map))
(def db (:db connection-map))


