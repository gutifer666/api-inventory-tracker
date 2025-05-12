# Guía para ejecutar la aplicación Inventory Tracker API localmente

Esta guía explica paso a paso cómo configurar y ejecutar la aplicación Inventory Tracker API en un entorno local a partir de un archivo .zip.

## Requisitos previos

### Software necesario
- **Java Development Kit (JDK)**: Versión 17 o superior
- **MySQL**: Versión 8.0 o superior
- **Maven**: Versión 3.6 o superior (opcional, ya que el proyecto incluye Maven Wrapper)

## Paso 1: Configuración de la base de datos MySQL

1. **Instalar MySQL** si aún no lo tienes instalado:
    - Descarga MySQL desde [el sitio oficial](https://dev.mysql.com/downloads/mysql/)
    - Sigue las instrucciones de instalación para tu sistema operativo

2. **Ejecutar el script SQL** para crear la base de datos:
    - Abre una terminal o línea de comandos
    - Inicia sesión en MySQL con un usuario con privilegios administrativos:
      ```
      mysql -u root -p
      ```
    - Introduce tu contraseña cuando se te solicite
    - Ejecuta el script SQL que se encuentra en el archivo .zip:
      ```
      source ruta/al/archivo/inventory_tracker_javier.sql
      ```
      (El archivo se encuentra en `src/main/resources/scripts/inventory_tracker_javier.sql` dentro del proyecto)

3. **Verificar la instalación**:
    - El script creará:
        - Un usuario MySQL llamado 'javier' con contraseña 'C12345678!'
        - Una base de datos llamada 'inventory_tracker'
        - Tablas para categorías, proveedores, productos, transacciones y usuarios
        - Datos de ejemplo para todas las tablas

## Paso 2: Descomprimir y preparar el proyecto

1. **Descomprime el archivo .zip** en una ubicación de tu elección

2. **Verifica la estructura del proyecto**:
    - Asegúrate de que la estructura del proyecto incluye las carpetas `src`, `pom.xml` y los scripts de Maven Wrapper (`mvnw` y `mvnw.cmd`)

## Paso 3: Configurar la aplicación

1. **Configuración de la base de datos**:

   ### Para desarrollo local con MySQL local:
    - Abre el archivo `src/main/resources/application.properties`
    - Verifica que la configuración de la base de datos coincida con tu entorno:
      ```properties
      spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/inventory_tracker
      spring.datasource.username=javier
      spring.datasource.password=C12345678!
      ```
    - Si has modificado algún parámetro durante la creación de la base de datos (nombre de usuario, contraseña, nombre de la base de datos), actualiza este archivo en consecuencia

   ### Para desarrollo con MySQL en Railway (rama railway):
    - Crea un archivo `.env` en la raíz del proyecto con las siguientes variables:
      ```
      MYSQL_DATABASE=railway
      MYSQL_ROOT_PASSWORD=tu_contraseña_de_railway
      MYSQLDATABASE=${MYSQL_DATABASE}
      MYSQLHOST=${RAILWAY_PRIVATE_DOMAIN}
      MYSQLPASSWORD=${MYSQL_ROOT_PASSWORD}
      MYSQLPORT=3306
      MYSQLUSER=root
      ```
    - Este archivo `.env` no se subirá al repositorio por seguridad (está incluido en `.gitignore`)
    - En producción, estas variables se configurarán directamente en Railway

## Paso 4: Ejecutar la aplicación

1. **Usando Maven Wrapper** (recomendado, no requiere instalación de Maven):
    - Abre una terminal o línea de comandos
    - Navega hasta la carpeta raíz del proyecto (donde se encuentra el archivo `pom.xml`)
    - En sistemas Unix/Linux/macOS:
      ```
      ./mvnw spring-boot:run
      ```
    - En Windows:
      ```
      mvnw.cmd spring-boot:run
      ```

2. **Usando Maven** (si ya lo tienes instalado):
    - Abre una terminal o línea de comandos
    - Navega hasta la carpeta raíz del proyecto
    - Ejecuta:
      ```
      mvn spring-boot:run
      ```

3. **Usando tu IDE** (IntelliJ IDEA, Eclipse, VS Code, etc.):
    - Importa el proyecto como un proyecto Maven
    - Busca la clase `InventoryTrackerApiApplication.java` en `src/main/java/com/javiergutierrez/inventory_tracker_api/`
    - Ejecuta esta clase como una aplicación Java


## Paso 5: Verificar que la aplicación está funcionando

1. **Acceder a la API**:
    - La aplicación se ejecutará en `http://localhost:8080` por defecto
    - Puedes acceder a la documentación de la API en `http://localhost:8080/swagger-ui.html`

2. **Credenciales de usuario**:
    - El script SQL ha creado varios usuarios de prueba:
        - Administrador: username `administrador_prueba`, password `password` (encriptada en la base de datos)
        - Empleado: username `empleado_prueba`, password `password`
        - Cliente: username `cliente_prueba`, password `password`

## Solución de problemas comunes

1. **Error de conexión a la base de datos**:
    - Verifica que MySQL esté en ejecución
    - Comprueba que el usuario y la contraseña en `application.properties` sean correctos
    - Asegúrate de que la base de datos `inventory_tracker` exista

2. **Error de versión de Java**:
    - Verifica que estás usando Java 17 o superior:
      ```
      java -version
      ```
    - Si tienes múltiples versiones de Java instaladas, asegúrate de que tu JAVA_HOME apunte a la versión correcta

3. **Errores de Maven**:
    - Si encuentras errores relacionados con dependencias, intenta limpiar el caché de Maven:
      ```
      ./mvnw clean
      ```
      o
      ```
      mvn clean
      ```

## Información adicional

- Esta aplicación utiliza Spring Boot 3.4.3
- Incluye Spring Security para la autenticación y autorización
- Utiliza JWT para la gestión de tokens
- La documentación de la API está disponible a través de Swagger UI

## Despliegue en Railway

### Rama `railway`

- La rama `railway` está configurada para ser desplegada en Railway
- Esta rama utiliza una base de datos MySQL alojada en Railway
- Las variables de entorno necesarias para la conexión a la base de datos se configuran directamente en Railway
- Para desarrollo local usando la base de datos de Railway, se debe crear un archivo `.env` con las credenciales necesarias (ver Paso 3)

