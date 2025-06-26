-- Crear base de datos
DROP DATABASE IF EXISTS meeting_rooms_db;
CREATE DATABASE meeting_rooms_db;
USE meeting_rooms_db;

-- Tabla Rooms
CREATE TABLE Rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    resources VARCHAR(255)
);

-- Tabla Employees
CREATE TABLE Employees (
    dni VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    department VARCHAR(50)
);

-- Tabla Reservations
CREATE TABLE Reservations (
    reservation_id VARCHAR(10) PRIMARY KEY,
    room_id INT NOT NULL,
    dni VARCHAR(20) NOT NULL,
    reservation_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES Rooms(room_id),
    CONSTRAINT fk_employee FOREIGN KEY (dni) REFERENCES Employees(dni)
);

-- Índices adicionales para optimizar búsquedas
CREATE INDEX index_reservation_room ON Reservations(room_id);
CREATE INDEX index_reservation_employee ON Reservations(dni);


-- 25 registros tabla Employees

INSERT INTO Employees (dni, name, email, department) VALUES
('12345678A', 'Laura González Pérez', 'lgonzalez@empresa.com', 'Marketing'),
('23456789B', 'Carlos Martínez Ruiz', 'cmartinez@empresa.com', 'Ventas'),
('34567890C', 'Ana Sánchez López', 'asanchez@empresa.com', 'RRHH'),
('45678901D', 'David Fernández García', 'dfernandez@empresa.com', 'IT'),
('56789012E', 'Elena Gómez Rodríguez', 'egomez@empresa.com', 'Finanzas'),
('67890123F', 'Javier Díaz Martín', 'jdiaz@empresa.com', 'Operaciones'),
('78901234G', 'Sofía Romero Hernández', 'sromero@empresa.com', 'Legal'),
('89012345H', 'Pablo Jiménez Moreno', 'pjimenez@empresa.com', 'Compras'),
('90123456I', 'Marta Torres Navarro', 'mtorres@empresa.com', 'Marketing'),
('01234567J', 'Daniel Vázquez Molina', 'dvazquez@empresa.com', 'Ventas'),
('11223344K', 'Lucía Gil Serrano', 'lgil@empresa.com', 'RRHH'),
('22334455L', 'Adrián Ruiz Blanco', 'aruiz@empresa.com', 'IT'),
('33445566M', 'Natalia Castro Méndez', 'ncastro@empresa.com', 'Finanzas'),
('44556677N', 'Hugo Ortega Rubio', 'hortega@empresa.com', 'Operaciones'),
('55667788O', 'Claudia Marín Soria', 'cmarin@empresa.com', 'Legal'),
('66778899P', 'Iván Navarro Cortés', 'inavarro@empresa.com', 'Compras'),
('77889900Q', 'Alicia Mora Ramírez', 'amora@empresa.com', 'Marketing'),
('88990011R', 'Rubén Delgado Iglesias', 'rdelgado@empresa.com', 'Ventas'),
('99001122S', 'Silvia Peña Campos', 'spena@empresa.com', 'RRHH'),
('00112233T', 'Óscar Vega Santos', 'ovega@empresa.com', 'IT'),
('10293847U', 'Eva León Cruz', 'eleon@empresa.com', 'Finanzas'),
('29384756V', 'Álvaro Herrera Flores', 'aherrera@empresa.com', 'Operaciones'),
('38475692W', 'Beatriz Calvo Marquez', 'bcalvo@empresa.com', 'Legal'),
('47586920X', 'Gabriel Reyes Fuentes', 'greyes@empresa.com', 'Compras'),
('58697031Y', 'Cristina Caballero Carrasco', 'ccaballero@empresa.com', 'Marketing');


-- 25 registros tabla Rooms

