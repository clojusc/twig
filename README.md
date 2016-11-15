# twig [![Build Status][travis-badge]][travis][![Dependencies Status][deps-badge]][deps][![Clojars Project][clojars-badge]][clojars][![Tag][tag-badge]][tag][![Clojure version][clojure-v]](project.clj)

[![][logo]][logo-large]

*A little logging helper for Clojure projects*


#### Table of Contents

* [Introduction](#introduction-)
* [Usage](#usage-)
* [License](#license-)


## Introduction

This is just a tiny bit of code, but it was starting to get duplicated around a
bunch of projects, so now there's twig.

It's got an XML file for configuring the formatter -- if anyone has figured out
how to do this in code, I'd love a pointer or a PR ;-)


## Usage

Add to your ``project.clj``:

```clj
[clojusc/twig "0.2.6"]
```

Then in a namespace of your choice:

```clj
(ns ...
  (:require [clojusc.twig :as logger])
  ...)
  ```

This will allow you to not only see nicely formatted log output (as configured
in the included ``resources/logback.xml`` file), but also do things like
setting the log level on a per-namespace basis:

```clj
(logger/set-level! '[com.datastax.driver
                     co.paralleluniverse]
                   :info)
(logger/set-level! 'my.project :debug)
```

Note that the level can be any of the levels supported by
``ch.qos.logback.classic Level``
(see the [source code](https://github.com/qos-ch/logback/blob/master/logback-classic/src/main/java/ch/qos/logback/classic/Level.java)).
As of now, those correspond to the following:
``:off``, ``:error``, ``:warn``, ``:info``, ``:debug``, ``:trace``, and
``:all``. You may pass these as keywords, symbols, or strings.

There are, of course, other things you can do besides setting the level:

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

Copyright © 2015, Duncan McGreggor

Copyright © 2016, Clojure-Aided Enrichment Center

Copyright © 2016, Element 84, Inc.

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

<!-- Named page links below: /-->

[travis]: https://travis-ci.org/clojusc/twig
[travis-badge]: https://travis-ci.org/clojusc/twig.png?branch=master
[deps]: http://jarkeeper.com/clojusc/twig
[deps-badge]: http://jarkeeper.com/clojusc/twig/status.svg
[logo]: resources/images/twig-250x.png
[logo-large]: resources/images/twig-1000x.png
[tag-badge]: https://img.shields.io/github/tag/clojusc/twig.svg?maxAge=2592000
[tag]: https://github.com/clojusc/twig/tags
[clojure-v]: https://img.shields.io/badge/clojure-1.8.0-blue.svg
[clojars]: https://clojars.org/clojusc/twig
[clojars-badge]: https://img.shields.io/clojars/v/clojusc/twig.svg


