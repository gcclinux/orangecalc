#!/bin/bash
# Orange Calculator RPM Build Script

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
VERSION="1.5.6"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

show_help() {
    echo -e "${GREEN}Orange Calculator RPM Build Script${NC}"
    echo
    echo "Usage:"
    echo "  ./build-rpm.sh           - Build RPM package"
    echo "  ./build-rpm.sh clean     - Clean and build RPM package"
    echo "  ./build-rpm.sh tar       - Create source tarball only"
    echo "  ./build-rpm.sh clean tar - Clean and create source tarball only"
    echo "  ./build-rpm.sh help      - Show this help"
    echo
    echo "Clean operations:"
    echo "  - Removes existing RPM packages"
    echo "  - Removes existing source tarballs"
    echo "  - Cleans RPM build directories"
    echo "  - Cleans local build artifacts (bin/, dist/)"
    echo
}

clean_build_artifacts() {
    echo -e "${YELLOW}Cleaning build artifacts...${NC}"
    
    # Clean local build directories
    if [ -d "$PROJECT_ROOT/bin" ]; then
        rm -rf "$PROJECT_ROOT/bin"
        echo -e "${GREEN}✓ Removed bin/ directory${NC}"
    fi
    
    if [ -d "$PROJECT_ROOT/dist" ]; then
        rm -rf "$PROJECT_ROOT/dist"
        echo -e "${GREEN}✓ Removed dist/ directory${NC}"
    fi
    
    # Clean RPM build artifacts
    if [ -d "$HOME/rpmbuild" ]; then
        # Remove existing RPM packages
        find "$HOME/rpmbuild/RPMS" -name "orangecalc-*.rpm" -delete 2>/dev/null
        find "$HOME/rpmbuild/SRPMS" -name "orangecalc-*.rpm" -delete 2>/dev/null
        echo -e "${GREEN}✓ Removed existing RPM packages${NC}"
        
        # Remove existing source tarballs
        rm -f "$HOME/rpmbuild/SOURCES/orangecalc-"*.tar.gz 2>/dev/null
        echo -e "${GREEN}✓ Removed existing source tarballs${NC}"
        
        # Clean build directories
        rm -rf "$HOME/rpmbuild/BUILD/orangecalc-"* 2>/dev/null
        rm -rf "$HOME/rpmbuild/BUILDROOT/orangecalc-"* 2>/dev/null
        echo -e "${GREEN}✓ Cleaned RPM build directories${NC}"
    fi
    
    echo -e "${GREEN}✓ Clean complete${NC}"
}

create_source_tarball() {
    echo -e "${CYAN}Creating source tarball...${NC}"

    # Create temporary directory with proper structure
    TEMP_DIR=$(mktemp -d)
    mkdir -p "$TEMP_DIR/orangecalc-${VERSION}"

    # Copy all files to the temporary directory
    cp -r * "$TEMP_DIR/orangecalc-${VERSION}/" 2>/dev/null || true
    cp -r .[^.]* "$TEMP_DIR/orangecalc-${VERSION}/" 2>/dev/null || true

    # Create tarball from temporary directory
    cd "$TEMP_DIR"
    tar --exclude='orangecalc-*/bin' --exclude='orangecalc-*/dist' --exclude='orangecalc-*/*.class' \
        --exclude='orangecalc-*/*~' --exclude='orangecalc-*/*.swp' --exclude='orangecalc-*/.git' \
        -czf "$HOME/rpmbuild/SOURCES/orangecalc-${VERSION}.tar.gz" "orangecalc-${VERSION}"

    # Clean up temporary directory
    rm -rf "$TEMP_DIR"
    cd "$PROJECT_ROOT"

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Source tarball created${NC}"
        TARBALL_SIZE=$(du -h "$HOME/rpmbuild/SOURCES/orangecalc-${VERSION}.tar.gz" | cut -f1)
        echo -e "${CYAN}  Location: $HOME/rpmbuild/SOURCES/orangecalc-${VERSION}.tar.gz${NC}"
        echo -e "${CYAN}  Size: $TARBALL_SIZE${NC}"
        return 0
    else
        echo -e "${RED}✗ Failed to create source tarball${NC}"
        return 1
    fi
}

