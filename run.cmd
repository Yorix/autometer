@echo off
SET ProcessName=javaw.exe
for /f "tokens=9 delims=," %%t in ('tasklist /FI "ImageName eq %ProcessName%" /nh /v /fo csv') do (explorer http://localhost:8080/ || exit)
start %ProcessName% -jar target\autometer-0.0.1-SNAPSHOT.jar
exit