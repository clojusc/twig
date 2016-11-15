(require
  '[cljs.repl :as repl]
  '[cljs.repl.rhino :as rhino])

(repl/repl* (rhino/repl-env)
  {:output-dir "resources/public/js"
   :optimizations :none
   :cache-analysis true
   :source-map true})
