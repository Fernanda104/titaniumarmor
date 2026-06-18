@echo off
echo Cerrando procesos de Titanium Armor...

for %%P in (9090 9091 9092 9093 9094 9095 9096 9097 9098 9099) do (
    for /f "tokens=5" %%A in ('netstat -ano ^| findstr :%%P') do (
        taskkill /PID %%A /F >nul 2>&1
    )
)

echo.
echo Todos los servicios fueron cerrados.
pause