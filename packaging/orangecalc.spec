Name:           orangecalc
Version:        1.5.7
Release:        1%{?dist}
Summary:        A lightweight Java calculator with multiple themes and advanced features

License:        MIT
URL:            https://github.com/gcclinux/orangecalc
Source0:        %{name}-%{version}.tar.gz

BuildArch:      noarch
BuildRequires:  java-21-openjdk-devel
Requires:       java-21-openjdk-headless
Requires:       hicolor-icon-theme

%description
Orange Calculator is a lightweight calculator application written in Java (Swing). 
It provides basic and advanced mathematical operations with a customizable interface 
featuring multiple color themes and button styles. The calculator includes calculation 
history, audio feedback, and keyboard shortcuts.

Features:
- Basic arithmetic operations (+, -, ×, ÷)
- Advanced functions (square root, power, percentage, pi)
- Multiple color themes (Orange, Purple, Lime, Plain, Black)
- Customizable button styles and borders
- Calculation history with HTML export
- Audio feedback for button presses
- Keyboard shortcuts support
- Cross-platform Java application

%prep
%setup -q

%build
# Compile Java sources
find src/ -name "*.java" > sources.txt
mkdir -p bin
javac -d bin -Xlint:deprecation @sources.txt

# Copy resources
cp -r res/* bin/

# Create JAR file
mkdir -p dist
echo "Manifest-Version: 1.0" > manifest.txt
echo "Main-Class: wagemaker.OrangeCalc" >> manifest.txt
echo "Created-By: Orange Calculator Build Script" >> manifest.txt
echo "Implementation-Title: Orange Calculator" >> manifest.txt
echo "Implementation-Version: 1.5.7" >> manifest.txt
echo "Implementation-Vendor: Ricardo Wagemaker" >> manifest.txt
jar cfm dist/OrangeCalculator.jar manifest.txt -C bin .

%install
# Create directory structure
install -d %{buildroot}%{_datadir}/%{name}
install -d %{buildroot}%{_bindir}
install -d %{buildroot}%{_datadir}/applications
install -d %{buildroot}%{_datadir}/icons/hicolor/48x48/apps
install -d %{buildroot}%{_datadir}/icons/hicolor/64x64/apps
install -d %{buildroot}%{_docdir}/%{name}

# Install JAR file
install -m 644 dist/OrangeCalculator.jar %{buildroot}%{_datadir}/%{name}/

# Install icons
install -m 644 res/images/calculator.png %{buildroot}%{_datadir}/icons/hicolor/48x48/apps/%{name}.png
install -m 644 res/images/calculator.png %{buildroot}%{_datadir}/icons/hicolor/64x64/apps/%{name}.png

# Create launcher script
cat > %{buildroot}%{_bindir}/%{name} << 'EOF'
#!/bin/bash
# Orange Calculator launcher script
exec java -jar %{_datadir}/%{name}/OrangeCalculator.jar "$@"
EOF
chmod 755 %{buildroot}%{_bindir}/%{name}

# Install desktop file
install -m 644 packaging/%{name}.desktop %{buildroot}%{_datadir}/applications/

# Install documentation
install -m 644 README.md %{buildroot}%{_docdir}/%{name}/
install -m 644 LICENSE %{buildroot}%{_docdir}/%{name}/

%post
# Update desktop database and icon cache
/bin/touch --no-create %{_datadir}/icons/hicolor &>/dev/null || :
/usr/bin/update-desktop-database &> /dev/null || :

%postun
if [ $1 -eq 0 ] ; then
    /bin/touch --no-create %{_datadir}/icons/hicolor &>/dev/null
    /usr/bin/gtk-update-icon-cache %{_datadir}/icons/hicolor &>/dev/null || :
fi
/usr/bin/update-desktop-database &> /dev/null || :

%posttrans
/usr/bin/gtk-update-icon-cache %{_datadir}/icons/hicolor &>/dev/null || :

%files
%doc %{_docdir}/%{name}/README.md
%license %{_docdir}/%{name}/LICENSE
%{_bindir}/%{name}
%{_datadir}/%{name}/
%{_datadir}/applications/%{name}.desktop
%{_datadir}/icons/hicolor/48x48/apps/%{name}.png
%{_datadir}/icons/hicolor/64x64/apps/%{name}.png

%changelog
* Sat Jan 11 2025 Ricardo Wagemaker <maintainer@example.com> - 1.5.7-1
- Updated to version 1.5.7
- Introduced vibrant Purple theme with dynamic color support
- Enhanced About dialog with theme-aware gradient backgrounds
- Improved theme persistence and initialization
- Fixed RPM packaging and build system
- Added comprehensive project validation tools
- Updated desktop integration and icon support

* Fri Jan 10 2025 Ricardo Wagemaker <maintainer@example.com> - 1.5.6-1
- Updated to version 1.5.6
- Added new Purple theme support
- Redesigned About dialog with dynamic theme colors
- Improved theme switching and persistence
- Enhanced UI with better color schemes
- Fixed theme initialization issues
- Updated build system with JAR creation support

* Fri Jan 10 2025 Ricardo Wagemaker <maintainer@example.com> - 1.5.5-1
- Initial RPM package for Orange Calculator
- Lightweight Java calculator with multiple themes
- Advanced mathematical functions included
- Desktop integration with proper icons and categories
- Audio feedback and calculation history features
- Keyboard shortcuts and customizable interface