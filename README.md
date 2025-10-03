# Orange Calculator - Build Guide

## Overview
This project provides multiple build systems to compile and run the Orange Calculator application. All build scripts compile Java source files to the `bin/` directory while preserving the package structure.

## Build Options

### 1. PowerShell Script (Recommended for Windows)
```powershell
# Compile only
.\build.ps1

# Clean and compile
.\build.ps1 -Clean

# Compile and run
.\build.ps1 -Run

# Clean, compile and run
.\build.ps1 -Clean -Run

# Show help
.\build.ps1 -Help
```

### 2. Batch Script (Windows CMD)
```batch
# Compile only
build.bat

# Clean and compile
build.bat clean

# Compile and run
build.bat run

# Clean, compile and run
build.bat clean run

# Show help
build.bat help
```

### 3. Shell Script (Linux/Mac)
```bash
# Make executable first (Linux/Mac only)
chmod +x build.sh

# Compile only
./build.sh

# Clean and compile
./build.sh clean

# Compile and run
./build.sh run

# Clean, compile and run
./build.sh clean run

# Show help
./build.sh help
```

### 4. Maven (Alternative)
If you prefer Maven, use the existing `pom.xml`:
```bash
# Compile
mvn compile

# Compile and run
mvn exec:java

# Clean and compile
mvn clean compile

# Package as JAR
mvn package
```

## Quick Run
After building, you can also run directly:
```batch
# Windows
run.bat

# Or manually
cd bin
java -cp . wagemaker.OrangeCalc
```

## Project Structure
```
orangecalc/
├── src/
│   └── wagemaker/           # Java source files
├── res/                     # Resources (images, audio)
├── bin/                     # Compiled classes (auto-generated)
│   ├── wagemaker/          # Package structure preserved
│   ├── images/             # Resources copied here
│   └── audio/              # Resources copied here
├── build.ps1               # PowerShell build script
├── build.bat               # Batch build script
├── build.sh                # Shell build script
├── run.bat                 # Quick run script
```

## Requirements
- Java 21 (LTS) or later
- javac (Java compiler) in PATH
- On Windows: PowerShell or Command Prompt
- On Linux/Mac: Bash shell and make (optional)

## Notes
- All scripts automatically copy resources from `res/` to `bin/`
- Class files are organized in package structure under `bin/wagemaker/`
- The main class is `wagemaker.OrangeCalc`
- All build scripts check for Java availability before compilation
- Use the clean option when switching between different Java versions

## Troubleshooting
1. **"Java not found"**: Ensure Java is installed and in your PATH
2. **"javac not found"**: Install JDK (not just JRE)
3. **Permission denied (Linux/Mac)**: Run `chmod +x build.sh`
4. **PowerShell execution policy**: Run `Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser`