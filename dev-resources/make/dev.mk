repl: deps clj
	@rlwrap \
		--command-name=clojure \
		--prompt-colour=yellow \
		lein repl

node-repl: deps node
	@rlwrap \
		--command-name=cljs-node \
		--prompt-colour=yellow \
		--substitute-prompt="clojusc.twig.dev.nodejs=> " \
		--only-cook=".*=>" \
		lein node-repl

rhino-repl: deps cljs
	@rlwrap \
		--command-name=cljs-rhino \
		--prompt-colour=yellow \
		--substitute-prompt="clojusc.twig.dev.rhino=> " \
		--only-cook=".*=>" \
		lein rhino-repl
