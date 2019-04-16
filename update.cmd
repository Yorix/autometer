@set @x=0; /*
@echo off
ver |>NUL find /v "5." && if "%~1"=="" cscript.exe //nologo //e:jscript "%~f0"& exit /b

cd /d "%~dp0"
git pull>.gitAns
SET /p gitPullAnswer=<.gitAns
SET expected=Already up to date
IF "%expected%"=="%gitPullAnswer:~0,-1%" (
GOTO END
)
SetLocal EnableExtensions
SET ProcessName=javaw.exe
TaskList /FI "ImageName EQ %ProcessName%" | Find /I "%ProcessName%"
IF NOT %ERRORLEVEL% NEQ 0 (
taskkill /IM javaw.exe /f
)
call mvn clean package
:END

*/new ActiveXObject('Shell.Application').ShellExecute (WScript.ScriptFullName,'Admin','','runas',1);
