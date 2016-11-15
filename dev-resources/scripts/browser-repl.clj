(require
  '[cljs.repl :as repl]
  '[cljs.repl.browser :as browser])

(repl/repl* (browser/repl-env)
  {:output-dir "resources/public/js"
   :optimizations :none
   :cache-analysis true
   :source-map true})
