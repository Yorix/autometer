@echo off
cd /d "%~dp0"
SetLocal EnableExtensions
SET ProcessName=javaw.exe
TaskList /FI "ImageName EQ %ProcessName%" | Find /I "%ProcessName%"
IF %ERRORLEVEL% NEQ 0 (
javaw.exe -jar target\autometer-0.0.1-SNAPSHOT.jar
) ELSE (
explorer http://localhost:8080/
)
