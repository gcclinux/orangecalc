# Create a Windows installer EXE (or app-image) using jpackage for the Orange Calculator
# Usage: .\jpackage.ps1
# Requirements:
# - JDK 14+ (jpackage on PATH)
# - Optional: WiX Toolset (for creating an EXE/MSI installer on Windows)

$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot

# Helper: ensure jpackage exists
$jpackageCmd = Get-Command jpackage -ErrorAction SilentlyContinue
if (-not $jpackageCmd) { Write-Error 'jpackage not found on PATH. Please install a JDK that provides jpackage (JDK 20+).' ; exit 1 }

# Build the jar first (we rely on your existing package.ps1 that creates dist/OrangeCalc.jar)
Write-Host '[jpackage] Building jar using package.ps1'
if (Test-Path (Join-Path $PSScriptRoot '..\package.ps1')) {
    try { & "$PSScriptRoot\..\package.ps1" } catch { Write-Error "package.ps1 failed: $_"; exit 1 }
} else {
    Write-Host '[jpackage] package.ps1 not found. Make sure your .jar is present in dist\ or update this script.'
}

# Read metadata from CalcProperties.java to avoid hard-coding values
$calcProps = Join-Path $PSScriptRoot '..\src\wagemaker\CalcProperties.java'
if (-not (Test-Path $calcProps)) { Write-Warning 'CalcProperties.java not found; using fallback metadata' }
else {
    $cp = Get-Content $calcProps -Raw
    $appName = ([regex]::Match($cp, 'Title\s*=\s*"([^"]+)"')).Groups[1].Value
    $version = ([regex]::Match($cp, 'Version\s*=\s*"([^"]+)"')).Groups[1].Value
    $vendor = ([regex]::Match($cp, 'Developer\s*=\s*"([^"]+)"')).Groups[1].Value
}

if (-not $appName) { $appName = 'OrangeCalc' }
if (-not $version) { $version = '1.5.7' }
if (-not $vendor) { $vendor = 'Ricardo Wagemaker' }

$mainClass = 'wagemaker.CalculatorOrangeLite'

# Default paths you can tweak
$distDir = Join-Path $PSScriptRoot '..\dist'
$inputDir = $distDir
$jarName = "$appName.jar"    # expected main jar name placed in dist\
$jarPath = Join-Path $distDir $jarName
# Prefer the project's calculator icon from res/images
$iconIco = Join-Path $PSScriptRoot '..\res\images\calculator.ico'
$installerOut = Join-Path $distDir 'installer'
$appImageOut = Join-Path $distDir 'appimage'

if (-not (Test-Path $installerOut)) { New-Item -ItemType Directory -Path $installerOut | Out-Null }
if (-not (Test-Path $appImageOut)) { New-Item -ItemType Directory -Path $appImageOut | Out-Null }

# If the expected jar is not present, try to create one from bin\ using 'jar' or fall back to any jar in dist\
if (-not (Test-Path $jarPath)) {
    $binDir = Join-Path $PSScriptRoot '..\bin'
    # create a safe jar name by removing non-alphanumerics
    $safeAppName = ($appName -replace '[^A-Za-z0-9]', '')
    if (-not $safeAppName) { $safeAppName = 'OrangeCalc' }
    $jarName = "$safeAppName.jar"
    $jarPath = Join-Path $distDir $jarName

    if (Test-Path $binDir) {
        # ensure dist exists
        if (-not (Test-Path $distDir)) { New-Item -ItemType Directory -Path $distDir | Out-Null }

        # create manifest file
        $manifestPath = Join-Path $distDir 'manifest.txt'
        $manifestContent = "Main-Class: $mainClass`n"
        Set-Content -Path $manifestPath -Value $manifestContent -Encoding ASCII

        $jarTool = Get-Command jar -ErrorAction SilentlyContinue
        if ($jarTool) {
            Write-Host "[jpackage] Creating jar $jarPath from $binDir using manifest $manifestPath"
            & $jarTool.Source 'cfm' $jarPath $manifestPath '-C' $binDir '.'
            if (-not (Test-Path $jarPath)) {
                Write-Warning "Jar creation failed or jar not found at $jarPath"
            } else {
                Write-Host "[jpackage] Created jar: $jarPath"
            }
        } else {
            Write-Warning "'jar' command not found. Please ensure JDK bin is on PATH so this script can create the jar."
        }
    }

    # If jar still not present, fallback to any jar in dist
    if (-not (Test-Path $jarPath)) {
        $found = Get-ChildItem -Path $distDir -Filter '*.jar' | Select-Object -First 1
        if ($found) { $jarPath = $found.FullName; $jarName = $found.Name; Write-Host "[jpackage] Using found jar: $jarName" } else { Write-Error "No jar found or created in $distDir. Build your jar before running jpackage."; exit 1 }
    }
}

