-- Esto lo usamos por si ya exidte la tabla
DROP DATABASE IF EXISTS VetTrack;

--Creamos la base de datos
CREATE DATABASE VetTrack;
USE VetTrack;

-- ESto se hace para borrar todas las tablas para volver a hacerlas
DROP TABLE IF EXISTS Articulo;
DROP TABLE IF EXISTS Venta;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Cliente;
DROP TABLE IF EXISTS Mascota;
DROP TABLE IF EXISTS Administrador;
DROP TABLE IF EXISTS HistorialCompra;
DROP TABLE IF EXISTS Cita;

-- Creacion de tablas

CREATE TABLE Usuario(
    IDUsuario int AUTO_INCREMENT,
    user varchar(20) not null unique,
    passwd text not null,
    rol enum('Administrador', 'Cliente'),
    PRIMARY KEY(IDUsuario),
);

CREATE TABLE Articulo(
    IDArticulo int AUTO_INCREMENT,
    nombre varchar(50) not null,
    descrip_a varchar(200),
    PRIMARY KEY(IDArticulo)
);

CREATE TABLE Venta(
    IDVenta int AUTO_INCREMENT,
    IDUsuario numeric(5) not null,
    descrip_v varchar(200),
    FOREIGN KEY(IDUsuario) REFERENCES Usuario(IDUsuario),
    PRIMARY KEY (IDVenta)
);

CREATE TABLE Cliente(
    IDUsuario int,
    FOREIGN KEY (IDUsuario) REFERENCES Usuario(IDUsuario),
    numMascotas int not null,
    --
    dni varchar(9) not null unique,
    telefono varchar(9) not null unique,
    nombreCompleto varchar(50) not null,
    --
    PRIMARY KEY(IDUsuario)
);

CREATE TABLE Mascota(
    numPassport numeric(15) not null unique, --Ejemplo: 985121004808821
    PRIMARY KEY (numPassport),
    nombre varchar(20) not null,
    especie varchar(30) not null,
    raza varchar(30) not null,
    fechaNacimiento DATE not null, -- Ejemplo para poner fecha: "2023-12-25" (YYYY-MM-DD)
    IDUsuario int,
    FOREIGN KEY (IDUsuario) REFERENCES Cliente(IDUsuario),
    ON DELETE cascade -- Para eliminar todas las mascotas del usuario si se borra
);

CREATE TABLE Administrador(
    IDUsuario int,
    FOREIGN KEY (IDUsuario) REFERENCES Usuario(IDUsuario),
    PRIMARY KEY(IDUsuario),
    cadenaInicioSesion varchar(20) not null unique
);

CREATE TABLE HistorialCompra(
    IDUsuario int,
    FOREIGN KEY (IDUsuario) REFERENCES Usuario(IDUsuario),
    PRIMARY KEY(IDUsuario),
    nombreArticulo varchar(30) not null,
    ON DELETE cascade
);

CREATE TABLE Cita(
    IDUsuario int,
    FOREIGN KEY (IDUsuario) REFERENCES Usuario(IDUsuario),
    PRIMARY KEY(IDUsuario),
    fechaCita DATE not null,
    horaCita TIME not null -- El formato es 12:30:00
);

-- Quedan hacer un par de inserts
