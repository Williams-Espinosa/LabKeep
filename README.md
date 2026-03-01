# 🔬 LabKeep

![Kotlin](https://img.shields.io/badge/Kotlin-Android-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Ktor](https://img.shields.io/badge/Ktor-3.x-087CFA?style=for-the-badge&logo=kotlin&logoColor=white)
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
- 📄 **Documentación interactiva** con Swagger UI *(solo Ktor)*

---

## 🖥️ Backend — Dos implementaciones disponibles

Este proyecto cuenta con **dos versiones del backend**. Ambas comparten la misma base de datos PostgreSQL y la misma funcionalidad, pero difieren en tecnología y arquitectura.

| | Javalin (v1) | Ktor (v2) |
|---|---|---|
| **Lenguaje** | Java 17+ | Kotlin 2.1 |
| **Framework** | Javalin 6 | Ktor 3.x |
| **ORM** | JDBC puro | Exposed ORM |
| **Arquitectura** | Capas (MVC) | Hexagonal |
| **Puerto** | `7001` | `8080` |
| **Swagger** | ❌ | ✅ `/swagger` |
| **Build** | Gradle | Gradle Kotlin DSL |

> ⚠️ **Importante para la app Android:** Si usas el backend **Javalin**, los endpoints de autenticación son `/login` y `/registro`. Si usas **Ktor**, son `/usuarios/login` y `/usuarios/registro`. Actualiza `RetrofitClient.kt` según cuál uses. Ver sección [Configuración Android](#️-configuración-android).

---

## 🟦 Backend v1 — Javalin + Java (Arquitectura en Capas)

### Arquitectura

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

### Stack

| Tecnología | Uso |
|---|---|
| **Java 17+** | Lenguaje principal |
| **Javalin 6** | Framework HTTP |
| **PostgreSQL** | Base de datos relacional |
| **HikariCP** | Pool de conexiones |
| **Cloudinary** | Almacenamiento de imágenes |
| **BCrypt** | Encriptación de contraseñas |
| **dotenv-java** | Variables de entorno |

### Estructura

```
src/main/java/williamsel/
├── Main.java
├── config/
│   ├── DBconfig.java
│   └── Inicio.java
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

### Ejecutar

```bash
./gradlew run
```

API disponible en: `http://localhost:7001`

### Endpoints — Javalin

Base URL: `http://localhost:7001`

#### 👤 Usuarios
| Método | Endpoint | Body |
|--------|----------|------|
| `POST` | `/registro` | `{ "correo": "", "contrasena": "" }` |
| `POST` | `/login` | `{ "correo": "", "contrasena": "" }` |

#### 🗂️ Categorías
| Método | Endpoint | Body |
|--------|----------|------|
| `GET` | `/categorias` | — |
| `GET` | `/categorias/{id}` | — |
| `POST` | `/categorias` | `{ "nombre": "" }` |
| `PUT` | `/categorias/{id}` | `{ "nombre": "" }` |
| `DELETE` | `/categorias/{id}` | — |

#### 💻 Dispositivos
| Método | Endpoint | Body |
|--------|----------|------|
| `GET` | `/dispositivos` | — |
| `GET` | `/dispositivos?q=texto` | — |
| `GET` | `/dispositivos/{id}` | — |
| `POST` | `/dispositivos` | `form-data`: `nombre`, `categoria_id`, `imagen` |
| `PUT` | `/dispositivos/{id}` | `{ "nombre": "", "categoriaId": 1, "estado": "" }` |
| `PUT` | `/dispositivos/{id}/imagen` | `form-data`: `imagen` |
| `PATCH` | `/dispositivos/{id}/estado` | `{ "estado": "disponible" }` |
| `DELETE` | `/dispositivos/{id}` | — |

#### 📦 Préstamos
| Método | Endpoint | Body |
|--------|----------|------|
| `GET` | `/prestamos` | — |
| `GET` | `/prestamos/dispositivo/{id}` | — |
| `POST` | `/prestamos/prestar` | `{ "dispositivo_id": 1 }` |
| `PUT` | `/prestamos/devolver/{dispositivoId}` | — |

---

## 🟣 Backend v2 — Ktor + Kotlin (Arquitectura Hexagonal)

### Arquitectura

```
src/main/kotlin/com/labkeep/
│
├── domain/                         ← NÚCLEO (sin dependencias externas)
│   ├── model/
│   │   ├── Models.kt               ← Entidades de dominio
│   │   └── Exceptions.kt           ← Excepciones de dominio
│   └── port/
│       ├── input/                  ← Puertos de entrada (interfaces de casos de uso)
│       └── output/                 ← Puertos de salida (interfaces de repos y storage)
│
├── application/                    ← CASOS DE USO
│   ├── usecase/
│   │   ├── UsuarioUseCases.kt
│   │   ├── CategoriaUseCases.kt
│   │   ├── DispositivoUseCases.kt
│   │   └── PrestamoUseCases.kt
│   └── dto/
│       └── Dtos.kt
│
└── infrastructure/                 ← ADAPTADORES
    ├── adapter/
    │   ├── http/
    │   │   ├── routes/             ← Rutas Ktor por módulo
    │   │   └── plugins/            ← Serialización, CORS, StatusPages, Swagger
    │   ├── persistence/
    │   │   └── repository/         ← Repositorios con Exposed ORM
    │   └── cloudinary/             ← Adaptador Cloudinary
    └── config/
        ├── DatabaseConfig.kt
        └── AppContainer.kt         ← Wiring de dependencias
```

### Stack

| Tecnología | Uso |
|---|---|
| **Kotlin 2.1** | Lenguaje principal |
| **Ktor 3.x** | Framework HTTP |
| **Exposed ORM** | Acceso a base de datos |
| **PostgreSQL** | Base de datos relacional |
| **HikariCP** | Pool de conexiones |
| **Cloudinary** | Almacenamiento de imágenes |
| **BCrypt** | Encriptación de contraseñas |
| **Swagger UI** | Documentación en `/swagger` |
| **Kotlinx Serialization** | Serialización JSON |

### Ejecutar

```bash
./gradlew run
```

API disponible en: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger`

### Endpoints — Ktor

Base URL: `http://localhost:8080`

#### 👤 Usuarios
| Método | Endpoint | Body |
|--------|----------|------|
| `POST` | `/usuarios/registro` | `{ "correo": "", "contrasena": "" }` |
| `POST` | `/usuarios/login` | `{ "correo": "", "contrasena": "" }` |

#### 🗂️ Categorías
| Método | Endpoint | Body |
|--------|----------|------|
| `GET` | `/categorias` | — |
| `GET` | `/categorias/{id}` | — |
| `POST` | `/categorias` | `{ "nombre": "" }` |
| `PUT` | `/categorias/{id}` | `{ "nombre": "" }` |
| `DELETE` | `/categorias/{id}` | — |

#### 💻 Dispositivos
| Método | Endpoint | Body |
|--------|----------|------|
| `GET` | `/dispositivos?q=...` | — |
| `GET` | `/dispositivos/{id}` | — |
| `POST` | `/dispositivos` | `form-data`: `nombre`, `categoria_id`, `imagen` |
| `PUT` | `/dispositivos/{id}` | `{ "nombre": "", "categoriaId": 1, "estado": "" }` |
| `PUT` | `/dispositivos/{id}/imagen` | `form-data`: `imagen` |
| `PATCH` | `/dispositivos/{id}/estado` | `{ "estado": "disponible" }` |
| `DELETE` | `/dispositivos/{id}` | — |

#### 📦 Préstamos
| Método | Endpoint | Body |
|--------|----------|------|
| `GET` | `/prestamos` | — |
| `GET` | `/prestamos/dispositivo/{id}` | — |
| `POST` | `/prestamos/prestar` | `{ "dispositivoId": 1, "usuarioId": 2 }` |
| `PUT` | `/prestamos/devolver/{dispositivoId}` | — |

### Añadir un nuevo módulo

1. **Dominio** → modelo en `Models.kt` + excepciones si aplica
2. **Puertos** → interfaces en `InputPorts.kt` y `OutputPorts.kt`
3. **Aplicación** → casos de uso en `XxxUseCases.kt`
4. **Infraestructura** → repositorio + tabla Exposed + rutas Ktor
5. **Config** → registrar en `AppContainer.kt` y `Application.kt`

---

## 🗄️ Base de Datos

Compartida por ambos backends.

```sql
usuario
  ├── id           SERIAL PK
  ├── correo       VARCHAR(255) UNIQUE
  └── contrasena   VARCHAR(255)

categoria
  ├── id           SERIAL PK
  └── nombre       VARCHAR(100)

dispositivo
  ├── id               SERIAL PK
  ├── nombre           VARCHAR(200)
  ├── categoria_id     FK → categoria.id
  ├── estado           VARCHAR(50) DEFAULT 'disponible'
  ├── imagen_url       VARCHAR(500)
  ├── imagen_public_id VARCHAR(200)
  └── fecha_creacion   TIMESTAMP DEFAULT NOW()

prestamo
  ├── id                SERIAL PK
  ├── dispositivo_id    FK → dispositivo.id
  ├── usuario_id        FK → usuario.id
  ├── fecha_prestamo    TIMESTAMP DEFAULT NOW()
  └── fecha_devolucion  TIMESTAMP (NULL = activo)
```

---

## ⚙️ Variables de Entorno

Ambos backends usan el mismo `.env`:

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
│   └── usescases/        → Casos de uso
└── presentacion/
    ├── screens/          → Pantalla Composable + UIState
    ├── components/       → Componentes reutilizables
    └── viewmodels/       → ViewModel + ViewModelFactory
```

### 📁 Estructura

```
app/src/main/java/com/williamsel/labkeep/
├── MainActivity.kt
├── core/
│   ├── navigation/
│   │   └── NavigationWrapper.kt
│   └── network/
│       └── RetrofitClient.kt
├── features/
│   ├── login/
│   ├── inventario/
│   ├── descripciondispositivo/
│   ├── nuevodispositivo/
│   ├── editardispositivo/
│   └── eliminardispositivo/
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

En `RetrofitClient.kt` configura la `BASE_URL` según el backend que uses:

```kotlin
// ✅ Si usas Javalin (v1) — sin cambios en endpoints de auth
private const val BASE_URL = "http://TU_IP:7001/"

// ✅ Si usas Ktor (v2) — debes actualizar los endpoints de auth en la app
private const val BASE_URL = "http://TU_IP:8080/"
```

> ⚠️ **Si cambias a Ktor**, actualiza en tu feature de login los endpoints:
> - `/login` → `/usuarios/login`
> - `/registro` → `/usuarios/registro`
>
> El resto de endpoints (categorías, dispositivos, préstamos) son iguales en ambos backends.

> ⚠️ Si pruebas en emulador usa `10.0.2.2` como IP. Si usas dispositivo físico, ambos deben estar en la misma red WiFi.

---

## 📄 Licencia

![Licencia: Privada](https://img.shields.io/badge/Licencia-No_Comercial-red?style=for-the-badge)
---

## Propiedad Intelectual

Todo el contenido de este repositorio, incluyendo el código fuente, diseño gráfico, está protegido por las leyes de propiedad intelectual.

* **Uso permitido:** Consulta, aprendizaje y exhibición personal.
* **Prohibiciones:** Se prohíbe estrictamente la copia parcial o total para uso comercial, la reventa del software.

> ###  *"Crea el presente, codifica el futuro."*
> © 2026 **Williams-Espinosa**. Todos los derechos reservados.

---
