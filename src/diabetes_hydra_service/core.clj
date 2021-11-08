(ns diabetes-hydra-service.core
  (:require [diabetes-hydra-service.api :as api]
            [diabetes-hydra-service.routes :as routes]
            [diabetes-hydra-service.config :as config]
            [levanzo.http :as http]
            [org.httpkit.server :as http-kit]
            [levanzo.payload :as payload]
            [levanzo.routing :as routing]))

(clojure.spec.alpha/check-asserts true)

(defn cors-enabled [middleware]
  (fn [request]
    (let [response (middleware request)
          headers (:headers response)
          headers (assoc headers "Access-Control-Allow-Origin" "*")
          response (assoc response :headers headers)]
      response)))

(def middleware (http/middleware {:api api/API
                                  :routes routes/routes
                                  :mount-path "/"
                                  :documentation-path "/vocab"}))

(def stop-server-ref (atom nil))

(defn start-api []
  (swap! stop-server-ref
         (fn [_] (http-kit/run-server (cors-enabled middleware) {:port config/port}))))

(defn stop-api [] (when (some? @stop-server-ref) (@stop-server-ref)))


(start-api)
;;(stop-api)
