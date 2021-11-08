(ns diabetes-hydra-service.config
  (:require [levanzo.http :as http]
            [levanzo.payload :as payload]
            [clojure.string :as string]))

(http/set-debug-errors! true)

(def port (or (System/getenv "PORT") 8080))
(def host (or (System/getenv "HOST") "localhost"))
(def base (str "http://" host ":" port "/"))
(payload/context {:vocab (str base "vocab#")
                  :base base})