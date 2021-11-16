(ns diabetes-hydra-service.middlewares
  (:require [diabetes-hydra-service.api :as api]
            [diabetes-hydra-service.namespaces :refer [vocab]]
            [diabetes-hydra-service.routes :as routes]
            [diabetes-hydra-service.config :as config]
            [levanzo.http :as http]
            [levanzo.routing :as routing]
            [levanzo.namespaces :as lns]
            [cheshire.core :as json]))

(defn cors-enabled [middleware]
  (fn [request]
    (let [response (middleware request)
          headers (:headers response)
          headers (assoc headers "Access-Control-Allow-Origin" "*")
          headers (assoc headers "Access-Control-Allow-Credentials" "true")
          headers (assoc headers "Access-Control-Allow-Headers" "*")
          headers (assoc headers "Access-Control-Allow-Methods" "GET, POST, PUT, DELETE, OPTIONS, PATCH")
          headers (assoc headers "Access-Control-Expose-Headers" "Link")
          response (assoc response :headers headers)]
      response)))

(defn context-file [middleware]
  (fn [request]
    (let [uri (:uri request)
          response (middleware request)
          headers (:headers response)
          headers (assoc headers "Content-Type" "application/ld+json")
          response (assoc response :status 200)          
          response (assoc response :headers headers)
          body (json/generate-string {"@context" ["https://www.w3.org/ns/hydra/core"
                                                  (merge
                                                   @lns/*ns-register*
                                                   {"@base" config/base
                                                    "@vocab" (vocab)
                                                    "personas" {"@id" (vocab "personas")
                                                                "@type" "@id"}})]})]
      (if (= uri "/context.jsonld")
        (assoc response :body body)
        response))))

(def middleware (http/middleware {:api api/API
                                  :routes routes/routes
                                  :mount-path "/"
                                  :documentation-path "/api/vocab"}))
