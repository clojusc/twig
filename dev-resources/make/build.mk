all: clj jar cljs

clj:
	lein compile

deps:
	lein deps

jar:
	lein jar

uberjar:
	lein uberjar

cljs:
	lein cljsbuild once twig

node:
	lein cljsbuild once node

clean:
	lein clean
	rm -rf .repl-* pom.xml*

travis: clean clj uberjar node cljs check
