# Orange Calculator Makefile

# Project configuration
PROJECT_NAME = orange-calculator
SRC_DIR = src
BIN_DIR = bin
RES_DIR = res
MAIN_CLASS = wagemaker.CalculatorOrangeLite

# Java configuration
JAVA_FILES = $(shell find $(SRC_DIR) -name "*.java" -type f)
CLASS_FILES = $(JAVA_FILES:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

# Default target
.PHONY: all build clean run help

all: build

help:
	@echo "Orange Calculator Build System"
	@echo "=============================="
	@echo ""
	@echo "Available targets:"
	@echo "  build   - Compile the project (default)"
	@echo "  clean   - Clean compiled files"
	@echo "  run     - Compile and run the application"
	@echo "  help    - Show this help"
	@echo ""

# Create bin directory
$(BIN_DIR):
	@echo "Creating bin directory..."
	@mkdir -p $(BIN_DIR)

# Compile Java files
build: $(BIN_DIR) $(CLASS_FILES) copy-resources
	@echo "Build complete!"

$(BIN_DIR)/%.class: $(SRC_DIR)/%.java | $(BIN_DIR)
	@echo "Compiling Java files..."
	@javac -cp $(SRC_DIR) -d $(BIN_DIR) $(JAVA_FILES)

# Copy resources
copy-resources: $(BIN_DIR)
	@echo "Copying resources..."
	@if [ -d "$(RES_DIR)" ]; then \
		cp -r $(RES_DIR)/* $(BIN_DIR)/ 2>/dev/null || true; \
		echo "Resources copied to bin directory."; \
	else \
		echo "No resources directory found."; \
	fi

# Clean compiled files
clean:
	@echo "Cleaning project..."
	@rm -rf $(BIN_DIR)
	@echo "Clean complete."

# Run the application
run: build
	@echo "Running application..."
	@cd $(BIN_DIR) && java -cp . $(MAIN_CLASS)

# Force rebuild
rebuild: clean build

.PHONY: copy-resources rebuild