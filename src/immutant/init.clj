(ns immutant.init
  (:require [screamy.core]
            [immutant.messaging :as msg]
            [immutant.web :as web]))

(def notify-queue "queue.notifications")

;; Create and notify on notification queue
(msg/start notify-queue)
(msg/listen notify-queue screamy.core/notify)

;; Set up HTTP notification handler
(web/start "/"
           screamy.core/notify-handler
           :init
           #(msg/publish notify-queue "Initialized screamy notifications"))
