Name:           orangecalc
Version:        1.5.5
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
- Multiple color themes (Orange, Lime, Dark Gray, Plain)
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
mkdir -p classes/artifacts
echo "Main-Class: wagemaker.CalculatorOrangeLite" > manifest.txt
jar cfm classes/artifacts/OrangeCalc.jar manifest.txt -C bin .

%install
# Create directory structure
install -d %{buildroot}%{_datadir}/%{name}
install -d %{buildroot}%{_bindir}
install -d %{buildroot}%{_datadir}/applications
install -d %{buildroot}%{_datadir}/icons/hicolor/48x48/apps
install -d %{buildroot}%{_datadir}/icons/hicolor/64x64/apps
install -d %{buildroot}%{_docdir}/%{name}

# Install JAR file
install -m 644 classes/artifacts/OrangeCalc.jar %{buildroot}%{_datadir}/%{name}/

# Install icons (using calculator.ico converted to PNG or existing PNG icons)
if [ -f res/images/calculator.ico ]; then
    # Convert ICO to PNG if needed, or use existing PNG files
    install -m 644 res/images/calculator.png %{buildroot}%{_datadir}/icons/hicolor/48x48/apps/%{name}.png
    install -m 644 res/images/calculator.png %{buildroot}%{_datadir}/icons/hicolor/64x64/apps/%{name}.png
fi

# Create launcher script
cat > %{buildroot}%{_bindir}/%{name} << 'EOF'
#!/bin/bash
# Orange Calculator launcher script
exec java -jar %{_datadir}/%{name}/OrangeCalc.jar "$@"
EOF
chmod 755 %{buildroot}%{_bindir}/%{name}

# Create desktop file
cat > %{buildroot}%{_datadir}/applications/%{name}.desktop << 'EOF'
[Desktop Entry]
Version=1.0
Type=Application
Name=Orange Calculator
Comment=A lightweight Java calculator with multiple themes and advanced features
Comment[es]=Una calculadora ligera en Java con múltiples temas y funciones avanzadas
Comment[fr]=Une calculatrice légère en Java avec plusieurs thèmes et fonctionnalités avancées
Comment[de]=Ein leichtgewichtiger Java-Rechner mit mehreren Themen und erweiterten Funktionen
Exec=orangecalc
Icon=orangecalc
Terminal=false
StartupNotify=true
Categories=Utility;Calculator;Office;
Keywords=calculator;math;arithmetic;java;
StartupWMClass=OrangeCalc
EOF

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
* Fri Jan 10 2025 Ricardo Wagemaker <maintainer@example.com> - 1.5.5-1
- Initial RPM package for Orange Calculator
- Lightweight Java calculator with multiple themes
- Advanced mathematical functions included
- Desktop integration with proper icons and categories
- Audio feedback and calculation history features
- Keyboard shortcuts and customizable interface