# Holidays App

## 🛠️ Requisitos Previos

Antes de comenzar, hay que asegurarse de tener instaladas las siguientes herramientas en tu sistema:

1. **Node.js**  
   Enlace para descargarlo desde [https://nodejs.org/](https://nodejs.org/).  
   Para verificar la instalación:
   ```bash
   node -v
   npm -v
   ```

2. **Angular CLI**  
   Para instalarlo globalmente:
   ```bash
   npm install -g @angular/cli
   ```

3. **Docker o Rancher**  
   Es necesario tener instalado Docker (o Rancher Desktop) para levantar el backend con `docker-compose`.  
   Enlace de descarga de Docker [https://www.docker.com/](https://www.docker.com/).

4. **IDE recomendados**
   - **Frontend**: [Visual Studio Code](https://code.visualstudio.com/)
   - **Backend**: [IntelliJ IDEA](https://www.jetbrains.com/idea/)

---

## 🚀 Puesta en Marcha

A continuación los pasos para poner en funcionamiento toda la aplicación:

### 1. Clonar el repositorio

```bash
git clone <URL-del-repositorio>
cd <nombre-del-proyecto>
```

### 2. Levantar la base de datos PostgreSQL con Docker

Dentro del directorio raíz del backend, ejecutar el docker compose o con el comando:

```bash
docker-compose up -d
```

> 🔐 Las credenciales por defecto para PostgreSQL son:  
> Usuario: `postgres`  
> Contraseña: `gft`  
> Base de datos: `holidays_app`  
>  
> Se pueden modificar estos valores en el archivo `docker-compose.yml`.

**Nota:** La base de datos `holidays_app` debe existir previamente en el sistema PostgreSQL. Puedes crearla manualmente desde PgAdmin o cualquier cliente PostgreSQL antes de ejecutar el backend.

### 3. Ejecutar el backend (Spring Boot)

Abre el proyecto en IntelliJ IDEA y ejecutar la clase `BootApplication` dentro del modulo de **boot**.

Esto:
- Arrancará el servidor backend.
- Aplicará las migraciones de Flyway para crear automáticamente todas las tablas necesarias en PostgreSQL.

### 4. Instalar dependencias del frontend

Desde la carpeta del frontend ejecutar el comando:

```bash
npm install
```

### 5. Levantar la aplicación Angular

Una vez instaladas las dependencias, arrancar la aplicación con:

```bash
ng serve
```

La aplicación estará disponible en [http://localhost:4200](http://localhost:4200)

---

## ✅ Aplicación en funcionamiento

- **Frontend**: Angular, servido por defecto en el puerto `4200`.  
- **Backend**: Spring Boot, con conexión a base de datos PostgreSQL (Docker).  
- **Base de datos**: `holidays_app`, migrada automáticamente por Flyway.
