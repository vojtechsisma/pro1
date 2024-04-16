MVN = mvn
MAIN_CLASS = Main

.PHONY: all clean build test run javadoc

all: build

build:
	$(MVN) clean compile

test:
	$(MVN) test

run:
	$(MVN) exec:java -Dexec.mainClass="$(MAIN_CLASS)"

javadoc:
	$(MVN) javadoc:javadoc

clean:
	$(MVN) clean
