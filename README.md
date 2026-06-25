# TitaniumArmor - Sistema de Microservicios

Proyecto desarrollado para la Evaluación Parcial 3 de la asignatura **Desarrollo Fullstack 1**.

TitaniumArmor es una plataforma backend basada en microservicios para la gestión de una tienda deportiva. El sistema permite administrar usuarios, catálogo de productos, inventario, ventas, pagos, envíos, promociones y reseñas. La arquitectura incorpora **API Gateway**, comunicación REST entre microservicios, documentación Swagger/OpenAPI, persistencia con MySQL, control de cambios de base de datos con Liquibase, despliegue local con Docker Compose y autenticación mediante JWT.

---

## Integrantes

* Fernanda Céspedes
* Cristopher Guerra

---

## Repositorio GitHub


https://github.com/Fernanda104/titaniumarmor


---

## Tecnologías utilizadas

* Java 21
* Spring Boot 4
* Spring Web / Web MVC
* Spring WebFlux / WebClient
* Spring Cloud Gateway
* Spring Security
* OAuth2 Resource Server
* JWT
* MySQL
* Liquibase
* Docker
* Docker Compose
* Maven
* JUnit
* Mockito
* Swagger / OpenAPI
* Lombok
* HATEOAS

---

## Arquitectura general

El proyecto sigue una arquitectura basada en microservicios. Cada microservicio posee una responsabilidad específica y mantiene separación por capas:

* `controller`: expone endpoints REST.
* `service`: contiene la lógica de negocio.
* `repository`: gestiona el acceso a datos mediante Spring Data JPA.
* `model`: define las entidades persistentes.
* `dto`: define objetos de transferencia de datos.
* `exception`: centraliza el manejo de errores.
* `config`: contiene configuraciones específicas del servicio.
* `assembler`: genera respuestas HATEOAS en los servicios que lo utilizan.

El acceso principal al sistema se realiza mediante el **API Gateway**, que centraliza el enrutamiento y valida el JWT para proteger los endpoints.

---

## Microservicios

| Servicio              | Puerto | Responsabilidad                                    |
| --------------------- | -----: | -------------------------------------------------- |
| `api-gateway`         |   9090 | Punto de entrada principal, rutas y validación JWT |
| `usuarios-service`    |   9091 | Gestión de usuarios                                |
| `catalogo-service`    |   9092 | Gestión de productos y categorías                  |
| `inventario-service`  |   9093 | Gestión de stock e inventario                      |
| `ventas-service`      |   9094 | Gestión de ventas                                  |
| `pagos-service`       |   9095 | Gestión de pagos                                   |
| `envios-service`      |   9096 | Gestión de envíos                                  |
| `auth-service`        |   9097 | Login y generación de JWT                          |
| `promociones-service` |   9098 | Gestión de promociones                             |
| `resenas-service`     |   9099 | Gestión de reseñas                                 |

---

## Flujo de autenticación JWT

El sistema utiliza JWT para proteger los endpoints expuestos a través del API Gateway.

Flujo de login:


Cliente
   ↓
POST /auth/login
   ↓
auth-service
   ↓
api-gateway
   ↓
usuarios-service
   ↓
MySQL
   ↓
JWT generado


Luego, para consumir endpoints protegidos, el cliente debe enviar el token en el header:


Authorization: Bearer TOKEN_GENERADO


El `auth-service` genera el token y el `api-gateway` lo valida. Los endpoints protegidos no pueden ser consumidos mediante el Gateway sin un JWT válido.

---

## Rutas del API Gateway

Todas estas rutas se consumen desde:


http://localhost:9090


| Ruta Gateway      | Microservicio destino |
| ----------------- | --------------------- |
| `/auth/**`        | `auth-service`        |
| `/usuarios/**`    | `usuarios-service`    |
| `/productos/**`   | `catalogo-service`    |
| `/categorias/**`  | `catalogo-service`    |
| `/inventarios/**` | `inventario-service`  |
| `/ventas/**`      | `ventas-service`      |
| `/pagos/**`       | `pagos-service`       |
| `/envios/**`      | `envios-service`      |
| `/promociones/**` | `promociones-service` |
| `/resenas/**`     | `resenas-service`     |

