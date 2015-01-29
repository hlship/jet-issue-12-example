(ns server
  (:require logging-setup
            [qbits.jet.server :as jet]
            [clojure.core.async :refer [thread]]
            [ring.util.response :as r]
            [ring.middleware.format :as f]
            [clojure.tools.logging :as l]))

(defn core-handler [request]
  (l/info "async-handler" (:request-method request) (:uri request))
  (r/response (select-keys request [:request-method :uri])))


(defn wrap-async
  [handler]
  (fn [request]
    (thread (handler request))))

;; Test with:
;;   ab -c 20 -k -t 10 localhost:8080/hello

(defn launch
  []
  (let [handler (-> core-handler
                    (f/wrap-restful-format :formats [:json :edn])
                    wrap-async)]
    (jet/run-jetty {:join?        false
                    :port         8080
                    :ring-handler handler})))