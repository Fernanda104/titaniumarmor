@echo off
title Titanium Armor - Iniciar Microservicios

echo ===========================================
echo      INICIANDO TITANIUM ARMOR
echo ===========================================

echo.
echo [1/9] Usuarios Service...
start "Usuarios Service" cmd /k "cd /d usuarios-service && .\mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

echo [2/9] Catalogo Service...
start "Catalogo Service" cmd /k "cd /d catalogo-service && .\mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

echo [3/9] Inventario Service...
start "Inventario Service" cmd /k "cd /d inventario-service && .\mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

echo [4/9] Ventas Service...
start "Ventas Service" cmd /k "cd /d ventas-service && .\mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

echo [5/9] Pagos Service...
start "Pagos Service" cmd /k "cd /d pagos-service && .\mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

echo [6/9] Envios Service...
start "Envios Service" cmd /k "cd /d envios-service && .\mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

echo [7/9] Promociones Service...
start "Promociones Service" cmd /k "cd /d promociones-service && .\mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

echo [8/9] Resenas Service...
start "Resenas Service" cmd /k "cd /d resenas-service && .\mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

echo [9/9] API Gateway...
start "API Gateway" cmd /k "cd /d api-gateway && .\mvnw.cmd spring-boot:run"

echo.
echo ===========================================
echo Todos los servicios fueron iniciados.
echo ===========================================

pause