---

## Endpoints públicos

Los siguientes endpoints están permitidos sin JWT:


POST /auth/login
GET /usuarios/email?email={email}


El endpoint `/usuarios/email` queda permitido porque `auth-service` lo utiliza durante el proceso de login para validar las credenciales del usuario antes de emitir el JWT.

---

## Endpoints protegidos

El resto de rutas consumidas mediante el Gateway requieren JWT.

Ejemplo sin token:


GET http://localhost:9090/usuarios


Resultado esperado:


401 Unauthorized


Ejemplo con token:


GET http://localhost:9090/usuarios
Authorization: Bearer TOKEN_GENERADO


Resultado esperado:


200 OK


---

## Login y obtención de token

Endpoint:


POST http://localhost:9097/auth/login


Body de ejemplo:


{
  "email": "usuario@correo.com",
  "password": "1234"
}


Respuesta esperada:


{
  "status": "ok",
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}


Si las credenciales son incorrectas:


{
  "status": "error",
  "token": ""
}


---

## Swagger / OpenAPI

Los microservicios principales exponen documentación Swagger en la ruta `/doc/swagger-ui.html`.

| Servicio    | Swagger                                     |
| ----------- | ------------------------------------------- |
| Usuarios    | `http://localhost:9091/doc/swagger-ui.html` |
| Catálogo    | `http://localhost:9092/doc/swagger-ui.html` |
| Inventario  | `http://localhost:9093/doc/swagger-ui.html` |
| Ventas      | `http://localhost:9094/doc/swagger-ui.html` |
| Pagos       | `http://localhost:9095/doc/swagger-ui.html` |
| Envíos      | `http://localhost:9096/doc/swagger-ui.html` |
| Promociones | `http://localhost:9098/doc/swagger-ui.html` |
| Reseñas     | `http://localhost:9099/swagger-ui.html` |

Nota: `auth-service` se prueba mediante Postman usando `POST /auth/login`.

---

## Bases de datos

Cada microservicio de dominio posee su propia base de datos MySQL.

| Servicio              | Base de datos    |
| --------------------- | ---------------- |
| `usuarios-service`    | `usuarios_db`    |
| `catalogo-service`    | `catalogo_db`    |
| `inventario-service`  | `inventario_db`  |
| `ventas-service`      | `ventas_db`      |
| `pagos-service`       | `pagos_db`       |
| `envios-service`      | `envios_db`      |
| `promociones-service` | `promociones_db` |
| `resenas-service`     | `resenas_db`     |

El proyecto utiliza Liquibase para crear y mantener las tablas principales de los microservicios.

La conexión a MySQL desde Docker se realiza mediante:


host.docker.internal:3306


Por lo tanto, antes de levantar los servicios se debe tener MySQL iniciado en el equipo host.

---

## Requisitos previos

Antes de ejecutar el proyecto, se debe tener instalado y configurado:

* Java 21
* Maven o Maven Wrapper incluido en cada servicio
* Docker Desktop
* MySQL ejecutándose localmente
* Puerto 3306 disponible para MySQL
* Puertos 9090 a 9099 disponibles

---

## Ejecución del proyecto

### 1. Clonar el repositorio


git clone https://github.com/Fernanda104/titaniumarmor
cd titaniumarmor


### 2. Compilar los microservicios

Desde la raíz del proyecto:


.\build-all.bat


Este script compila los microservicios y genera los archivos `.jar` necesarios dentro de cada carpeta `target`.

### 3. Levantar los servicios con Docker Compose


docker compose up --build


También se puede ejecutar en segundo plano:


docker compose up --build -d


### 4. Verificar servicios levantados


docker compose ps


Deben aparecer los servicios:


api-gateway
usuarios-service
catalogo-service
inventario-service
ventas-service
pagos-service
envios-service
auth-service
promociones-service
resenas-service

### 5. Detener servicios


docker compose down


---

## Pruebas principales con Postman

### Login


