rem $env:DOCKER_BUILDKIT=0
rem docker rm -f $(docker ps -aq)
FOR /f %%i IN ('docker ps -aq') DO docker rm -f %%i
FOR /f %%i IN ('docker images -aq') DO docker rmi -f %%i

cd api-gateway
call .\mvnw.cmd clean package -DskipTests

cd ../usuarios-service
call .\mvnw.cmd clean package -DskipTests

cd ../catalogo-service
call .\mvnw.cmd clean package -DskipTests

cd ../inventario-service
call .\mvnw.cmd clean package -DskipTests

cd ../ventas-service
call .\mvnw.cmd clean package -DskipTests

cd ../pagos-service
call .\mvnw.cmd clean package -DskipTests

cd ../envios-service
call .\mvnw.cmd clean package -DskipTests

cd ../promociones-service
call .\mvnw.cmd clean package -DskipTests

cd ../resenas-service
call .\mvnw.cmd clean package -DskipTests