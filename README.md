# twig

[![][twig-logo]][twig-logo-large]

[twig-logo]: resources/images/twig-250x.png
[twig-logo-large]: resources/images/twig-1000x.png

*A little logging helper for Clojure projects*


#### Table of Contents

* [Introduction](#introduction-)
* [Usage](#usage-)
* [License](#license-)


## Introduction

This is just a tiny bit of code, but it was starting to get duplicated around a bunch of projects, so now there's twig.

It's got an XML file for configuring the formatter -- if anyone has figured out how to do this in code, I'd love a pointer or a PR ;-)


## Usage

Add to your ``project.clj``:

```clj
[twig "0.1.0-dev"]
```

Then in a namespace of your choice:

```clj
(ns ...
  (:require [twig.core :as logger])
  ...)
  ```

This will allow you to not only see nicely formatted log output (as configured in the included ``resources/logback.xml`` file), but also do things like setting the log level on a per-namespace basis:

```clj
(logger/set-level! [com.datastax.driver
                    co.paralleluniverse]
                   :debug)
```

There are, of course, other things you can do:

```clj
(require [clojure.tools.logging :as log])

(log/debug "Logging agent:" log/*logging-agent*)
(log/debug "Logging factory:" (logger/get-factory))
(log/debug "Logging factory name:" (logger/get-factory-name))
(log/debug "Logger:" (logger/get-logger *ns*))
(log/debug "Logger name:" (logger/get-logger-name *ns*))
(log/debug "Logger level:" (logger/get-logger-level *ns*))
(log/debug "Logger context:" (logger/get-logger-context *ns*))
(log/debug "Logger configurator:" (logger/get-config *ns*))
```

## License

Copyright © 2015-2016, Duncan McGreggor

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
