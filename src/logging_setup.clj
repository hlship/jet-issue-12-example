(ns logging-setup
  (:require
    [clojure.tools.logging :as l]
    [io.aviso.exception :as e]
    [io.aviso.repl :as repl]
    [io.aviso.writer :as writer]
    [io.aviso.toolchest.exceptions :refer [to-message]]))

(alter-var-root
  #'l/log*
  (fn [default-impl]
    (fn [logger level throwable message]
      (default-impl logger
                    level
                    nil
                    (if throwable
                      (str message
                           writer/eol
                           (e/format-exception throwable {:filter repl/standard-frame-filter}))
                      message)))))

(Thread/setDefaultUncaughtExceptionHandler
  (reify Thread$UncaughtExceptionHandler
    (uncaughtException [_ _ t]
      (l/error t (to-message t)))))