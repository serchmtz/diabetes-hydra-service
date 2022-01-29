(ns diabetes-hydra-service.routes
  (:require [levanzo.routing :as routing]
            [levanzo.payload :as payload]
            [levanzo.hydra :as hydra]
            [diabetes-hydra-service.entities.entrypoint :as entrypoint]
            [diabetes-hydra-service.entities.persona :as persona]
            [diabetes-hydra-service.entities.estados :as estados]
            [diabetes-hydra-service.entities.datos :as datos]
            [diabetes-hydra-service.handlers :refer :all]))

(defn get-entrypoint
  [args body request]
  (-> (payload/jsonld
       (make-absolute-path
        (payload/id {:model entrypoint/EntryPoint}))
       (payload/type entrypoint/EntryPoint)
       (payload/supported-property {:property "hydra:title"
                                    :value "Punto de entrada de la API Diabetes"})
       
       (-> (payload/supported-link {:property entrypoint/medicos-link
                                    :model persona/MedicoCollection})
           (update (hydra/id entrypoint/medicos-link) make-absolute-path))

       (-> (payload/supported-link {:property entrypoint/pacientes-link
                                    :model persona/PacienteCollection})
           (update (hydra/id entrypoint/pacientes-link) make-absolute-path))

       (-> (payload/supported-link {:property entrypoint/localizaciones-link
                                    :model estados/LocalizacionGeograficaCollection})
           (update (hydra/id entrypoint/localizaciones-link) make-absolute-path))
       (-> (payload/supported-link {:property entrypoint/expedientes-link
                                    :model datos/ExpedienteClinicoCollection})
           (update (hydra/id entrypoint/expedientes-link) make-absolute-path))

       (-> (payload/supported-link {:property entrypoint/historias-link
                                    :model datos/HistoriaClinicaCollection})
           (update (hydra/id entrypoint/historias-link) make-absolute-path))

       (-> (payload/supported-link {:property entrypoint/notas-link
                                    :model datos/NotaMedicaCollection})
           (update (hydra/id entrypoint/notas-link) make-absolute-path)))
      
      payload/compact))

(def routes (routing/api-routes
             {:path "api"
              :model entrypoint/EntryPoint
              :handlers {:get get-entrypoint}
              :nested [{:path "/"
                        :model entrypoint/EntryPoint
                        :handlers {:get get-entrypoint}}
                       {:path ["/" "medicos"]
                        :model persona/MedicoCollection
                        :handlers {:get (get-collection-with-ids "medicos" persona/MedicoCollection)
                                   :post (post-entity "medicos" persona/Medico :medico-id)}
                        :nested [{:path ["/" :medico-id]
                                  :model persona/Medico
                                  :handlers {:get (get-entity "medicos" persona/Medico)
                                             :delete (delete-entity "medicos" persona/Medico)
                                             :put (put-entity "medicos" persona/Medico)}}]}

                       {:path ["/" "pacientes"]
                        :model persona/PacienteCollection
                        :handlers {:get (get-collection-with-ids "pacientes" persona/PacienteCollection)
                                   :post (post-entity "pacientes" persona/Paciente :paciente-id)}
                        :nested [{:path ["/" :paciente-id]
                                  :model persona/Paciente
                                  :handlers {:get (get-entity "pacientes" persona/Paciente)
                                             :delete (delete-entity "pacientes" persona/Paciente)
                                             :put (put-entity "pacientes" persona/Paciente)}}]}


                       {:path ["/" "expedientes"]
                        :model datos/ExpedienteClinicoCollection
                        :handlers {:get (get-collection-with-ids "expedientes" datos/ExpedienteClinicoCollection)
                                   :post (post-entity "expedientes" datos/ExpedienteClinico :expediente-id)}
                        :nested [{:path ["/" :expediente-id]
                                  :model datos/ExpedienteClinico
                                  :handlers {:get (get-entity "expedientes" datos/ExpedienteClinico)
                                             :delete (delete-entity "expedientes" datos/ExpedienteClinico)
                                             :put (put-entity "expedientes" datos/ExpedienteClinico)}}]}

                       {:path ["/" "historias"]
                        :model datos/HistoriaClinicaCollection
                        :handlers {:get (get-collection-with-ids "historias" datos/HistoriaClinicaCollection)
                                   :post (post-entity "historias" datos/HistoriaClinica :historia-id)}
                        :nested [{:path ["/" :historia-id]
                                  :model datos/HistoriaClinica
                                  :handlers {:get (get-entity "historias" datos/HistoriaClinica)
                                             :delete (delete-entity "historias" datos/HistoriaClinica)
                                             :put (put-entity "historias" datos/HistoriaClinica)}}]}
                       {:path ["/" "notas"]
                        :model datos/NotaMedicaCollection
                        :handlers {:get (get-collection-with-ids "notas" datos/NotaMedicaCollection)
                                   :post (post-entity "notas" datos/NotaMedica :nota-id)}
                        :nested [{:path ["/" :nota-id]
                                  :model datos/NotaMedica
                                  :handlers {:get (get-entity "notas" datos/NotaMedica)
                                             :delete (delete-entity "notas" datos/NotaMedica)
                                             :put (put-entity "notas" datos/NotaMedica)}}]}
                       
                       {:path ["/" "localizaciones"]
                        :model estados/LocalizacionGeograficaCollection
                        :handlers {:get (get-collection-with-ids "localizaciones" estados/LocalizacionGeograficaCollection)
                                   :post (post-entity "localizaciones" estados/LocalizacionGeografica :localizacion-id)}
                        :nested [{:path ["/" :localizacion-id]
                                  :model estados/LocalizacionGeografica
                                  :handlers {:get (get-entity "localizaciones" estados/LocalizacionGeografica)
                                             :delete (delete-entity "localizaciones" estados/LocalizacionGeografica)
                                             :put (put-entity "localizaciones" estados/LocalizacionGeografica)}}]}]}))

