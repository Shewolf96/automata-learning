

.PHONY: run install

install:
	./scripts/install.sh

run: target/Automata-learning-1.0-SNAPSHOT.jar
	./scripts/run.sh $(PATH)

target/Automata-learning-1.0-SNAPSHOT.jar: src/main/java/**/*.java
	./scripts/compile.sh
