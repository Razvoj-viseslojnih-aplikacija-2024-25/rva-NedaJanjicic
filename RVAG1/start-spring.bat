@echo off
SET PORT=8080
SET JAR=target\RVAG1-0.0.1-SNAPSHOT.jar

echo Checking if port %PORT% is in use...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :%PORT%') do (
    echo Port %PORT% is in use by PID %%a. Killing it...
    taskkill /PID %%a /F
)

echo Starting Spring Boot application...
java -jar %JAR%

pause