POST http://localhost:9097/auth/login
Content-Type: application/json


Body:


{
  "email": "usuario@correo.com",
  "password": "1234"
}


Copiar el token recibido.

---

### Probar endpoint protegido sin token


GET http://localhost:9090/usuarios


Resultado esperado:


401 Unauthorized


---

### Probar endpoint protegido con token


GET http://localhost:9090/usuarios
Authorization: Bearer TOKEN_GENERADO


Resultado esperado:


200 OK


---

## Ejecución de pruebas unitarias

Para ejecutar las pruebas de un microservicio específico:


cd pagos-service
.\mvnw clean test


Ejemplo para inventario:


cd inventario-service
.\mvnw clean test


Ejemplo para ventas:


cd ventas-service
.\mvnw clean test

Los servicios que contienen pruebas unitarias relevantes incluyen:

* `inventario-service`
* `pagos-service`
* `ventas-service`
* `promociones-service`
* `resenas-service`

Las pruebas utilizan JUnit, Mockito y asserts para validar comportamiento de servicios y controladores.

---

## Scripts disponibles

### `build-all.bat`

Compila los microservicios y genera los `.jar`.

Uso:

.\build-all.bat


### `clean-all.bat`

Limpia los archivos generados por Maven en cada microservicio.

Uso:


.\clean-all.bat


### Docker Compose

Construye y levanta contenedores:


docker compose up --build


Detiene los contenedores:


docker compose down


Muestra el estado de los servicios:


docker compose ps


Muestra logs:


docker compose logs


Logs de un servicio específico:


docker compose logs api-gateway
docker compose logs auth-service


---

## Comunicación entre microservicios

El proyecto utiliza `WebClient` para la comunicación REST entre microservicios.

Ejemplos de interoperabilidad:

* `auth-service` consulta usuarios mediante el Gateway para validar credenciales.
* `inventario-service` valida productos del catálogo.
* `ventas-service` consulta productos e inventario.
* `pagos-service` valida ventas.
* `envios-service` valida pagos aprobados.

La comunicación interna se realiza usando nombres de servicio definidos por Docker Compose, por ejemplo:


http://api-gateway:9090
http://usuarios-service:9091
http://catalogo-service:9092


---

## Seguridad

La seguridad se concentra en el `api-gateway`.

Configuración general:

* `/auth/**` queda público para permitir login.
* `/usuarios/email` queda público porque `auth-service` lo requiere para autenticar.
* Todas las demás rutas requieren JWT.
* El token se firma con una clave secreta compartida entre `auth-service` y `api-gateway`.

Ejemplo de header requerido:


Authorization: Bearer TOKEN_GENERADO

---

## Estructura general del proyecto


titaniumarmor/
├── api-gateway/
├── auth-service/
├── usuarios-service/
├── catalogo-service/
├── inventario-service/
├── ventas-service/
├── pagos-service/
├── envios-service/
├── promociones-service/
├── resenas-service/
├── docker-compose.yml
├── build-all.bat
└── clean-all.bat


---

## Consideraciones importantes

* El sistema está preparado para ejecución local mediante Docker Compose.
* MySQL debe estar iniciado en el host antes de levantar los contenedores.
* Los microservicios exponen sus puertos de forma directa para facilitar pruebas, Swagger y defensa técnica.
* Para consumo protegido se recomienda utilizar el API Gateway en el puerto 9090.
* Los endpoints directos de microservicios pueden responder sin pasar por JWT; la validación JWT está centralizada en el Gateway.
* En un entorno productivo, los microservicios internos no deberían exponerse públicamente y deberían quedar accesibles solo desde la red interna.

---

## Estado del proyecto

Funcionalidades implementadas:

* Arquitectura de microservicios.
* API Gateway.
* Rutas centralizadas mediante Gateway.
* Autenticación JWT.
* Servicio de autenticación independiente.
* Comunicación REST con WebClient.
* Persistencia MySQL por microservicio.
* Migraciones Liquibase.
* Swagger/OpenAPI en microservicios principales.
* Pruebas unitarias en servicios principales.
* Despliegue local con Docker Compose.

---
