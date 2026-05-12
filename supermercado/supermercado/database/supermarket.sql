CREATE DATABASE supermarket;
USE supermarket;

CREATE TABLE categorias (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

CREATE TABLE productos (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo_barras   VARCHAR(50)     NOT NULL UNIQUE,
    nombre          VARCHAR(150)    NOT NULL,
    descripcion     VARCHAR(255),
    precio          DECIMAL(10, 2)  NOT NULL,
    stock           INT             NOT NULL DEFAULT 0,
    activo          BOOLEAN         NOT NULL DEFAULT TRUE,   -- Soft Delete
    categoria_id    BIGINT          NOT NULL,
    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

CREATE TABLE proveedores (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nit         VARCHAR(20)  NOT NULL UNIQUE,               -- NIT único y obligatorio
    nombre      VARCHAR(150) NOT NULL,
    telefono    VARCHAR(20),
    email       VARCHAR(100),
    direccion   VARCHAR(255)
);

-- Relación ManyToMany entre Productos y Proveedores
CREATE TABLE producto_proveedor (
    producto_id  BIGINT NOT NULL,
    proveedor_id BIGINT NOT NULL,
    PRIMARY KEY (producto_id, proveedor_id),
    CONSTRAINT fk_pp_producto
        FOREIGN KEY (producto_id)  REFERENCES productos(id),
    CONSTRAINT fk_pp_proveedor
        FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
);

-- Registro de entradas de almacén (historial de abastecimiento)
CREATE TABLE entradas_almacen (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id  BIGINT         NOT NULL,
    proveedor_id BIGINT         NOT NULL,
    cantidad     INT            NOT NULL,
    fecha        DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_entrada_producto
        FOREIGN KEY (producto_id)  REFERENCES productos(id),
    CONSTRAINT fk_entrada_proveedor
        FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
);

CREATE TABLE empleados (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    cedula          VARCHAR(20)    NOT NULL UNIQUE,
    nombre          VARCHAR(150)   NOT NULL,
    cargo           ENUM('ADMINISTRADOR', 'CAJERO', 'AUXILIAR') NOT NULL,
    fecha_ingreso   DATE           NOT NULL,
    salario         DECIMAL(10, 2) NOT NULL
);

CREATE TABLE ventas (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha        DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    empleado_id  BIGINT         NOT NULL,
    subtotal     DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    iva          DECIMAL(10, 2) NOT NULL DEFAULT 0.00,   -- 19%
    total        DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_venta_empleado
        FOREIGN KEY (empleado_id) REFERENCES empleados(id)
);

CREATE TABLE detalle_venta (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    venta_id    BIGINT         NOT NULL,
    producto_id BIGINT         NOT NULL,
    cantidad    INT            NOT NULL,
    precio_unit DECIMAL(10, 2) NOT NULL,
    subtotal    DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_detalle_venta
        FOREIGN KEY (venta_id)    REFERENCES ventas(id),
    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (producto_id) REFERENCES productos(id)
);