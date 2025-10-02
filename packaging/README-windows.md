Windows packaging

This repository contains `packaging/jpackage.ps1` which will produce either:

- a Windows installer (EXE) if the WiX toolset is installed on the system, or
- an app-image ZIP (no installer) if WiX is not available.

Requirements (to build an EXE installer locally):
- JDK 21+ with `jpackage` on PATH
- WiX toolset (install via Chocolatey: `choco install wixtoolset -y` or download from https://wixtoolset.org)
- PowerShell (run as Administrator for WiX installation)

Build locally:

1. From repo root run (PowerShell):

   .\packaging\jpackage.ps1

2. If WiX is installed the installer will be in `dist/installer/`.
   Otherwise an app-image ZIP will be created at `dist/SmallTextPad-windows-appimage.zip`.

CI:

### Extract and run the produced app-image (example: unzip then run the launcher/exe inside):
```bash
# from repo root (PowerShell)
Expand-Archive -Path .\dist\"Orange Calculator-windows-appimage.zip\" -DestinationPath .\dist\appimage-unzipped

# then open the unzipped folder in Explorer and run the executable under the app folder (usually in bin\)

Start-Process .\dist\appimage-unzipped
```
