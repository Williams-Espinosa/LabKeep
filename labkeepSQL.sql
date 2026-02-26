CREATE TABLE usuario (
    id          SERIAL PRIMARY KEY,
    correo      VARCHAR(150) NOT NULL UNIQUE,
    contrasena  VARCHAR(255) NOT NULL
);

CREATE TABLE categoria (
    id     SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE dispositivo (
    id               SERIAL PRIMARY KEY,
    nombre           VARCHAR(150)        NOT NULL,
    categoria_id     INT                 NOT NULL REFERENCES categoria(id) ON DELETE RESTRICT,
    estado           VARCHAR(20)         NOT NULL DEFAULT 'disponible'
                         CHECK (estado IN ('disponible', 'prestado')),
    imagen_url       TEXT,
    imagen_public_id TEXT,
    fecha_creacion   TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE prestamo (
    id                SERIAL PRIMARY KEY,
    dispositivo_id    INT       NOT NULL REFERENCES dispositivo(id) ON DELETE CASCADE,
    usuario_id        INT       NOT NULL REFERENCES usuario(id)     ON DELETE CASCADE,
    fecha_prestamo    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion  TIMESTAMP
);