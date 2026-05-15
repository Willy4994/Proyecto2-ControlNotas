CREATE DATABASE IF NOT EXISTS BDNotas;
USE BDNotas;

CREATE TABLE IF NOT EXISTS alumnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    carnet VARCHAR(20) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    seccion ENUM('A','B') NOT NULL,
    nota DECIMAL(5,2) NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO alumnos (carnet, nombres, apellidos, seccion, nota) VALUES
('2026001','Ana Lucia','Perez Gomez','A',85.50),
('2026002','Carlos Eduardo','Lopez Ruiz','A',78.00),
('2026003','Maria Fernanda','Garcia Soto','B',91.25),
('2026004','Jose Andres','Hernandez Diaz','B',69.00)
ON DUPLICATE KEY UPDATE carnet = carnet;
