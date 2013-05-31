(ns immutant.init
  (:require [screamy.core]
            [immutant.messaging :as msg]))

;; This file will be loaded when the application is deployed to Immutant, and
;; can be used to start services your app needs. Examples:

;; Messaging allows for starting (and stopping) destinations (queues & topics)
;; and listening for messages on a destination.

(msg/start "queue.notifications")
(msg/listen "queue.notifications" screamy.core/notify)
