# Holidays App - Backend

## Tecnologías utilizadas

- **Spring Boot**
- **Spring Data JPA**
- **Flyway**
- **PostgreSQL**
- **Docker**
- **Visual Studio Code** (opcional, recomendado como IDE)

Este módulo contiene la API REST desarrollada con Spring Boot. Se encarga de gestionar la lógica del negocio y la conexión con la base de datos PostgreSQL.

## Requisitos Previos

Asegúrate de tener instalado lo siguiente antes de iniciar el backend:

- **Java 17+ (Se recomienda utilizar el SDK de JAVA Correto 17 incluso si se puede la 22 mejor)**
- **Docker** o **Rancher Desktop** para levantar la base de datos
- **IntelliJ IDEA** (opcional, pero recomendado)

## Configuración de la Base de Datos

Para facilitar el entorno de desarrollo, se utiliza Docker para lanzar una instancia de PostgreSQL.

Desde la raíz del proyecto (donde se encuentra el archivo `docker-compose.yml`), ejecuta:

```bash
docker-compose up -d
```

Las credenciales por defecto son:
- Usuario: `postgres`
- Contraseña: `gft`
- Base de datos: `holidays_app`

⚠️ Asegúrate de que la base de datos `holidays_app` exista antes de arrancar el backend. Puedes crearla desde PgAdmin o con cualquier cliente de PostgreSQL.

Si deseas modificar estos valores, edita el archivo `docker-compose.yml`.

## Ejecución del Backend

- Abre el proyecto en IntelliJ IDEA.
- Dirígete al módulo `boot`.
- Ejecuta la clase `BootApplication`.

Al iniciar:
- Se levanta el servidor backend.
- Se aplican automáticamente las migraciones con **Flyway**, creando las tablas necesarias en la base de datos si no existen.

El backend quedará disponible en el puerto configurado (por defecto suele ser `8080`).