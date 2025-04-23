# Controlador de Dispositivos con Verificación de Direcciones MAC y Seguridad JWT

## Descripción del Proyecto

Este proyecto es una API REST desarrollada con Spring Boot que permite la gestión de diferentes tipos de dispositivos (como Tablets y otros definidos) y la verificación de sus direcciones MAC con respecto a una lista obtenida externamente. Además, implementa un sistema de seguridad basado en JSON Web Tokens (JWT) para proteger sus endpoints.

El objetivo principal es:

1.  **Gestionar dispositivos:** Permitir la creación, lectura, actualización y eliminación de información sobre diferentes tipos de dispositivos.
2.  **Verificar direcciones MAC:** Comparar las direcciones MAC de los dispositivos almacenados con una lista de direcciones MAC obtenida de una fuente externa (un archivo) para identificar coincidencias y diferencias.
3.  **Implementar seguridad:** Proteger los endpoints de la API mediante autenticación y autorización basadas en JWT y roles de usuario.

## Funcionalidades Principales

### 1. Gestión de Dispositivos

* **Modelo de Dispositivo Padre:** Se define una clase base `Dispositivo` con parámetros comunes a todos los dispositivos (por ejemplo, `nombre`, `macAddress`, `sede`, `ipAddress`).
* **Dispositivos Específicos:** Se pueden crear modelos para diferentes tipos de dispositivos (como `Tablet`,`Pc`,`Móvil`,`Switch`,`Ap`,..) que heredan de la clase `Dispositivo` y pueden tener parámetros adicionales específicos.
* **CRUD de Dispositivos:** La API proporciona endpoints para realizar las operaciones básicas de Crear, Leer, Actualizar y Eliminar (CRUD) para los diferentes tipos de dispositivos.

### 2. Verificación de Direcciones MAC

* **Obtención de Direcciones MAC Externas:** La aplicación tiene la capacidad de leer una lista,en principio desde un archivo .csv, de direcciones MAC, sedes, IPs y otros atributos desde un archivo externo.
* **Comparación de Direcciones MAC:** Se implementa lógica para comparar las direcciones MAC obtenidas del archivo externo con las direcciones MAC de los dispositivos almacenados en la base de datos.
* **Identificación de Coincidencias y Diferencias:** La API puede identificar qué direcciones MAC del archivo coinciden con las de los dispositivos guardados y cuáles no, proporcionando información sobre posibles dispositivos no registrados o cambios en las direcciones MAC.

### 3. Seguridad con JWT

* **Autenticación:** Los usuarios pueden registrarse e iniciar sesión en la API, obteniendo a cambio un token JWT.
* **Tokens JWT:** Los tokens JWT se utilizan para autenticar las peticiones a los endpoints protegidos.
* **Autorización basada en Roles:** Se implementa un sistema de roles ("ADMIN", "USER") para controlar el acceso a diferentes endpoints de la API.
* **Filtro JWT:** Un filtro (`JwtAuthFilter`) intercepta las peticiones entrantes, verifica la validez del token JWT presente en la cabecera `Authorization`, y establece la autenticación en el contexto de seguridad de Spring.
* **Configuración de Seguridad (`SecurityConfig`):** Define las reglas de acceso a los diferentes endpoints:
    * `/auth/login`: Permitido a todos para iniciar sesión.
    * `/auth/register`: Requiere el rol "ADMIN" para registrar nuevos usuarios.
    * Cualquier otra petición (por ejemplo, `/dispositivos/**`, `/tablets/**`): Requiere autenticación.
    * Se ha configurado para permitir el acceso sin autenticación a las rutas de Swagger UI (`/swagger-ui/**` y `/v3/api-docs/**`) para facilitar la documentación y prueba de la API.
