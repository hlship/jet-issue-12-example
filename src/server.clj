(ns server
  (:require logging-setup
            [qbits.jet.server :as jet]
            [clojure.core.async :refer [thread <!]]
            [io.aviso.rook.async :as a]
            [io.aviso.rook.server :as s]
            [ring.util.response :as r]
            [clojure.tools.logging :as l]))

(defn core-handler [request]
  (l/info "core-handler" (:request-method request) (:uri request))
  (Thread/sleep 10)
  ;; Build a medium-sized buffer (I suspect that too small a response will not trigger the issue).
  (-> (range 1 2000)
      r/response))

;; Test with:
;;   http localhost:8080/hello --timeout .1
;;
;; That's httpie -- http://httpie.org

(defn launch
  []
  (let [handler (-> core-handler
                    a/ring-handler->async-handler
                    a/wrap-with-standard-middleware)]
    (jet/run-jetty {:join?        false
                    :port         8080
                    :ring-handler handler})))