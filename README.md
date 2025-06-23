# Holidays App - Guía de Configuración

### Tecnologías utilizadas

<p>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/angularjs/angularjs-original.svg" alt="Angular" width="40" height="40"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" alt="JavaScript" width="40" height="40"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/typescript/typescript-original.svg" alt="TypeScript" width="40" height="40"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg" alt="HTML5" width="40" height="40"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg" alt="CSS3" width="40" height="40"/>
</p>


## Requisitos Previos

Antes de comenzar, hay que asegurarse de tener instaladas las siguientes herramientas en tu sistema:

1. **Node.js y npm**  
   Son necesarios para ejecutar el frontend desarrollado en Angular.  
   Puedes descargar Node.js desde su sitio oficial: [https://nodejs.org/](https://nodejs.org/)  
   Para comprobar que está instalado correctamente, ejecuta en la terminal:
   ```bash
   node -v
   npm -v
   ```

2. **Angular CLI**  
   Es la herramienta de línea de comandos para trabajar con proyectos Angular.  
   Se instala globalmente con el siguiente comando:
   ```bash
   npm install -g @angular/cli
   ```

3. **Docker o Rancher**  
   Necesitarás uno de estos sistemas para ejecutar los contenedores del backend (especialmente la base de datos).  
   Enlace de descarga de Docker [https://www.docker.com/](https://www.docker.com/).
   Enlace de descarga de Rancher https://github.com/rancher-sandbox/rancher-desktop/releases

5. **IDE recomendados**
   - **Frontend**: [Visual Studio Code](https://code.visualstudio.com/)
   - **Backend**: [IntelliJ IDEA](https://www.jetbrains.com/idea/)

---

## 🚀 Puesta en Marcha

A continuación, te explicamos paso a paso cómo levantar todo el entorno de desarrollo, tanto frontend como backend.

### 1. Clonar el repositorio

Primero, clona el repositorio del proyecto en tu máquina:

```bash
git clone https://github.com/lhachete/Holidays-App-GFT.git
cd Holidays-App-GFT
```

### 2. Levantar la base de datos PostgreSQL con Docker

Dentro del directorio raíz del backend, ejecuta el siguiente comando para levantar el servicio de PostgreSQL:

```bash
docker-compose up -d
```

Esto iniciará un contenedor con la base de datos necesaria para el backend.  Las credenciales por defecto configuradas en el `docker-compose.yml` son:

> 🔐 Las credenciales por defecto para PostgreSQL son:  
> Usuario: `postgres`  
> Contraseña: `gft`  
> Base de datos: `holidays_app`  
>  
> Se pueden modificar estos valores en el archivo `docker-compose.yml`.

	⚠️ Asegúrate de que la base de datos `holidays_app` exista antes de arrancar el      backend.  
	Puedes crearla fácilmente usando PgAdmin o cualquier cliente para PostgreSQL.

### 3. Levantar el Backend con Spring Boot

Abre el proyecto en IntelliJ IDEA y ejecuta la clase principal `BootApplication`, que se encuentra en el módulo `boot`.

Esto realizará varias acciones automáticamente:

- Inicia el servidor backend en el puerto configurado.
    
- Ejecuta las migraciones con **Flyway**, generando todas las tablas necesarias en la base de datos si aún no existen.

### 4. Instalar dependencias del frontend

Accede a la carpeta del frontend y ejecuta la instalación de dependencias con:

```bash
npm install
```

Este paso descargará todos los paquetes y módulos necesarios definidos en `package.json`.

### 5. Levantar la aplicación Angular

Una vez instaladas las dependencias, arrancar la aplicación con:

```bash
ng serve
```

Angular iniciará un servidor de desarrollo, y podrás acceder a la aplicación desde tu navegador en: [http://localhost:4200](http://localhost:4200)

---

## Aplicación en funcionamiento

- **Frontend**: Angular, accesible en `http://localhost:4200`.
- **Backend**: Spring Boot conectado a PostgreSQL.
- **Base de Datos**: `holidays_app`, inicializada automáticamente por Flyway al arrancar el backend.
