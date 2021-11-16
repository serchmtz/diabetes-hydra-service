(ns diabetes-hydra-service.config
  (:require [levanzo.http :as http]
            [levanzo.payload :as payload]            
            [clojure.string :as string]))

(def protocol (or (System/getenv "PROTOCOL") "http"))
(def port (Integer/parseInt (or (System/getenv "PORT") "8080")))
(def host (or (System/getenv "HOST") "localhost"))
(def use-port-for-base (Boolean/parseBoolean (or (System/getenv "USE_PORT_FOR_BASE") "true")))
(def base-url (str protocol "://" host  (if use-port-for-base (str ":" port) "") "/"))
(def base (or (System/getenv "BASE_URL") base-url))
(def server-host (str "http://" (or (System/getenv "SERVER_HOST") host)))