# Parse arguments
CLEAN_BUILD=false
TAR_ONLY=false

for arg in "$@"; do
    case $arg in
        clean)
            CLEAN_BUILD=true
            ;;
        tar)
            TAR_ONLY=true
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

echo -e "${GREEN}Orange Calculator RPM Build Script${NC}"
echo -e "${GREEN}==================================${NC}"

# Check if rpmbuild is available (skip for tar-only builds)
if [ "$TAR_ONLY" = false ] && ! command -v rpmbuild &> /dev/null; then
    echo -e "${RED}Error: rpmbuild not found${NC}"
    echo -e "${YELLOW}Install with: sudo dnf install rpm-build rpmdevtools${NC}"
    exit 1
fi

# Set up RPM build directories if they don't exist
if [ ! -d "$HOME/rpmbuild" ]; then
    echo -e "${YELLOW}Setting up RPM build directories...${NC}"
    rpmdev-setuptree
fi

cd "$PROJECT_ROOT"

# Clean build artifacts if requested
if [ "$CLEAN_BUILD" = true ]; then
    clean_build_artifacts
fi

# Validate project first
echo -e "${CYAN}Validating project...${NC}"
if ! packaging/validate-project.sh > /dev/null 2>&1; then
    echo -e "${RED}Project validation failed!${NC}"
    echo "Run: packaging/validate-project.sh"
    exit 1
fi
echo -e "${GREEN}✓ Project validation passed${NC}"

# Create source tarball
if ! create_source_tarball; then
    exit 1
fi

# If tar-only mode, exit here
if [ "$TAR_ONLY" = true ]; then
    echo
    echo -e "${GREEN}Source tarball creation complete!${NC}"
    echo -e "${CYAN}Tarball location: $HOME/rpmbuild/SOURCES/orangecalc-${VERSION}.tar.gz${NC}"
    exit 0
fi

# Build RPM
echo -e "${CYAN}Building RPM package...${NC}"
rpmbuild -ba packaging/orangecalc.spec

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ RPM build successful!${NC}"
    
    # Find the generated RPM
    RPM_FILE=$(find "$HOME/rpmbuild/RPMS" -name "orangecalc-${VERSION}-*.rpm" | head -1)
    
    if [ -n "$RPM_FILE" ]; then
        echo
        echo -e "${GREEN}Generated RPM:${NC}"
        echo -e "${CYAN}  $RPM_FILE${NC}"
        
        # Show RPM info
        echo
        echo -e "${GREEN}RPM Information:${NC}"
        rpm -qip "$RPM_FILE"
        
        echo
        echo -e "${GREEN}RPM Contents:${NC}"
        rpm -qlp "$RPM_FILE"
        
        # Get file size
        RPM_SIZE=$(du -h "$RPM_FILE" | cut -f1)
        echo
        echo -e "${GREEN}RPM Size: ${CYAN}$RPM_SIZE${NC}"
        
        echo
        echo -e "${GREEN}To install:${NC}"
        echo -e "${CYAN}  sudo rpm -ivh $RPM_FILE${NC}"
        
        echo
        echo -e "${GREEN}To test:${NC}"
        echo -e "${CYAN}  orangecalc${NC}"
        
    else
        echo -e "${RED}✗ Could not find generated RPM file${NC}"
        exit 1
    fi
else
    echo -e "${RED}✗ RPM build failed${NC}"
    echo
    echo -e "${YELLOW}Check build logs in:${NC}"
    echo -e "${CYAN}  ~/rpmbuild/BUILD/orangecalc-${VERSION}/${NC}"
    exit 1
fi

echo
echo -e "${GREEN}RPM build process complete!${NC}"