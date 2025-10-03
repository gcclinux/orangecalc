@echo off
REM Simple run script for Orange Calculator
cd /d %~dp0bin
java -cp . wagemaker.OrangeCalc
pause