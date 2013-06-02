(ns screamy.core
  (:require [clojure.java.shell :as sh]
            [immutant.messaging :as msg]))

(def notify-send-cmd "notify-send")
(def growlnotify-cmd "growlnotify")

(defonce notify-enabled?
  (= 0 (:exit (sh/sh "which" "notify-send"))))

(defonce growl-enabled?
  (= 0 (:exit (sh/sh "which" "growlnotify"))))

(defn notify-send
  [body & [summary]]
  (when notify-enabled?
    (sh/sh notify-send-cmd
           "--urgency=normal"
           "--expire-time=5000"
           "--app-name=Screamy"
           "MCP"
           (str body))))

(defn growlnotify
  [body & [summary]]
  (when growl-enabled?
    (sh/sh growlnotify-cmd
           (str body))))

(defn notify
  "Basic notify method"
  [msg & [opts]]
  (println "Notify Message:" msg)
  (cond
   notify-enabled? (notify-send msg)
   growl-enabled? (growlnotify msg)
   :else
   (println "Unable to notify (neither growlnotify nor notify-send found)")))

(defn notify-handler
  "Handler for enqueuing notification messages"
  [request]
  (try
    (msg/publish "queue.notifications" (slurp (:body request)))
    {:status 200
     :body (str {:success true} "\n")
     :headers {"Content-Type" "application/edn"}}
    (catch Throwable e
      {:status 500
       :body (str {:success false :exception (str e)} "\n")
       :headers {"Content-Type" "application/edn"}})))
