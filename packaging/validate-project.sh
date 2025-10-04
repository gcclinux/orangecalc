#!/bin/bash
# Orange Calculator Project Validation Script

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

echo -e "${GREEN}Orange Calculator Project Validation${NC}"
echo -e "${GREEN}====================================${NC}"

# Check required directories
echo -e "${CYAN}Checking directory structure...${NC}"
REQUIRED_DIRS=(
    "src/wagemaker"
    "res/images"
    "res/audio"
    "packaging"
)

for dir in "${REQUIRED_DIRS[@]}"; do
    if [ -d "$PROJECT_ROOT/$dir" ]; then
        echo -e "${GREEN}✓${NC} $dir"
    else
        echo -e "${RED}✗${NC} $dir (missing)"
    fi
done

# Check required source files
echo -e "\n${CYAN}Checking Java source files...${NC}"
REQUIRED_JAVA_FILES=(
    "src/wagemaker/About.java"
    "src/wagemaker/BorderCompoundAction.java"
    "src/wagemaker/BorderLowerAction.java"
    "src/wagemaker/CalcProperties.java"
    "src/wagemaker/ClearHistAction.java"
    "src/wagemaker/DarkGrayAction.java"
    "src/wagemaker/DisplayReport.java"
    "src/wagemaker/FontTheme.java"
    "src/wagemaker/HistAction.java"
    "src/wagemaker/ImageLoader.java"
    "src/wagemaker/LimeAction.java"
    "src/wagemaker/NoBorderAction.java"
    "src/wagemaker/OrangeAction.java"
    "src/wagemaker/OrangeCalc.java"
    "src/wagemaker/PageLoader.java"
    "src/wagemaker/PlainAction.java"
    "src/wagemaker/PurpleAction.java"
    "src/wagemaker/Sound.java"
    "src/wagemaker/SoundControl.java"
)

for file in "${REQUIRED_JAVA_FILES[@]}"; do
    if [ -f "$PROJECT_ROOT/$file" ]; then
        echo -e "${GREEN}✓${NC} $file"
    else
        echo -e "${RED}✗${NC} $file (missing)"
    fi
done

# Check required resource files
echo -e "\n${CYAN}Checking resource files...${NC}"
REQUIRED_RESOURCES=(
    "res/images/calculator.png"
    "res/images/calculator.ico"
    "res/images/gcclinux.png"
    "res/images/orange.png"
    "res/images/lime.png"
    "res/images/star.gif"
    "res/audio/typewriter.wav"
)

for file in "${REQUIRED_RESOURCES[@]}"; do
    if [ -f "$PROJECT_ROOT/$file" ]; then
        echo -e "${GREEN}✓${NC} $file"
    else
        echo -e "${RED}✗${NC} $file (missing)"
    fi
done

# Check packaging files
echo -e "\n${CYAN}Checking packaging files...${NC}"
PACKAGING_FILES=(
    "packaging/build.sh"
    "packaging/orangecalc.spec"
    "packaging/orangecalc.desktop"
    "README.md"
    "LICENSE"
)

for file in "${PACKAGING_FILES[@]}"; do
    if [ -f "$PROJECT_ROOT/$file" ]; then
        echo -e "${GREEN}✓${NC} $file"
    else
        echo -e "${RED}✗${NC} $file (missing)"
    fi
done

# Check main class
echo -e "\n${CYAN}Checking main class...${NC}"
if grep -q "public class OrangeCalc" "$PROJECT_ROOT/src/wagemaker/OrangeCalc.java" 2>/dev/null; then
    echo -e "${GREEN}✓${NC} Main class OrangeCalc found"
else
    echo -e "${RED}✗${NC} Main class OrangeCalc not found"
fi

if grep -q "public static void main" "$PROJECT_ROOT/src/wagemaker/OrangeCalc.java" 2>/dev/null; then
    echo -e "${GREEN}✓${NC} Main method found"
else
    echo -e "${RED}✗${NC} Main method not found"
fi

# Check version consistency
echo -e "\n${CYAN}Checking version consistency...${NC}"
if [ -f "$PROJECT_ROOT/src/wagemaker/CalcProperties.java" ]; then
    VERSION=$(grep 'Version = ' "$PROJECT_ROOT/src/wagemaker/CalcProperties.java" | sed 's/.*Version = "\([^"]*\)".*/\1/')
    echo -e "${GREEN}✓${NC} CalcProperties version: $VERSION"
    
    if [ -f "$PROJECT_ROOT/packaging/orangecalc.spec" ]; then
        SPEC_VERSION=$(grep '^Version:' "$PROJECT_ROOT/packaging/orangecalc.spec" | awk '{print $2}')
        if [ "$VERSION" = "$SPEC_VERSION" ]; then
            echo -e "${GREEN}✓${NC} RPM spec version matches: $SPEC_VERSION"
        else
            echo -e "${RED}✗${NC} RPM spec version mismatch: $SPEC_VERSION (should be $VERSION)"
        fi
    fi
fi

# Check compilation
echo -e "\n${CYAN}Testing compilation...${NC}"
if command -v javac &> /dev/null; then
    cd "$PROJECT_ROOT"
    if javac -cp src -d /tmp/orangecalc-test src/wagemaker/*.java 2>/dev/null; then
        echo -e "${GREEN}✓${NC} Java compilation successful"
        rm -rf /tmp/orangecalc-test
    else
        echo -e "${RED}✗${NC} Java compilation failed"
    fi
else
    echo -e "${YELLOW}!${NC} javac not found, skipping compilation test"
fi

# Check desktop file validity
echo -e "\n${CYAN}Checking desktop file...${NC}"
if [ -f "$PROJECT_ROOT/packaging/orangecalc.desktop" ]; then
    if command -v desktop-file-validate &> /dev/null; then
        if desktop-file-validate "$PROJECT_ROOT/packaging/orangecalc.desktop" 2>/dev/null; then
            echo -e "${GREEN}✓${NC} Desktop file is valid"
        else
            echo -e "${RED}✗${NC} Desktop file validation failed"
        fi
    else
        echo -e "${YELLOW}!${NC} desktop-file-validate not found, skipping validation"
    fi
fi

echo -e "\n${GREEN}Validation complete!${NC}"