@echo off
REM Orange Calculator Build Script for Windows CMD
REM Usage: build.bat [clean] [run]

setlocal enabledelayedexpansion

set PROJECT_ROOT=%~dp0..\
set SRC_DIR=%PROJECT_ROOT%src
set BIN_DIR=%PROJECT_ROOT%bin
set RES_DIR=%PROJECT_ROOT%res
set MAIN_CLASS=wagemaker.OrangeCalc

echo Orange Calculator Build Script
echo ==============================

REM Check arguments
set CLEAN_BUILD=false
set RUN_APP=false

:parse_args
if "%1"=="clean" (
    set CLEAN_BUILD=true
    shift
    goto parse_args
)
if "%1"=="run" (
    set RUN_APP=true
    shift
    goto parse_args
)
if "%1"=="help" (
    goto show_help
)
if not "%1"=="" (
    shift
    goto parse_args
)

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java not found in PATH
    exit /b 1
)

REM Clean if requested
if "%CLEAN_BUILD%"=="true" (
    echo Cleaning project...
    if exist "%BIN_DIR%" rmdir /s /q "%BIN_DIR%"
    mkdir "%BIN_DIR%" 2>nul
    echo Clean complete.
)

REM Create bin directory if it doesn't exist
if not exist "%BIN_DIR%" mkdir "%BIN_DIR%"

REM Compile Java files
echo Compiling Java source files...
dir /s /b "%SRC_DIR%\*.java" > temp_files.txt
if errorlevel 1 (
    echo No Java files found in %SRC_DIR%
    del temp_files.txt 2>nul
    exit /b 1
)

javac -cp "%SRC_DIR%" -d "%BIN_DIR%" @temp_files.txt
if errorlevel 1 (
    echo Compilation failed!
    del temp_files.txt 2>nul
    exit /b 1
)
del temp_files.txt

echo Compilation successful!

REM Copy resources
echo Copying resources...
if exist "%RES_DIR%" (
    xcopy /e /y "%RES_DIR%\*" "%BIN_DIR%\" >nul
    echo Resources copied to bin directory.
) else (
    echo No resources directory found.
)

REM Run if requested
if "%RUN_APP%"=="true" (
    echo Running application...
    cd /d "%BIN_DIR%"
    java -cp . %MAIN_CLASS%
    cd /d "%PROJECT_ROOT%"
)

echo Build process complete!
goto end

:show_help
echo Orange Calculator Build Script
echo.
echo Usage:
echo   build.bat           - Compile the project
echo   build.bat clean     - Clean and compile
echo   build.bat run       - Compile and run
echo   build.bat clean run - Clean, compile and run
echo   build.bat help      - Show this help
echo.

:end
endlocal