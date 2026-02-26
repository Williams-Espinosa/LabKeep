# 🔬 LabKeep

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-Android-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Javalin](https://img.shields.io/badge/Javalin-6.x-0083D3?style=for-the-badge&logo=java&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![Cloudinary](https://img.shields.io/badge/Cloudinary-Storage-3448C5?style=for-the-badge&logo=cloudinary&logoColor=white)
![HikariCP](https://img.shields.io/badge/HikariCP-Connection_Pool-00BFFF?style=for-the-badge)

Sistema de gestión de préstamos de dispositivos para laboratorio. Permite registrar equipos, categorizarlos, controlar su estado y llevar un historial completo de préstamos y devoluciones.

---

## 🚀 Características

- 📦 **CRUD completo** de dispositivos y categorías
- 🔄 **Control de estado** de dispositivos (`disponible` / `prestado`)
- 📋 **Historial de préstamos** por dispositivo
- 🖼️ **Gestión de imágenes** con Cloudinary
- 🔐 **Autenticación** con registro y login (contraseñas encriptadas con BCrypt)
- 🌐 **CORS** habilitado para cualquier origen
- 🔌 **Connection pooling** con HikariCP

---

## 🏗️ Arquitectura

El proyecto sigue una arquitectura en capas bien definida:

```
HTTP Request
     │
     ▼
 Router (Routes*)          → Define los endpoints
     │
     ▼
 Controller (*Controller)  → Maneja la request/response HTTP
     │
     ▼
 Service (*Service)        → Lógica de negocio y validaciones
     │
     ▼
 Repository (*Repository)  → Acceso a base de datos (JDBC puro)
     │
     ▼
 PostgreSQL
```

---

## 🛠️ Stack Tecnológico

| Tecnología | Uso |
|---|---|
| **Java 17+** | Lenguaje principal |
| **Javalin 6** | Framework HTTP ligero para la API REST |
| **PostgreSQL** | Base de datos relacional |
| **HikariCP** | Pool de conexiones JDBC de alto rendimiento |
| **Cloudinary** | Almacenamiento y gestión de imágenes en la nube |
| **BCrypt** | Encriptación de contraseñas |
| **dotenv-java** | Manejo de variables de entorno desde `.env` |

---

## 📁 Estructura del Proyecto

```
src/main/java/williamsel/
├── Main.java                        # Punto de entrada, configuración de Javalin
├── config/
│   ├── DBconfig.java                # Configuración de HikariCP y conexión a PostgreSQL
│   └── Inicio.java                  # Wiring de dependencias (manual DI)
├── model/
│   ├── Usuario.java
│   ├── Categoria.java
│   ├── Dispositivo.java
│   └── Prestamo.java
├── repository/
│   ├── UsuarioRepository.java
│   ├── CategoriaRepository.java
│   ├── DispositivoRepository.java
│   └── PrestamoRepository.java
├── service/
│   ├── UsuarioService.java
│   ├── CategoriaService.java
│   ├── DispositivoService.java
│   ├── PrestamoService.java
│   └── ClouudinaryService.java
├── controller/
│   ├── UsuarioController.java
│   ├── CategoriaController.java
│   ├── DispositivoController.java
│   └── PrestamoController.java
└── routers/
    ├── RoutesUsuario.java
    ├── RouteCategoria.java
    ├── RoutesDispositivo.java
    └── RoutesPrestamos.java
```

---

## ⚙️ Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/Williams-Espinosa/labkeep.git
cd labkeep
```

### 2. Configurar variables de entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=labkeep
DB_USER=tu_usuario
DB_PASS=tu_contraseña

CLOUDINARY_CLOUD_NAME=tu_cloud_name
CLOUDINARY_API_KEY=tu_api_key
CLOUDINARY_API_SECRET=tu_api_secret
```

### 3. Crear la base de datos

Ejecuta el schema SQL en PostgreSQL:

```bash
psql -U tu_usuario -d labkeep -f schema.sql
```

### 4. Compilar y ejecutar

```bash
mvn compile exec:java
```

La API estará disponible en `http://localhost:7001`

---

## 🗄️ Base de Datos

```sql
usuario
  ├── id           SERIAL PK
  ├── correo       VARCHAR(150) UNIQUE
  └── contrasena   VARCHAR(255)

categoria
  ├── id           SERIAL PK
  └── nombre       VARCHAR(100) UNIQUE

dispositivo
  ├── id               SERIAL PK
  ├── nombre           VARCHAR(150)
  ├── categoria_id     FK → categoria.id
  ├── estado           CHECK ('disponible' | 'prestado')
  ├── imagen_url       TEXT
  ├── imagen_public_id TEXT
  └── fecha_creacion   TIMESTAMP DEFAULT NOW()

prestamo
  ├── id                SERIAL PK
  ├── dispositivo_id    FK → dispositivo.id
  ├── usuario_id        FK → usuario.id
  ├── fecha_prestamo    TIMESTAMP DEFAULT NOW()
  └── fecha_devolucion  TIMESTAMP (NULL = activo)
```

---

## 📡 Endpoints de la API

Base URL: `http://localhost:7001`

### 👤 Usuarios

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| `POST` | `/registro` | Registrar usuario | `{ "correo": "", "contrasena": "" }` |
| `POST` | `/login` | Iniciar sesión | `{ "correo": "", "contrasena": "" }` |

### 🗂️ Categorías

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| `GET` | `/categorias` | Listar todas | — |
| `GET` | `/categorias/{id}` | Obtener por ID | — |
| `POST` | `/categorias` | Crear categoría | `{ "nombre": "" }` |
| `PUT` | `/categorias/{id}` | Actualizar | `{ "nombre": "" }` |
| `DELETE` | `/categorias/{id}` | Eliminar | — |

### 💻 Dispositivos

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| `GET` | `/dispositivos` | Listar todos | — |
| `GET` | `/dispositivos?q=texto` | Buscar por nombre o categoría | — |
| `GET` | `/dispositivos/{id}` | Obtener por ID | — |
| `POST` | `/dispositivos` | Crear dispositivo | `form-data`: `nombre`, `categoria_id`, `imagen` |
| `PUT` | `/dispositivos/{id}` | Actualizar datos | `{ "nombre": "", "categoriaId": 1, "estado": "" }` |
| `PUT` | `/dispositivos/{id}/imagen` | Actualizar imagen | `form-data`: `imagen` |
| `PATCH` | `/dispositivos/{id}/estado` | Cambiar estado | `{ "estado": "disponible" }` |
| `DELETE` | `/dispositivos/{id}` | Eliminar | — |

### 📦 Préstamos

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| `GET` | `/prestamos` | Listar todos | — |
| `GET` | `/prestamos/dispositivo/{id}` | Historial por dispositivo | — |
| `POST` | `/prestamos/prestar` | Registrar préstamo | `{ "dispositivo_id": 1 }` |
| `PUT` | `/prestamos/devolver/{dispositivoId}` | Registrar devolución | — |

---

---

## 📱 Aplicación Android (LabKeep UI)

Cliente móvil desarrollado en **Kotlin** con **Jetpack Compose** y arquitectura **Clean Architecture** por features.

### 🛠️ Stack Android

| Tecnología | Uso |
|---|---|
| **Kotlin** | Lenguaje principal |
| **Jetpack Compose** | UI declarativa |
| **ViewModel + UIState** | Gestión de estado por pantalla |
| **Retrofit** | Cliente HTTP para consumir la API REST |
| **Navigation Compose** | Navegación entre pantallas |
| **Clean Architecture** | Separación en capas: `data`, `domain`, `presentacion` |

### 🏗️ Arquitectura Android

Cada feature sigue la misma estructura de capas:

```
feature/
├── data/
│   ├── datasource/
│   │   ├── api/          → Interface Retrofit
│   │   ├── mapper/       → DTO → Entidad de dominio
│   │   └── models/       → DTOs (respuesta de la API)
│   └── repositories/     → Implementación del repositorio
├── di/
│   └── *Provider.kt      → Inyección de dependencias manual
├── domain/
│   ├── entities/         → Modelos de negocio
│   ├── repositories/     → Interface del repositorio
│   └── usescases/        → Casos de uso (lógica de negocio)
└── presentacion/
    ├── screens/          → Pantalla Composable + UIState
    ├── components/       → Componentes reutilizables
    └── viewmodels/       → ViewModel + ViewModelFactory
```

### 📁 Estructura del Proyecto Android

```
app/src/main/java/com/williamsel/labkeep/
├── MainActivity.kt
├── core/
│   ├── navigation/
│   │   └── NavigationWrapper.kt       # Rutas: Login, Inventario, Descripcion, NuevoDispositivo
│   └── network/
│       └── RetrofitClient.kt          # Configuración base de Retrofit
├── features/
│   ├── login/                         # Autenticación de usuario
│   ├── inventario/                    # Listado de dispositivos
│   ├── descripciondispositivo/        # Detalle de un dispositivo
│   ├── nuevodispositivo/              # Crear dispositivo (con imagen)
│   ├── editardispositivo/             # Editar datos del dispositivo
│   └── eliminardispositivo/           # Eliminar dispositivo
└── ui/theme/
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

### 🗺️ Pantallas

| Pantalla | Descripción |
|---|---|
| `LoginScreen` | Inicio de sesión con correo y contraseña |
| `InventarioScreen` | Lista todos los dispositivos disponibles |
| `DescripcionDispositivoScreen` | Detalle completo de un dispositivo |
| `NuevoDispositivoScreen` | Formulario para registrar un nuevo dispositivo |
| `EditarDispositivoScreen` | Editar nombre, categoría o estado |
| `EliminarDispositivoScreen` | Confirmación y eliminación de dispositivo |

### ⚙️ Configuración Android

En `RetrofitClient.kt` apunta la `BASE_URL` a tu servidor:

```kotlin
private const val BASE_URL = "http://TU_IP:7001/"
```

> ⚠️ Si pruebas en emulador usa `10.0.2.2:7001`. Si usas dispositivo físico, usa la IP local de tu máquina. ⚠️

---

## 📄 Licencia

Este proyecto es de uso académico/personal.
---
> 💻 *"Codifica el futuro, crea el presente"*  
> Desarrollado por **Williams-Espinosa** © 2026 — Todos los derechos reservados.