# Icon (optional)
if (-not (Test-Path $iconIco)) { Write-Host '[jpackage] calculator.ico not found; continuing without custom icon'; $iconIco = $null } else { Write-Host "[jpackage] Using icon: $iconIco" }

# Detect WiX (needed for exe/msi packaging on Windows)
$wixFound = $false
foreach ($tool in @('candle.exe','light.exe','wix.exe')) {
    if (Get-Command $tool -ErrorAction SilentlyContinue) { $wixFound = $true; break }
}

if ($wixFound) { Write-Host '[jpackage] WiX toolset detected — creating Windows installer (exe)'; $pkgType = 'exe' } else { Write-Host '[jpackage] WiX not detected — will create app-image (no installer). To produce an EXE installer install WiX and re-run this script.' ; $pkgType = 'app-image' }



try {
    if ($pkgType -eq 'exe') {
        $jpackArgs = @(
            '--type','exe',
            '--input', $inputDir,
            '--name', $appName,
            '--main-jar', $jarName,
            '--main-class', $mainClass,
            '--app-version', $version,
            '--vendor', $vendor,
            '--dest', $installerOut,
            '--win-menu','--win-shortcut'
        )
        if ($iconIco) { $jpackArgs += @('--icon', $iconIco) }
        # Clean previous installer output to avoid "already exists" errors
        $targetInstallerFolder = Join-Path $installerOut $appName
        if (Test-Path $targetInstallerFolder) {
            Write-Host "[jpackage] Removing existing installer target folder: $targetInstallerFolder"
            Remove-Item -LiteralPath $targetInstallerFolder -Recurse -Force -ErrorAction SilentlyContinue
        }
        Write-Host "[jpackage] Running jpackage (installer) with: $($jpackArgs -join ' ')"
        & $jpackageCmd @jpackArgs
        Write-Host '[jpackage] Installer created. Contents of installer folder:'
        Get-ChildItem -Path $installerOut -Recurse | Select-Object FullName
    } else {
        $jpackArgs = @(
            '--type','app-image',
            '--input', $inputDir,
            '--name', $appName,
            '--main-jar', $jarName,
            '--main-class', $mainClass,
            '--app-version', $version,
            '--vendor', $vendor,
            '--dest', $appImageOut
        )
        if ($iconIco) { $jpackArgs += @('--icon', $iconIco) }
        # Clean previous app-image output to avoid "already exists" errors
        $producedFolder = Join-Path $appImageOut $appName
        if (Test-Path $producedFolder) {
            Write-Host "[jpackage] Removing existing app-image folder: $producedFolder"
            Remove-Item -LiteralPath $producedFolder -Recurse -Force -ErrorAction SilentlyContinue
        }
        Write-Host "[jpackage] Running jpackage (app-image) with: $($jpackArgs -join ' ')"
        & $jpackageCmd @jpackArgs

        # Zip the produced app-image for distribution
        $producedFolder = Join-Path $appImageOut $appName
        if (Test-Path $producedFolder) {
            $zipPath = Join-Path $distDir "$appName-windows-appimage.zip"
            if (Test-Path $zipPath) { Remove-Item $zipPath -Force }
            Write-Host "[jpackage] Zipping app-image to $zipPath"
            Compress-Archive -Path (Join-Path $producedFolder '*') -DestinationPath $zipPath -Force
            Write-Host "[jpackage] App-image zip created: $zipPath"
        } else { Write-Warning "Expected app-image folder not found: $producedFolder" }
    }
} catch { Write-Error "jpackage failed: $_"; exit 1 }

Write-Host '[jpackage] Packaging finished.'