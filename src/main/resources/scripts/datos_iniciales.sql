USE inventory_tracker;

-- Crear datos de usuarios

INSERT INTO user (id, username, password, roles, full_name, sales, earnings)
VALUES (1, 'administrador_prueba', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_ADMIN', 'Administrador de Prueba', 0, 0.0); -- password: pass

INSERT INTO user (id, username, password, roles, full_name, sales, earnings)
VALUES (2, 'empleado_prueba', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_EMPLOYEE', 'Empleado de Prueba', 0, 0.0); -- password: pass

INSERT INTO user (id, username, password, roles, full_name, sales, earnings)
VALUES (3, 'cliente_prueba', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_CUSTOMER', 'Cliente de Prueba', 0, 0.0); -- password: pass

-- Crear datos de categorías

INSERT INTO category (id, name, description)
VALUES
(1, 'General', 'Productos de uso general'),
(2, 'Electrónica', 'Dispositivos y gadgets electrónicos'),
(3, 'Ropa', 'Prendas de vestir y accesorios'),
(4, 'Alimentos', 'Productos alimenticios y bebidas');

-- Crear datos de proveedores

INSERT INTO supplier (id, name)
VALUES
(1, 'Proveedor 1'),
(2, 'Proveedor 2'),
(3, 'Proveedor 3');

-- Crear datos de productos

INSERT INTO product (id, code, name, description, category_id, cost_price, retail_price, quantity, supplier_id)
VALUES
(1, 'GEN001', 'Bicicleta', 'Bicicleta BH de montaña', 1, 300.00, 500.00, 20, 1),
(2, 'ELEC001', 'Smartphone', 'Teléfono inteligente de última generación', 2, 200.0, 300.0, 10, 2),
(3, 'ROPA001', 'Camiseta', 'Camiseta de algodón 100%', 3, 30.0, 50.0, 50, 3),
(4, 'ALIM001', 'Leche', 'Leche entera pasteurizada', 4, 1.0, 2.0, 100, 1),
(5, 'GEN002', 'Silla', 'Silla de madera para comedor', 1, 20.0, 30.0, 30, 2),
(6, 'ELEC002', 'Tablet', 'Tablet de 10 pulgadas', 2, 100.0, 150.0, 15, 3),
(7, 'ROPA002', 'Pantalón', 'Pantalón vaquero de hombre', 3, 40.0, 60.0, 40, 1),
(8, 'ALIM002', 'Pan', 'Pan de trigo', 4, 0.5, 1.0, 200, 2),
(9, 'GEN003', 'Mesa', 'Mesa de centro de cristal', 1, 50.0, 80.0, 25, 3),
(10, 'ELEC003', 'Smartwatch', 'Reloj inteligente con monitor de actividad', 2, 80.0, 120.0, 20, 1),
(11, 'ROPA003', 'Vestido', 'Vestido de fiesta para mujer', 3, 60.0, 100.0, 35, 2),
(12, 'ALIM003', 'Refresco', 'Refresco de cola', 4, 1.5, 2.5, 150, 3);

-- Crear datos de transacciones

-- Insertar datos en la tabla transaction
INSERT INTO transactions (employee_name, client_name, product_code, product_name, quantity, transaction_price, created_at)
VALUES
('Empleado de Prueba', 'Juan Pérez', 'GEN001', 'Bicicleta', 1, 500.00, '2023-12-01 09:30:00'),
('Empleado de Prueba', 'María López', 'ELEC001', 'Smartphone', 2, 600.00, '2023-12-02 11:45:00'),
('Empleado de Prueba', 'Carlos Gómez', 'ROPA001', 'Camiseta', 3, 150.00, '2023-12-03 14:20:00'),
('Empleado de Prueba', 'Ana Martínez', 'ALIM001', 'Leche', 5, 10.00, '2023-12-04 16:15:00'),
('Empleado de Prueba', 'Pedro Sánchez', 'GEN002', 'Silla', 2, 60.00, '2023-12-05 10:00:00'),
('Empleado de Prueba', 'Laura Fernández', 'ELEC002', 'Tablet', 1, 150.00, '2023-12-06 13:30:00'),
('Empleado de Prueba', 'Miguel Torres', 'ROPA002', 'Pantalón', 2, 120.00, '2023-12-07 15:45:00'),
('Empleado de Prueba', 'Carmen Ruiz', 'ALIM002', 'Pan', 10, 10.00, '2023-12-08 09:10:00'),
('Empleado de Prueba', 'Pablo Díaz', 'GEN003', 'Mesa', 1, 80.00, '2023-12-09 12:25:00'),
('Empleado de Prueba', 'Isabel Moreno', 'ELEC003', 'Smartwatch', 3, 360.00, '2023-12-10 17:00:00'),
('Empleado de Prueba', 'Javier Ortiz', 'ROPA003', 'Vestido', 1, 100.00, '2023-12-11 11:30:00'),
('Empleado de Prueba', 'Lucía Vega', 'ALIM003', 'Refresco', 6, 15.00, '2023-12-12 14:45:00');