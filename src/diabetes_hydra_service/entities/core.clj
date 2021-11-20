(ns diabetes-hydra-service.entities.core
  (:require [levanzo.hydra :as hydra]
            [levanzo.utils :refer [conformant]]
            [clojure.spec.alpha :as s]))

(def ^:dynamic *supported-classes-registry* (atom {}))

(s/def ::register-class-args (s/cat :arg (s/or :class ::hydra/SupportedClass
                                               :collection ::hydra/Collection)))

(s/fdef register-class
  :args ::register-class-args
  :ret (s/map-of ::hydra/id ::hydra/SupportedClass))

(defn register-class
  [cls]
  (conformant
   #'register-class
   ::register-class-args [cls]
   (swap! *supported-classes-registry* assoc (hydra/id cls) cls)))

(defn unregister-class
  [cls]
  (conformant
   #'unregister-class
   ::register-class-args [cls]
   (swap! *supported-classes-registry* dissoc (hydra/id cls))))

(defn supported-classes
  []
  (-> @*supported-classes-registry*
      vals
      vec))


(defn clear-registered-classes
  []
  (reset! *supported-classes-registry* {}))

