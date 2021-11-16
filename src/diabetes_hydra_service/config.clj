(ns diabetes-hydra-service.config
  (:require [levanzo.http :as http]
            [levanzo.payload :as payload]            
            [clojure.string :as string]))

(def protocol (or (System/getenv "PROTOCOL") "http"))
(def port (Integer/parseInt (or (System/getenv "PORT") "8080")))
(def host (or (System/getenv "HOST") "localhost"))
(def base (str protocol "://" host ":" port "/"))
(def server-host (or (System/getenv "SERVER_HOST") base))
