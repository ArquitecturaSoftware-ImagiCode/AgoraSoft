CREATE SCHEMA IF NOT EXISTS inventario;
GRANT ALL PRIVILEGES ON SCHEMA inventario TO postgres;
ALTER DATABASE "AgoraSoft" SET search_path TO inventario, public;
CREATE TABLE IF NOT EXISTS inventario.usuario (
                                                  id VARCHAR(255) NOT NULL PRIMARY KEY,
    activated BOOLEAN DEFAULT FALSE,
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    rol VARCHAR(255),
    organizacion VARCHAR(255),
    correo VARCHAR(255)
    );

GRANT ALL PRIVILEGES ON TABLE inventario.usuario TO postgres;

DO $$
BEGIN
  RAISE NOTICE 'Schema inventario y tabla usuario creados exitosamente';
END $$;

CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(50),
    activo BOOLEAN DEFAULT TRUE
);

GRANT ALL PRIVILEGES ON TABLE clientes TO postgres;

DO $$
BEGIN
  RAISE NOTICE 'Tabla clientes creada exitosamente';
END $$;