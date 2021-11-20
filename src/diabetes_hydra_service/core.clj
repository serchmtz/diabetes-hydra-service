(ns diabetes-hydra-service.core
  (:require [diabetes-hydra-service.middlewares
             :refer [cors-enabled context-file middleware]]
            [diabetes-hydra-service.config :as config]
            [ring.middleware.reload :refer [wrap-reload]]
            [org.httpkit.server :as http-kit])
  (:gen-class))

(clojure.spec.alpha/check-asserts true)

(def stop-server-ref (atom nil))

(def app
  (-> middleware
      cors-enabled
      context-file))

(defn start-api []
  (swap! stop-server-ref
         (fn [_]
           (http-kit/run-server
            #'app
            {:port config/port}))))

(defn start-api-dev []
  (swap! stop-server-ref
         (fn [_]
           (http-kit/run-server
            (wrap-reload #'app)
            {:port config/port}))))

(defn stop-api
  []
  (when (some? @stop-server-ref)
    (@stop-server-ref)))

(.addShutdownHook
 (Runtime/getRuntime)
 (Thread.
  (fn []
    (println "Stopping the Hydra server...")
    (stop-api)
    (println "Hydra server stopped."))))

(defn -dev-main [& args]
  (println "Starting Hydra server in dev mode...")
  (start-api-dev)
  (println "Hydra server started on" config/base))

(defn -main [& args]
  (println "Starting Hydra server...")
  (start-api)
  (println "Hydra server started on" config/base))
