# Screamy

libnotify and growl notifications from Immutant queues

## Usage

```
$ lein immutant deploy
```

```
$ curl localhost:8080/screamy -d'this is a message'
```


Then publish messages to `queue.notifications`.

Requires `notify-send` or `growlnotify` on your $PATH.

## License

Copyright © 2013 Matthew Lee Hinman

Distributed under the Eclipse Public License, the same as Clojure.
