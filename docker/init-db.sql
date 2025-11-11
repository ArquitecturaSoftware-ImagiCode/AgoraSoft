
--SCHEMA Y TABLA USUARIO

CREATE SCHEMA IF NOT EXISTS inventario;
GRANT ALL PRIVILEGES ON SCHEMA inventario TO postgres;
ALTER DATABASE "AgoraSoft" SET search_path TO inventario, public;

CREATE TABLE IF NOT EXISTS inventario.usuario (
    id VARCHAR(255) PRIMARY KEY,
    activated BOOLEAN DEFAULT FALSE,
    nombre VARCHAR(255),
    apellido VARCHAR(255),
    rol VARCHAR(255),
    organizacion VARCHAR(255),
    correo VARCHAR(255)
);
    CREATE TABLE IF NOT EXISTS inventario.empleado (
    id CHAR(36) NOT NULL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    rol VARCHAR(50) NOT NULL,
    departamento VARCHAR(100),
    telefono VARCHAR(20),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

GRANT ALL PRIVILEGES ON TABLE inventario.usuario TO postgres;


--TABLA PRODUCTOS

CREATE TABLE IF NOT EXISTS inventario.productos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(500),
    precio NUMERIC(10,2) NOT NULL,
    categoria VARCHAR(255),
    imagen_url VARCHAR(500),
    usuario_proveedor VARCHAR(255) NOT NULL,
    CONSTRAINT fk_usuario_proveedor FOREIGN KEY (usuario_proveedor) REFERENCES inventario.usuario(id)
);


--TABLA INVENTARIOS

CREATE TABLE IF NOT EXISTS inventario.inventarios (
    id VARCHAR(255) PRIMARY KEY,
    usuario_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_inventario_usuario FOREIGN KEY (usuario_id) REFERENCES inventario.usuario(id)
);


--TABLA ITEMS_INVENTARIO

CREATE TABLE IF NOT EXISTS inventario.items_inventario (
    id SERIAL PRIMARY KEY,
    inventario_id VARCHAR(255) NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    CONSTRAINT fk_item_inventario FOREIGN KEY (inventario_id) REFERENCES inventario.inventarios(id),
    CONSTRAINT fk_item_producto FOREIGN KEY (producto_id) REFERENCES inventario.productos(id)
);


--TABLA COMPRAS

CREATE TABLE IF NOT EXISTS inventario.compras (
    id SERIAL PRIMARY KEY,
    usuario_id VARCHAR(255) NOT NULL,
    proveedor_id VARCHAR(255) NOT NULL,
    fecha_compra TIMESTAMP NOT NULL,
    total NUMERIC(10,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_compra_usuario FOREIGN KEY (usuario_id) REFERENCES inventario.usuario(id),
    CONSTRAINT fk_compra_proveedor FOREIGN KEY (proveedor_id) REFERENCES inventario.usuario(id)
);


--TABLA DETALLES_COMPRA

CREATE TABLE IF NOT EXISTS inventario.detalles_compra (
    id SERIAL PRIMARY KEY,
    compra_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario NUMERIC(10,2) NOT NULL,
    subtotal NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_detalle_compra FOREIGN KEY (compra_id) REFERENCES inventario.compras(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_compra_producto FOREIGN KEY (producto_id) REFERENCES inventario.productos(id)
);


--TABLA VENTAS

CREATE TABLE IF NOT EXISTS inventario.ventas (
    id SERIAL PRIMARY KEY,
    usuario_id VARCHAR(255) NOT NULL,
    cliente_nombre VARCHAR(255),
    fecha_venta TIMESTAMP NOT NULL,
    total NUMERIC(10,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_venta_usuario FOREIGN KEY (usuario_id) REFERENCES inventario.usuario(id)
);


--TABLA DETALLES_VENTA

CREATE TABLE IF NOT EXISTS inventario.detalles_venta (
    id SERIAL PRIMARY KEY,
    venta_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario NUMERIC(10,2) NOT NULL,
    subtotal NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (venta_id) REFERENCES inventario.ventas(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_venta_producto FOREIGN KEY (producto_id) REFERENCES inventario.productos(id)
);


--MENSAJE DE CONFIRMACIÓN

DO $$
BEGIN
  RAISE NOTICE '✅ Todas las tablas de inventario creadas exitosamente';
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