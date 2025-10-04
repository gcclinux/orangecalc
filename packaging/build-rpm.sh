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

echo -e "${GREEN}Orange Calculator RPM Build Script${NC}"
echo -e "${GREEN}==================================${NC}"

# Check if rpmbuild is available
if ! command -v rpmbuild &> /dev/null; then
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

# Validate project first
echo -e "${CYAN}Validating project...${NC}"
if ! packaging/validate-project.sh > /dev/null 2>&1; then
    echo -e "${RED}Project validation failed!${NC}"
    echo "Run: packaging/validate-project.sh"
    exit 1
fi
echo -e "${GREEN}✓ Project validation passed${NC}"

# Create source tarball
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
else
    echo -e "${RED}✗ Failed to create source tarball${NC}"
    exit 1
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