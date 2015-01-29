(defproject example/jet-issue-12 "0.0.1"

            :main main

            :dependencies [[org.clojure/clojure "1.7.0-alpha5"]

                           [org.clojure/core.async "0.1.346.0-17112a-alpha"]

                           [cc.qbits/jet "0.5.4"]

                           [ring/ring-core "1.3.2"]

                           [org.clojure/tools.logging "0.3.1"]
                           [org.slf4j/slf4j-api "1.7.6"]
                           [ch.qos.logback/logback-classic "1.1.1"]

                           [io.aviso/toolchest "0.1.1"]
                           [io.aviso/pretty "0.1.14"]
                           [io.aviso/rook "0.1.23"]
                           [com.cognitect/transit-clj "0.8.259"]])
