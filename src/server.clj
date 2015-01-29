(ns server
  (:require logging-setup
            [qbits.jet.server :as jet]
            [clojure.core.async :refer [thread]]
            [io.aviso.rook.async :as a]
            [io.aviso.rook.server :as s]
            [ring.util.response :as r]
            [clojure.tools.logging :as l]))

(defn core-handler [request]
  (l/info "core-handler" (:request-method request) (:uri request))
  (r/response (select-keys request [:request-method :uri])))

;; Test with:
;;   ab -c 20 -k -t 10 localhost:8080/hello

(defn launch
  []
  (let [handler (-> core-handler
                    a/ring-handler->async-handler
                    (s/wrap-with-timeout 100)
                    a/wrap-with-standard-middleware)]
    (jet/run-jetty {:join?        false
                    :port         8080
                    :ring-handler handler})))