/* Crea usuario */
CREATE USER 'javier'@'localhost' IDENTIFIED BY 'C12345678!';

/* Crea base de datos */
CREATE DATABASE inventory_tracker;

/* Asigna todos los privilegios al usuario en esa base de datos */
GRANT ALL PRIVILEGES ON inventory_tracker.* TO 'javier'@'localhost';

USE inventory_tracker;

/* Crea tabla category */
CREATE TABLE category (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    name        VARCHAR(50) NOT NULL
);

/* Crea tabla supplier */
CREATE TABLE supplier (
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

/* Crea tabla product */
CREATE TABLE product (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    code         VARCHAR(50) NOT NULL,
    cost_price   DOUBLE NOT NULL,
    description  VARCHAR(255) NOT NULL,
    name         VARCHAR(50) NOT NULL,
    quantity     INT NOT NULL,
    retail_price DOUBLE NOT NULL,
    category_id  BIGINT NOT NULL,
    supplier_id  BIGINT NOT NULL,
    CONSTRAINT FK1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT FK2kxvbr72tmtscjvyp9yqb12by FOREIGN KEY (supplier_id) REFERENCES supplier (id)
);

/* Crea tabla transactions */
CREATE TABLE transactions (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_name     VARCHAR(50) NOT NULL,
    client_name       VARCHAR(50) NOT NULL,
    product_code      VARCHAR(50) NOT NULL,
    product_name      VARCHAR(50) NOT NULL,
    quantity          INT NOT NULL,
    transaction_price DOUBLE NOT NULL,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL
);

/* Crea tabla user */
CREATE TABLE user (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    earnings  DOUBLE DEFAULT 0 NULL,
    full_name VARCHAR(100) NOT NULL,
    password  VARCHAR(100) NOT NULL,
    roles     VARCHAR(50) NOT NULL,
    sales     INT DEFAULT 0 NULL,
    username  VARCHAR(50) NOT NULL,
    CONSTRAINT UKsb8bbouer5wak8vyiiy4pf2bx UNIQUE(username)
);

/* Datos de ejemplo */

-- category
INSERT INTO inventory_tracker.category (id, description, name) VALUES (1, 'Productos de uso general', 'General');
INSERT INTO inventory_tracker.category (id, description, name) VALUES (2, 'Dispositivos y gadgets electrónicos', 'Electrónica');
INSERT INTO inventory_tracker.category (id, description, name) VALUES (3, 'Prendas de vestir y accesorios', 'Ropa');
INSERT INTO inventory_tracker.category (id, description, name) VALUES (4, 'Productos alimenticios y bebidas', 'Alimentos');

-- supplier
INSERT INTO inventory_tracker.supplier (id, name) VALUES (1, 'Proveedor 1');
INSERT INTO inventory_tracker.supplier (id, name) VALUES (2, 'Proveedor 2');
INSERT INTO inventory_tracker.supplier (id, name) VALUES (3, 'Proveedor 3');

-- product
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (1, 'GEN001', 300, 'Bicicleta BH de montaña', 'Bicicleta', 15, 500, 1, 1);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (2, 'ELEC001', 200, 'Teléfono inteligente de última generación', 'Smartphone', 9, 300, 2, 2);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (3, 'ROPA001', 30, 'Camiseta de algodón 100%', 'Camiseta', 48, 50, 3, 3);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (4, 'ALIM001', 1, 'Leche entera pasteurizada', 'Leche', 98, 2, 4, 1);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (5, 'GEN002', 20, 'Silla de madera para comedor', 'Silla', 30, 30, 1, 2);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (6, 'ELEC002', 100, 'Tablet de 10 pulgadas', 'Tablet', 13, 150, 2, 3);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (7, 'ROPA002', 40, 'Pantalón vaquero de hombre', 'Pantalón', 40, 60, 3, 1);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (8, 'ALIM002', 0.5, 'Pan de trigo', 'Pan', 200, 1, 4, 2);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (9, 'GEN003', 50, 'Mesa de centro de cristal', 'Mesa', 25, 80, 1, 3);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (10, 'ELEC003', 80, 'Reloj inteligente con monitor de actividad', 'Smartwatch', 20, 120, 2, 1);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (11, 'ROPA003', 60, 'Vestido de fiesta para mujer', 'Vestido', 35, 100, 3, 2);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (12, 'ALIM003', 1.5, 'Refresco de cola', 'Refresco', 150, 2.5, 4, 3);
INSERT INTO inventory_tracker.product (id, code, cost_price, description, name, quantity, retail_price, category_id, supplier_id) VALUES (14, 'ELEC004', 45, 'Auriculares inalámbricos con cancelación de ruido', 'Auriculares Bluetooth edited', 28, 79.99, 2, 1);

-- transactions
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (1, 'Empleado de Prueba', 'Juan Pérez', 'GEN001', 'Bicicleta', 1, 500, '2023-12-01 09:30:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (2, 'Empleado de Prueba', 'María López', 'ELEC001', 'Smartphone', 2, 600, '2023-12-02 11:45:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (3, 'Empleado de Prueba', 'Carlos Gómez', 'ROPA001', 'Camiseta', 3, 150, '2023-12-03 14:20:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (4, 'Empleado de Prueba', 'Ana Martínez', 'ALIM001', 'Leche', 5, 10, '2023-12-04 16:15:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (5, 'Empleado de Prueba', 'Pedro Sánchez', 'GEN002', 'Silla', 2, 60, '2023-12-05 10:00:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (6, 'Empleado de Prueba', 'Laura Fernández', 'ELEC002', 'Tablet', 1, 150, '2023-12-06 13:30:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (7, 'Empleado de Prueba', 'Miguel Torres', 'ROPA002', 'Pantalón', 2, 120, '2023-12-07 15:45:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (8, 'Empleado de Prueba', 'Carmen Ruiz', 'ALIM002', 'Pan', 10, 10, '2023-12-08 09:10:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (9, 'Empleado de Prueba', 'Pablo Díaz', 'GEN003', 'Mesa', 1, 80, '2023-12-09 12:25:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (10, 'Empleado de Prueba', 'Isabel Moreno', 'ELEC003', 'Smartwatch', 3, 360, '2023-12-10 17:00:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (11, 'Empleado de Prueba', 'Javier Ortiz', 'ROPA003', 'Vestido', 1, 100, '2023-12-11 11:30:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (12, 'Empleado de Prueba', 'Lucía Vega', 'ALIM003', 'Refresco', 6, 15, '2023-12-12 14:45:00');
INSERT INTO inventory_tracker.transactions (id, employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at) VALUES (13, 'Empleado de Prueba', 'Guti Cliente', 'GEN001', 'Bicicleta', 1, 200, '2025-05-02 12:34:30');

-- user
INSERT INTO inventory_tracker.user (id, earnings, full_name, password, roles, sales, username) VALUES (1, 0, 'Administrador de Prueba', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_ADMIN', 0, 'administrador_prueba');
INSERT INTO inventory_tracker.user (id, earnings, full_name, password, roles, sales, username) VALUES (2, 2755, 'Empleado de Pruebas', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_EMPLOYEE', 13, 'empleado_prueba');
INSERT INTO inventory_tracker.user (id, earnings, full_name, password, roles, sales, username) VALUES (3, 0, 'Cliente de Prueba', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_CUSTOMER', 0, 'cliente_prueba');
INSERT INTO inventory_tracker.user (id, earnings, full_name, password, roles, sales, username) VALUES (4, 0, 'Empleado de Pruebas 2', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_EMPLOYEE', 0, 'empleado_prueba_2');
INSERT INTO inventory_tracker.user (id, earnings, full_name, password, roles, sales, username) VALUES (5, 0, 'Javier Gutiérrez', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_ADMIN', 0, 'admin_guti');


