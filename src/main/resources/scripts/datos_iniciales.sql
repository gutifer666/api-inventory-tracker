USE inventory_tracker;

-- Crear datos de usuarios

INSERT INTO user (id, username, password, roles, full_name, sales, earnings)
VALUES (1, 'administrador_prueba', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_ADMIN', 'Administrador de Prueba', 0, 0.0); -- password: pass

INSERT INTO user (id, username, password, roles, full_name, sales, earnings)
VALUES (2, 'empleado_prueba', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_EMPLOYEE', 'Empleado de Prueba', 0, 0.0); -- password: pass

INSERT INTO user (id, username, password, roles, full_name, sales, earnings)
VALUES (3, 'cliente_prueba', '$2a$10$TW9wmcAMesXeH1naGswppOMcSA70FkdDs3oH9e7I8BPujPwEXwaE6', 'ROLE_CUSTOMER', 'Cliente de Prueba', 0, 0.0); -- password: pass
