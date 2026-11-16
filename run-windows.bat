@echo off
setlocal
cd /d "%~dp0"
where mvn >nul 2>nul
if errorlevel 1 (
  echo Maven was not found. Install Maven 3.8+ and add its bin folder to PATH.
  pause
  exit /b 1
)
echo Building and starting Electricity Bill Calculator...
mvn clean spring-boot:run
endlocal