* **Servicio de Autenticación (`AuthService`):** Gestiona el registro, inicio de sesión y la generación y renovación de tokens JWT.
* **Servicio JWT (`JwtService`):** Proporciona métodos para generar, extraer información y validar tokens JWT.
* **Detalles del Usuario (`UserDetailsService`):** Carga la información del usuario desde la base de datos para la autenticación, incluyendo sus roles.
* **Repositorio de Tokens (`TokenRepository`):** Gestiona los tokens JWT en la base de datos, permitiendo invalidar tokens durante el logout.
* **Logout:** Se implementa un endpoint `/auth/logout` para invalidar los tokens JWT.

## Tecnologías Utilizadas

* **Spring Boot:** Framework Java para el desarrollo rápido de aplicaciones.
* **Spring Security:** Framework para la seguridad de aplicaciones Spring.
* **JWT (JSON Web Tokens):** Estándar para la creación de tokens de acceso seguros.
* **Hibernate/JPA:** ORM (Mapeo Objeto-Relacional) para la interacción con la base de datos.
* **Base de Datos:**  MySQL.
* **Lombok:** Librería para reducir el código boilerplate (anotaciones como `@Data`, `@Builder`, `@RequiredArgsConstructor`).
* **SLF4j y Logback:** Librerías para el logging.
* **Swagger UI / OpenAPI:** Herramientas para la documentación y prueba de la API.
* **Maven/Gradle:** Herramienta de gestión de dependencias y build.
* **PostMan:** Herramienta para pruebas de funcionamiento.

## Configuración

* **Archivo de Propiedades (`application.properties`):** Contiene la configuración del perfil de la base de datos, seguridad (clave secreta JWT, tiempo de expiración de tokens), datos del admin.
* **Archivo de Propiedades de la base de datos (`application-mysql.properties`):** Contiene la configuración de la base de datos mysql
* **Configuración de Seguridad (`SecurityConfig`):** Define las reglas de autenticación y autorización.
* **Configuración de Beans (`AppConfig`):** Define beans importantes como el `PasswordEncoder`, `AuthenticationManager`, y `UserDetailsService`.

## Endpoints Principales
### Autorizacion:
* `/auth/register` (POST): Registra un nuevo usuario (requiere rol "ADMIN").
* `/auth/login` (POST): Inicia sesión y obtiene un token JWT.
* `/auth/refresh` (POST): Refresca el token de acceso.
* `/auth/logout` (POST): Invalida el token JWT.
### Dispositivos(Padre):
* `/dispositivos/` (GET) Muestra una lista de todos los dispositivos guardados.
* `/dispositivos/{mac}` (GET) Busca un dispositivo con una dirección mac específica.
* `/dispositivos/sede/{sede}` (GET) Busca un dispositivo por su sede
* `/dispositivos/{mac}` (DELETE) Elimina un dispositivo por su dirección mac.
### Dispositivos(Específico):
* `/{nombre dispositivo}` (POST): Crea un dispositivo especifico
* `/{nombre dispositivo}` (GET): Obtiene lista de dispositivos de ese tipo.
* `/{nombre dispositivo}/{macAddress}` (PUT): Actualiza el dispositivo.
* `/{nombre dispositivo}/sede/{sede}`(GET) Obtiene una lista de dispositivos especificos por sede.
### Procesar Archivos :
* `/MacAddressProvider/csv` (POST) Recibe el archivo .csv y lo procesa guardando los datos en la base de datos.
### Comparar MacAddress :
* `/comparator/logs` (GET) Devuelve una lista de los logs con direccion mac que no corresponde a ningún dispositivo guardado.
### Logs :
* `/logs` (GET): Devuelve una lista de los logs guardados.
* `/logs/{sede}` (GET) Devuelve una lista de los logs guardados con una sede específica.
### Swagger-UI:
* `/swagger-ui/index.html#`: Interfaz de Swagger UI para la documentación y prueba de la API (accesible sin autenticación).
* `/v3/api-docs`: Endpoint para obtener la especificación OpenAPI (accesible sin autenticación).


## Pruebas
* Se han realizado pruebas mediante Postman para probar todos los endpoints de la aplicación. 

## Próximos Pasos (Posibles)
* 
* Considerar la implementación de roles más granulares para la autorización.
* Mejorar el manejo de errores y las respuestas de la API.

