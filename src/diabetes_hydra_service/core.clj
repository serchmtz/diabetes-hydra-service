(ns diabetes-hydra-service.core
  (:require [diabetes-hydra-service.middlewares
             :refer [cors-enabled context-file middleware]]
            [diabetes-hydra-service.config :as config]
            [org.httpkit.server :as http-kit])
  (:gen-class))

(clojure.spec.alpha/check-asserts true)

(def stop-server-ref (atom nil))

(defn start-api []
  (swap! stop-server-ref
         (fn [_]
           (http-kit/run-server
            (-> middleware
                cors-enabled
                context-file)
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

(defn -main [& args]
  (println "Starting Hydra server...")
  (start-api)
  (println "Hydra server started on" config/server-host))
