check-jar-config:
	@lein uberjar
	java -cp target/trifl-$(VERSION)-standalone.jar \
	clojusc.env_ini_test show-example-ini
check:
	@lein with-profile +test test :unit

check-system:
	@lein with-profile +test test :system

check-integration:
	@lein with-profile +test test :integration

check-all: check-deps
	@lein with-profile +test test :all

kibit:
	@lein with-profile +test kibit

base-eastwood:
	@lein with-profile +test eastwood "$(EW_OPTS)"

eastwood:
	@EW_OPTS="{:namespaces [:source-paths]}" make base-eastwood

lint: kibit eastwood

lint-unused:
	@EW_OPTS="{:linters [:unused-fn-args :unused-locals :unused-namespaces :unused-private-vars :wrong-ns-form] :namespaces [:source-paths]}" make base-eastwood

lint-ns:
	@EW_OPTS="{:linters [:unused-namespaces :wrong-ns-form] :namespaces [:source-paths]}" make base-eastwood

check-deps:
	@lein with-profile +test do ancient check :all
