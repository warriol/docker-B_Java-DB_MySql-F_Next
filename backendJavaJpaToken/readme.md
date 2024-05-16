# Configuración para prueba con Firebase y base de datos en Spring Boot

Antes de poder probar la aplicación, es necesario configurar las credenciales de Firebase y la base de datos en Spring Boot. A continuación se detallan los pasos necesarios:

## Firebase

1. Si no tiene un proyecto en Firebase, cree uno desde la consola de Firebase.
2. Obtenga las credenciales del proyecto en Firebase (archivo JSON).
3. Cree un archivo llamado `firebase-credential.json` en la carpeta `src/main/resources` de su proyecto Spring Boot.
4. Pegue las credenciales obtenidas de Firebase en el archivo `firebase-credential.json`.
5. Si no realiza estos pasos, la verificación con Firebase fallará al ejecutar la aplicación.

## Configuración de la base de datos

### MySQL (Configuración predeterminada)

1. La aplicación está configurada para usar MySQL por defecto.
2. Asegúrese de tener el controlador MySQL instalado en su proyecto.
3. Configure las credenciales de su base de datos MySQL en el archivo `application.yml`.
4. Si está utilizando MySQL, no es necesario cambiar la configuración del controlador.

### PostgreSQL (Opcional)

1. Si prefiere usar PostgreSQL en lugar de MySQL, debe instalar el controlador PostgreSQL en su proyecto.
2. Actualice las dependencias y configuraciones necesarias para PostgreSQL en el archivo `pom.xml` y `application.yml`.
3. Inserte las credenciales de su base de datos PostgreSQL en el archivo `application.yml`.
