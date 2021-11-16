(ns diabetes-hydra-service.config
  (:require [levanzo.http :as http]
            [levanzo.payload :as payload]
            [clojure.string :as string]))


(def port (Integer/parseInt (or (System/getenv "SERVER_PORT") "8080")))
(def host (or (System/getenv "SERVER_HOST") "localhost"))
(def base (str "http://" host ":" port "/"))
