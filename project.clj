(defproject diabetes-hydra-service "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :plugins [[reifyhealth/lein-git-down "0.4.0"]]
  :repositories [["internal" "file:///home/sergio/mavenrepo"]
                 ["public-github" {:url "git://github.com"}]]
  :min-lein-version "2.9.1"
  :middleware [lein-git-down.plugin/inject-properties]
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [uk.org.russet/tawny-owl "2.3.0"
                  :exclusions
                  [com.fasterxml.jackson.core/jackson-core
                   com.fasterxml.jackson.core/jackson-databind
                   com.fasterxml.jackson.core/jackson-annotations
                   org.slf4j/slf4j-api
                   org.slf4j/jcl-over-slf4j
                   org.apache.httpcomponents/httpclient
                   org.apache.httpcomponents/httpclient-cache
                   commons-logging]]
                 [levanzo "0.2.4"]
                 [com.novemberain/monger "3.5.0"]
                 ;; [serchmtz/levanzo "5150d157b25baf47659424791f5d7fd4d486c24b"]
                 [http-kit "2.5.3"]]
  :main diabetes-hydra-service.core
  :uberjar-name "diabetes-hydra-service.jar"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[ring/ring-devel "1.9.4"]]}}
  :repl-options {:init-ns diabetes-hydra-service.core})