INSERT INTO Rooms (name, capacity, resources) VALUES
('Sala Aurora', 8, 'Proyector, Pizarra blanca, WiFi'),
('Sala Boreal', 6, 'Pantalla TV, Sistema de conferencia'),
('Sala Cumbre', 12, 'Pizarra interactiva, Videoconferencia'),
('Sala Delta', 4, 'Monitor compartido, Teléfono conferencia'),
('Sala Élite', 10, 'Proyector, Pizarra magnética, WiFi'),
('Sala Forja', 8, 'Pantalla dual, Sistema de audio'),
('Sala Galaxia', 15, 'Videoconferencia 4K, Pizarra digital'),
('Sala Horizonte', 6, 'Tabletas integradas, WiFi dedicado'),
('Sala Índigo', 5, 'Pantalla táctil, Altavoces'),
('Sala Júpiter', 20, 'Escenario, Micrófonos inalámbricos'),
('Sala Krypton', 8, 'Realidad aumentada, WiFi 6'),
('Sala Luna', 10, 'Proyector 4K, Sistema Dolby Atmos'),
('Sala Maravilla', 6, 'Pizarra de cristal, Tabletas'),
('Sala Nebula', 12, 'Videoconferencia 360°, Pantallas táctiles'),
('Sala Oasis', 4, 'Sistema de sonido envolvente'),
('Sala Pinnacle', 8, 'Computadoras integradas, Servidor local'),
('Sala Quantum', 10, 'Realidad virtual, WiFi 6E'),
('Sala Río', 6, 'Pizarra interactiva, Grabación automática'),
('Sala Sol', 25, 'Equipo de traducción simultánea'),
('Sala Torre', 8, 'Control de iluminación, Acústica premium'),
('Sala Universo', 30, 'Sistema de votación en tiempo real'),
('Sala Vortex', 6, 'Robots de telepresencia'),
('Sala Whisper', 4, 'Aislamiento acústico, Micrófonos direccionales'),
('Sala Xanadú', 10, 'Mesa interactiva, Realidad mixta'),
('Sala Yucatán', 8, 'Control ambiental inteligente');


-- 25 registros tabla Reservations

INSERT INTO Reservations (reservation_id, room_id, dni, reservation_date, start_time, end_time) VALUES
('a3b7c1d9e5', 1, '12345678A', '2023-11-15', '09:00:00', '10:30:00'),
('z9x5y2w8v4', 3, '23456789B', '2023-11-15', '11:00:00', '12:30:00'),
('m2n8p4q6r0', 5, '34567890C', '2023-11-15', '14:00:00', '15:30:00'),
('k7j3h1g5f9', 7, '45678901D', '2023-11-16', '10:00:00', '11:30:00'),
('t4s6u2v8w0', 2, '56789012E', '2023-11-16', '12:00:00', '13:00:00'),
('l1o5i9p3u7', 4, '67890123F', '2023-11-16', '15:00:00', '16:30:00'),
('e8r2t6y4u0', 6, '78901234G', '2023-11-17', '09:30:00', '11:00:00'),
('q3w7e1r5t9', 8, '89012345H', '2023-11-17', '13:00:00', '14:30:00'),
('b6v4c8x2z0', 9, '90123456I', '2023-11-17', '16:00:00', '17:00:00'),
('n9m1k5j7h3', 10, '01234567J', '2023-11-20', '08:00:00', '10:00:00'),
('d5f1g9h3j7', 11, '11223344K', '2023-11-20', '11:00:00', '12:30:00'),
('s4a8d2f6g0', 12, '22334455L', '2023-11-20', '14:00:00', '15:30:00'),
('w1e5r9t3y7', 13, '33445566M', '2023-11-21', '09:00:00', '10:30:00'),
('u6i2o8p4a0', 14, '44556677N', '2023-11-21', '11:30:00', '13:00:00'),
('y3t7r1e9w5', 15, '55667788O', '2023-11-21', '15:00:00', '16:30:00'),
('h2j8k6l4m0', 16, '66778899P', '2023-11-22', '10:00:00', '12:00:00'),
('g7f3d9s5a1', 17, '77889900Q', '2023-11-22', '13:00:00', '14:30:00'),
('v4b6n2m8q0', 18, '88990011R', '2023-11-22', '16:00:00', '17:30:00'),
('p5o9i3u7y1', 19, '99001122S', '2023-11-23', '08:30:00', '10:00:00'),
('x2z6c0v4b8', 20, '00112233T', '2023-11-23', '11:00:00', '12:30:00'),
('r7t1y5u9i3', 21, '10293847U', '2023-11-23', '14:00:00', '15:30:00'),
('k4j8h2g6f0', 22, '29384756V', '2023-11-24', '09:30:00', '11:00:00'),
('l3q7w1e5r9', 23, '38475692W', '2023-11-24', '12:00:00', '13:30:00'),
('m8n4b2v6c0', 24, '47586920X', '2023-11-24', '15:00:00', '16:30:00'),
('z5x1c7v3b9', 25, '58697031Y', '2023-11-27', '10:00:00', '12:00:00');