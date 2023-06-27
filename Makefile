BUILD_DIR := ./build
# SRC_DIR := ./src
SRC_DIR := ./src/main/java

SRCS := $(shell find $(SRC_DIR) -name '*.java')
CLASSES := $(SRCS:$(SRC_DIR)/%.java=$(BUILD_DIR)/%.class)

.PHONY: all clean checkstyle spotbugs run

all: $(CLASSES) | spotbugs

spotbugs:
	@echo "Running Spotbugs . . ."
	@echo -n "\033[31m"
	@java -jar ./tools/spotbugs-4.7.3/lib/spotbugs.jar -textui $(CLASSES)
	@echo -n "\033[0m"

checkstyle:
	@echo "Running Checkstyle . . ."
	@echo -n "\033[31m"
	@java -jar ./tools/checkstyle-10.10.0-all.jar -c ./tools/config/google_checks.xml $(SRCS) | grep "[WARN]"
	@echo -n "\033[0m"
	@echo -n "\033[1A"
	
# ATTENZIONE
# builda sempre tutto
$(CLASSES): $(SRCS) | checkstyle
	@echo "Compiling source code . . ."
	@javac $^ -d $(BUILD_DIR)

clean:
	@echo -n "\033[31m"
	@rm -rvf $(BUILD_DIR)
	@echo -n "\033[0m"

run:
	@java -cp ./build dijkstra.dollhouse.GameLauncher