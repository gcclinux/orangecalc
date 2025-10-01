# Orange Calculator Build Script
# Compiles Java source files and copies resources

param(
    [switch]$Clean,
    [switch]$Run,
    [switch]$Help
)

$PROJECT_ROOT = $PSScriptRoot
$SRC_DIR = Join-Path $PROJECT_ROOT "src"
$BIN_DIR = Join-Path $PROJECT_ROOT "bin"
$RES_DIR = Join-Path $PROJECT_ROOT "res"
$MAIN_CLASS = "wagemaker.CalculatorOrangeLite"

function Show-Help {
    Write-Host "Orange Calculator Build Script" -ForegroundColor Green
    Write-Host ""
    Write-Host "Usage:"
    Write-Host "  .\build.ps1           - Compile the project"
    Write-Host "  .\build.ps1 -Clean    - Clean and compile"
    Write-Host "  .\build.ps1 -Run      - Compile and run"
    Write-Host "  .\build.ps1 -Help     - Show this help"
    Write-Host ""
}

function Clean-Project {
    Write-Host "Cleaning project..." -ForegroundColor Yellow
    if (Test-Path $BIN_DIR) {
        Remove-Item -Path $BIN_DIR -Recurse -Force
    }
    New-Item -Path $BIN_DIR -ItemType Directory -Force | Out-Null
    Write-Host "Clean complete." -ForegroundColor Green
}

function Copy-Resources {
    Write-Host "Copying resources..." -ForegroundColor Yellow
    if (Test-Path $RES_DIR) {
        Copy-Item -Path "$RES_DIR\*" -Destination $BIN_DIR -Recurse -Force
        Write-Host "Resources copied to bin directory." -ForegroundColor Green
    } else {
        Write-Host "No resources directory found." -ForegroundColor Yellow
    }
}

function Compile-Java {
    Write-Host "Compiling Java source files..." -ForegroundColor Yellow
    
    # Check if Java is available
    try {
        $javaVersion = java -version 2>&1 | Select-String "version"
        Write-Host "Using Java: $javaVersion" -ForegroundColor Cyan
    } catch {
        Write-Host "Error: Java not found in PATH" -ForegroundColor Red
        exit 1
    }
    
    # Find all Java files
    $javaFiles = Get-ChildItem -Path $SRC_DIR -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
    
    if ($javaFiles.Count -eq 0) {
        Write-Host "No Java files found in $SRC_DIR" -ForegroundColor Red
        exit 1
    }
    
    Write-Host "Found $($javaFiles.Count) Java files to compile" -ForegroundColor Cyan
    
    # Compile with proper classpath and output directory
    $compileCmd = "javac -cp `"$SRC_DIR`" -d `"$BIN_DIR`" " + ($javaFiles -join " ")
    
    try {
        Invoke-Expression $compileCmd
        if ($LASTEXITCODE -eq 0) {
            Write-Host "Compilation successful!" -ForegroundColor Green
        } else {
            Write-Host "Compilation failed!" -ForegroundColor Red
            exit 1
        }
    } catch {
        Write-Host "Error during compilation: $_" -ForegroundColor Red
        exit 1
    }
}

function Run-Application {
    Write-Host "Running application..." -ForegroundColor Yellow
    try {
        Set-Location $BIN_DIR
        java -cp . $MAIN_CLASS
    } catch {
        Write-Host "Error running application: $_" -ForegroundColor Red
        exit 1
    } finally {
        Set-Location $PROJECT_ROOT
    }
}

# Main execution
if ($Help) {
    Show-Help
    exit 0
}

Write-Host "Orange Calculator Build Script" -ForegroundColor Green
Write-Host "==============================" -ForegroundColor Green

if ($Clean) {
    Clean-Project
} elseif (-not (Test-Path $BIN_DIR)) {
    New-Item -Path $BIN_DIR -ItemType Directory -Force | Out-Null
}

Compile-Java
Copy-Resources

if ($Run) {
    Run-Application
}

Write-Host "Build process complete!" -ForegroundColor Green