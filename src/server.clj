(ns server
  (:require logging-setup
            [qbits.jet.server :as jet]
            [clojure.core.async :refer [thread]]
            [ring.util.response :as r]
            [clojure.tools.logging :as l]))

(defn async-handler
  [request]
  (thread
    (l/info "async-handler" (:request-method request) (:uri request))
    (r/response "OK")))

;; Test with:
;;   ab -c 20 -k -t 10 localhost:8080/hello

(defn launch
  []
  (jet/run-jetty {:join? false
                  :port 8080
                  :ring-handler async-handler}))