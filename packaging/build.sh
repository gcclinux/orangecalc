#!/bin/bash
# Orange Calculator Build Script for Unix-like systems

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC_DIR="$PROJECT_ROOT/src"
BIN_DIR="$PROJECT_ROOT/bin"
RES_DIR="$PROJECT_ROOT/res"
MAIN_CLASS="wagemaker.OrangeCalc"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

show_help() {
    echo -e "${GREEN}Orange Calculator Build Script${NC}"
    echo
    echo "Usage:"
    echo "  ./build.sh           - Compile the project"
    echo "  ./build.sh clean     - Clean and compile"
    echo "  ./build.sh run       - Compile and run"
    echo "  ./build.sh clean run - Clean, compile and run"
    echo "  ./build.sh help      - Show this help"
    echo
}

clean_project() {
    echo -e "${YELLOW}Cleaning project...${NC}"
    rm -rf "$BIN_DIR"
    mkdir -p "$BIN_DIR"
    echo -e "${GREEN}Clean complete.${NC}"
}

copy_resources() {
    echo -e "${YELLOW}Copying resources...${NC}"
    if [ -d "$RES_DIR" ]; then
        cp -r "$RES_DIR"/* "$BIN_DIR/" 2>/dev/null
        echo -e "${GREEN}Resources copied to bin directory.${NC}"
    else
        echo -e "${YELLOW}No resources directory found.${NC}"
    fi
}

compile_java() {
    echo -e "${YELLOW}Compiling Java source files...${NC}"
    
    # Check if Java is available
    if ! command -v java &> /dev/null; then
        echo -e "${RED}Error: Java not found in PATH${NC}"
        exit 1
    fi
    
    # Check if javac is available
    if ! command -v javac &> /dev/null; then
        echo -e "${RED}Error: javac not found in PATH${NC}"
        exit 1
    fi
    
    java_version=$(java -version 2>&1 | grep version)
    echo -e "${CYAN}Using Java: $java_version${NC}"
    
    # Find all Java files
    java_files=$(find "$SRC_DIR" -name "*.java" -type f)
    
    if [ -z "$java_files" ]; then
        echo -e "${RED}No Java files found in $SRC_DIR${NC}"
        exit 1
    fi
    
    file_count=$(echo "$java_files" | wc -l)
    echo -e "${CYAN}Found $file_count Java files to compile${NC}"
    
    # Compile with proper classpath and output directory
    if javac -cp "$SRC_DIR" -d "$BIN_DIR" $java_files; then
        echo -e "${GREEN}Compilation successful!${NC}"
    else
        echo -e "${RED}Compilation failed!${NC}"
        exit 1
    fi
}

run_application() {
    echo -e "${YELLOW}Running application...${NC}"
    cd "$BIN_DIR"
    java -cp . "$MAIN_CLASS"
    cd "$PROJECT_ROOT"
}

# Parse arguments
CLEAN_BUILD=false
RUN_APP=false

for arg in "$@"; do
    case $arg in
        clean)
            CLEAN_BUILD=true
            ;;
        run)
            RUN_APP=true
            ;;
        help)
            show_help
            exit 0
            ;;
        *)
            echo -e "${RED}Unknown argument: $arg${NC}"
            show_help
            exit 1
            ;;
    esac
done

# Main execution
echo -e "${GREEN}Orange Calculator Build Script${NC}"
echo -e "${GREEN}==============================${NC}"

if [ "$CLEAN_BUILD" = true ]; then
    clean_project
elif [ ! -d "$BIN_DIR" ]; then
    mkdir -p "$BIN_DIR"
fi

compile_java
copy_resources

if [ "$RUN_APP" = true ]; then
    run_application
fi

echo -e "${GREEN}Build process complete!${NC}"