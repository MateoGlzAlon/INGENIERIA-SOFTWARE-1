-- Eliminar tablas si existen
-- DROP TABLE IF EXISTS Usuario, Articulo, Venta, Cliente, Mascota, Administrador, Cita;

-- DROP TABLE IF EXISTS HistorialCompra;

-- Crear la tabla Usuario
CREATE TABLE IF NOT EXISTS Usuario(
    idUsuario INT AUTO_INCREMENT,
    nombreUsuario VARCHAR(20) NOT NULL UNIQUE,
    contraseña TEXT NOT NULL,
    rol ENUM('Administrador', 'Cliente'),
    PRIMARY KEY(idUsuario)
);

-- Crear la tabla Cliente
CREATE TABLE IF NOT EXISTS Cliente(
    idUsuario INT,
    dni VARCHAR(9) NOT NULL UNIQUE,
    telefono VARCHAR(9) NOT NULL,
    nombre VARCHAR(20) NOT NULL,
    apellidos VARCHAR(30) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario),
    numMascotas INT NOT NULL,
    PRIMARY KEY(idUsuario)
);

-- Crear la tabla Administrador 
CREATE TABLE IF NOT EXISTS Administrador(
    idUsuario INT,
    cadenaInicioSesion VARCHAR(20) NOT NULL UNIQUE,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario),
    PRIMARY KEY(idUsuario)
);


-- Crear la tabla Articulo
CREATE TABLE IF NOT EXISTS Articulo (
    idArticulo INT AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    marca VARCHAR(20) NOT NULL,
    precio FLOAT NOT NULL, 
    descripcionArticulo VARCHAR(200),
    PRIMARY KEY (idArticulo)
);

-- Crear la tabla Servicio
CREATE TABLE IF NOT EXISTS Servicio (
    idServicio INT AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    precio FLOAT NOT NULL, 
    descripcionServicio VARCHAR(200),
    PRIMARY KEY (idServicio)
);

-- Crear la tabla Venta
CREATE TABLE IF NOT EXISTS Venta (
    idVenta INT AUTO_INCREMENT,
    idMismaVenta INT NOT NULL,
    idUsuario INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    idArtServ INT NOT NULL,
    descripcionVenta VARCHAR(200),
    fechaVenta DATE,
    FOREIGN KEY (idUsuario) REFERENCES Usuario (idUsuario),
    PRIMARY KEY (idVenta)
);

-- Crear la tabla Mascota
CREATE TABLE IF NOT EXISTS Mascota (
    idMascota INT AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    especie VARCHAR(30) NOT NULL,
    raza VARCHAR(30) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    idUsuario INT,
    FOREIGN KEY (idUsuario) REFERENCES Cliente (idUsuario) ON DELETE CASCADE,
    PRIMARY KEY (idMascota)
);

-- Crear la tabla Cita
CREATE TABLE IF NOT EXISTS Cita (
    idCita INT,
    idUsuario INT NOT NULL,
    fechaCita DATE NOT NULL,
    horaCita TIME NOT NULL,
    idMascota INT NOT NULL,
    descripcionCita VARCHAR(150) NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES Usuario (idUsuario),
    PRIMARY KEY (idCita)
);