-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-04-2024 a las 03:14:18
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_discoteca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `ID_cliente` int(50) NOT NULL,
  `Nombre_cliente` varchar(200) NOT NULL,
  `Numero_identificacion` varchar(200) NOT NULL,
  `Fecha_nacimiento` date NOT NULL,
  `Genero` varchar(20) NOT NULL,
  `Direccion` varchar(200) NOT NULL,
  `Numero_telefono` varchar(20) NOT NULL,
  `Correo_electronico` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`ID_cliente`, `Nombre_cliente`, `Numero_identificacion`, `Fecha_nacimiento`, `Genero`, `Direccion`, `Numero_telefono`, `Correo_electronico`) VALUES
(1, 'Juan Pérez', '123456789', '1990-01-08', 'M', 'Calle Principal, Ciudad', '6784520', 'juan@example.com'),
(2, 'douglas', '12545', '1992-04-12', 'M', 'la paz', '62504155', 'douglas1@gmail.com'),
(3, 'victor', '12356712', '1993-12-04', 'M', 'el alto', '62301477', 'victor@gmail.com'),
(5, 'mario', '10012345', '1995-05-12', 'M', 'calle los amigos', '76578967', 'mario09@hotmail.com'),
(6, 'Maria', '19823456', '1990-05-12', 'F', 'Calle san marcos', '65467859', 'Maria765@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `control_asistencia_discoteca`
--

CREATE TABLE `control_asistencia_discoteca` (
  `ID_registro_asistencia` int(11) NOT NULL,
  `ID_cliente` int(50) NOT NULL,
  `ID_evento` int(50) NOT NULL,
  `Fecha_hora_entrada` datetime(6) NOT NULL,
  `Fecha_hora_salida` datetime(6) NOT NULL,
  `Precio_pagado` decimal(50,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `control_asistencia_discoteca`
--

INSERT INTO `control_asistencia_discoteca` (`ID_registro_asistencia`, `ID_cliente`, `ID_evento`, `Fecha_hora_entrada`, `Fecha_hora_salida`, `Precio_pagado`) VALUES
(1, 1, 1, '2024-07-15 20:00:00.000000', '2024-07-15 00:00:00.000000', 40),
(2, 2, 4, '2023-02-10 20:00:00.000000', '2023-02-11 02:00:00.000000', 40);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

CREATE TABLE `empleados` (
  `ID_empleado` int(50) NOT NULL,
  `Nombre_empleado` varchar(200) NOT NULL,
  `Cargo` varchar(200) NOT NULL,
  `Fecha_contratacion` date NOT NULL,
  `Nombre_usuario` varchar(200) NOT NULL,
  `Contraseña` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`ID_empleado`, `Nombre_empleado`, `Cargo`, `Fecha_contratacion`, `Nombre_usuario`, `Contraseña`) VALUES
(1, 'juan', 'vendedor', '1995-05-12', 'juan01', '1234'),
(2, 'María García', 'Cajera', '2023-05-20', 'mariagarcia', 'clave456'),
(3, 'Luis Martínez', 'Bartender', '2023-03-10', 'luismartinez', 'password789'),
(4, 'Sofia Gutierrez', 'Cajera', '2023-01-01', 'Sofiag01', '123sofi');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `eventos`
--

CREATE TABLE `eventos` (
  `ID_Evento` int(50) NOT NULL,
  `Nombre_evento` varchar(200) NOT NULL,
  `Fecha_Hora_evento` datetime(6) NOT NULL,
  `Descripcion_evento` text NOT NULL,
  `Artistas_DJ_invitados` varchar(200) NOT NULL,
  `Capacidad_maxima_evento` int(200) NOT NULL,
  `Precio_entrada` decimal(65,0) NOT NULL,
  `Estado_evento` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `eventos`
--

INSERT INTO `eventos` (`ID_Evento`, `Nombre_evento`, `Fecha_Hora_evento`, `Descripcion_evento`, `Artistas_DJ_invitados`, `Capacidad_maxima_evento`, `Precio_entrada`, `Estado_evento`) VALUES
(1, 'Fiesta de Verano', '2024-07-15 20:00:00.000000', 'Una noche llena de diversión bajo las estrellas', 'DJ Alex, DJ Maria', 200, 20, 'Activo'),
(2, 'Noche de Electrónica', '2024-08-20 22:00:00.000000', 'Experimenta la mejor música electrónica', 'DJ John, DJ Sarah', 150, 15, 'Activo'),
(3, 'Fiesta de Clásicos', '2024-09-10 18:00:00.000000', 'Una fiesta para recordar Clásicos', 'DJ Carlos, DJ Laura', 300, 25, 'Activo'),
(4, 'Fiesta de Primavera', '2024-09-10 18:00:00.000000', 'Experimenta la mejor música en primavera', 'Dj Jonhson', 200, 30, 'inactivo'),
(5, 'Fiesta de FIn De Semana', '2023-05-10 21:00:00.000000', 'Una fiesta para relajarse', 'Dj Nelson', 200, 50, 'inactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario`
--

CREATE TABLE `inventario` (
  `ID_inventario` int(50) NOT NULL,
  `ID_producto` int(50) NOT NULL,
  `Fecha_registro` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inventario`
--

INSERT INTO `inventario` (`ID_inventario`, `ID_producto`, `Fecha_registro`) VALUES
(1, 1, '2023-11-15'),
(2, 2, '2023-04-16'),
(3, 3, '2023-01-01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `ID_producto` int(50) NOT NULL,
  `Nombre_producto` varchar(200) NOT NULL,
  `Categoria_producto` varchar(200) NOT NULL,
  `Precio_unitario` decimal(65,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`ID_producto`, `Nombre_producto`, `Categoria_producto`, `Precio_unitario`) VALUES
(1, 'Cerveza Paceña', 'na', 20),
(2, 'Taquiña', 'Cerveza', 20),
(3, 'Singani', 'Cocteles', 50),
(4, 'Huari', 'Cerveza', 15),
(5, 'Bock', 'Cerveza', 15),
(6, 'Vodka', 'Vodka', 25),
(7, 'Ron Bahamas', 'Ron', 70),
(8, 'Whisky', 'Whisky', 80),
(9, 'Vodka COCO', 'Vodka', 100),
(10, 'Champague', 'Champan', 40);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `ID_proveedor` int(50) NOT NULL,
  `Nombre_proveedor` varchar(200) NOT NULL,
  `Direccion` varchar(200) NOT NULL,
  `Numero_telefono_contacto` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`ID_proveedor`, `Nombre_proveedor`, `Direccion`, `Numero_telefono_contacto`) VALUES
(1, 'Distribuidora García', 'Calle Bolívar #123, La Paz', '+59172548963'),
(2, 'Importadora Martínez', 'Av. Sucre #456, Cochabamba', '+59167321598'),
(3, 'Comercial Pérez S.R.L. ', 'Calle Camacho #789, Santa Cruz ', '+59160123456'),
(4, 'Importadora Fast', 'Av. san javier #333 El Alto', '+59167048675');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `ID_venta` int(50) NOT NULL,
  `ID_cliente` int(50) NOT NULL,
  `ID_empleado` int(50) NOT NULL,
  `Fecha_hora_venta` datetime(6) NOT NULL,
  `Monto_total_venta` decimal(50,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`ID_venta`, `ID_cliente`, `ID_empleado`, `Fecha_hora_venta`, `Monto_total_venta`) VALUES
(1, 1, 1, '2024-04-15 19:30:00.000000', 150),
(2, 2, 2, '2024-04-16 21:00:00.000000', 200),
(3, 1, 1, '2024-04-16 21:00:00.000000', 100),
(4, 5, 4, '2023-02-10 19:00:00.000000', 300);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`ID_cliente`);

--
-- Indices de la tabla `control_asistencia_discoteca`
--
ALTER TABLE `control_asistencia_discoteca`
  ADD PRIMARY KEY (`ID_registro_asistencia`),
  ADD KEY `ID_cliente` (`ID_cliente`),
  ADD KEY `ID_evento` (`ID_evento`);

--
-- Indices de la tabla `empleados`
--
ALTER TABLE `empleados`
  ADD PRIMARY KEY (`ID_empleado`);

--
-- Indices de la tabla `eventos`
--
ALTER TABLE `eventos`
  ADD PRIMARY KEY (`ID_Evento`);

--
-- Indices de la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD PRIMARY KEY (`ID_inventario`),
  ADD KEY `ID_producto` (`ID_producto`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`ID_producto`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`ID_proveedor`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`ID_venta`),
  ADD KEY `ID_cliente` (`ID_cliente`),
  ADD KEY `ID_empleado` (`ID_empleado`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `ID_cliente` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `control_asistencia_discoteca`
--
ALTER TABLE `control_asistencia_discoteca`
  MODIFY `ID_registro_asistencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `empleados`
--
ALTER TABLE `empleados`
  MODIFY `ID_empleado` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `eventos`
--
ALTER TABLE `eventos`
  MODIFY `ID_Evento` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `inventario`
--
ALTER TABLE `inventario`
  MODIFY `ID_inventario` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `ID_producto` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `ID_proveedor` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `ID_venta` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `control_asistencia_discoteca`
--
ALTER TABLE `control_asistencia_discoteca`
  ADD CONSTRAINT `control_asistencia_discoteca_ibfk_1` FOREIGN KEY (`ID_cliente`) REFERENCES `clientes` (`ID_cliente`) ON UPDATE CASCADE,
  ADD CONSTRAINT `control_asistencia_discoteca_ibfk_2` FOREIGN KEY (`ID_evento`) REFERENCES `eventos` (`ID_Evento`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `inventario`
--
ALTER TABLE `inventario`
  ADD CONSTRAINT `inventario_ibfk_1` FOREIGN KEY (`ID_producto`) REFERENCES `productos` (`ID_producto`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`ID_cliente`) REFERENCES `clientes` (`ID_cliente`) ON UPDATE CASCADE,
  ADD CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`ID_empleado`) REFERENCES `empleados` (`ID_empleado`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
