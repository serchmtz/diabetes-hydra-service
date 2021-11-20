(ns diabetes-hydra-service.utils)

(defn load-files
  "Loads all the files in path dir."
  [path]
  (let [file  (java.io.File. path)
        files (.listFiles file)]
  (doseq [x files]
    (when (.isFile x)
      (load-file (.getCanonicalPath x))))))
