-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-06-2020 a las 03:23:29
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gonzalez_lucas_2020`
--
CREATE DATABASE IF NOT EXISTS `gonzalez_lucas_2020` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `gonzalez_lucas_2020`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE `alumno` (
  `alu_dni` bigint(10) NOT NULL,
  `alu_nombre` varchar(30) NOT NULL,
  `alu_apellido` varchar(30) NOT NULL,
  `alu_fec_nac` date DEFAULT NULL,
  `alu_domicilio` varchar(100) DEFAULT NULL,
  `alu_telefono` varchar(10) NOT NULL,
  `alu_insc_cod` bigint(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`alu_dni`, `alu_nombre`, `alu_apellido`, `alu_fec_nac`, `alu_domicilio`, `alu_telefono`, `alu_insc_cod`) VALUES
(35852687, 'Cristopher', 'Catalan', NULL, '', '2612698547', 15),
(35877927, 'Matias', 'Fontana', '1991-03-08', 'España1756 dto 4', '2616211287', NULL),
(35984632, 'Juan', 'Gomez', '1984-05-12', 'Chile 156', '2626587841', NULL),
(37895621, 'Adriel', 'Fontana', '1993-03-02', 'España 1569', '2612359874', 14),
(38334139, 'Lucas', 'Gonzalez', '1994-05-18', 'Juan B. Justo 235', '2616209794', 13),
(38654215, 'Elvio', 'Lao', '1995-06-02', 'Mendoza 5647', '2612036985', NULL),
(38987420, 'Gabriel', 'Catalan', '1994-12-11', 'Catamarca 56', '2614523015', 16),
(39383777, 'Sebastian', 'Ruiz', '1996-02-11', 'Perito Moreno 2209', '2616802269', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrera`
--

CREATE TABLE `carrera` (
  `car_cod` bigint(10) NOT NULL,
  `car_nombre` varchar(30) NOT NULL,
  `car_duracion` int(3) DEFAULT NULL COMMENT 'Duracion en semestres'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `carrera`
--

INSERT INTO `carrera` (`car_cod`, `car_nombre`, `car_duracion`) VALUES
(1, 'Tecnicatura Programacion', 4),
(2, 'Ingenieria en Sistemas', 10),
(4, 'Enología', 3),
(5, 'Tecnicatura en Seg e Higiene', 4),
(6, 'Ingenieria Civil', 10),
(7, 'Medicina Legal', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cursado`
--

CREATE TABLE `cursado` (
  `cur_alu_dni` bigint(10) NOT NULL,
  `cur_mat_cod` bigint(10) NOT NULL,
  `cur_nota` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cursado`
--

INSERT INTO `cursado` (`cur_alu_dni`, `cur_mat_cod`, `cur_nota`) VALUES
(35852687, 8, NULL),
(35877927, 10, NULL),
(35984632, 3, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inscripcion`
--

CREATE TABLE `inscripcion` (
  `insc_cod` bigint(10) NOT NULL,
  `insc_dni_alumno` bigint(30) NOT NULL,
  `insc_fecha` date DEFAULT NULL,
  `insc_car_cod` bigint(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `inscripcion`
--

INSERT INTO `inscripcion` (`insc_cod`, `insc_dni_alumno`, `insc_fecha`, `insc_car_cod`) VALUES
(10, 39383777, '2020-06-15', 1),
(13, 38334139, '2020-06-16', 1),
(14, 37895621, '2020-06-16', 5),
(15, 35852687, '2020-06-17', 1),
(16, 38987420, '2020-06-20', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materia`
--

CREATE TABLE `materia` (
  `mat_cod` bigint(10) NOT NULL,
  `mat_nombre` varchar(30) NOT NULL,
  `mat_prof_dni` bigint(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `materia`
--

INSERT INTO `materia` (`mat_cod`, `mat_nombre`, `mat_prof_dni`) VALUES
(3, 'Programacion 2', 28345987),
(8, 'Programacion 1', 8653148),
(9, 'Laboratorio 1', 12435106),
(10, 'Laboratorio 2', 12436201);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE `profesor` (
  `prof_dni` bigint(10) NOT NULL,
  `prof_nombre` varchar(30) NOT NULL,
  `prof_apellido` varchar(30) NOT NULL,
  `prof_fec_nac` date DEFAULT NULL,
  `prof_domicilio` varchar(100) DEFAULT NULL,
  `prof_telefono` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `profesor`
--

INSERT INTO `profesor` (`prof_dni`, `prof_nombre`, `prof_apellido`, `prof_fec_nac`, `prof_domicilio`, `prof_telefono`) VALUES
(8653148, 'Carlos', 'Rodriguez', '1950-03-16', 'Circunvalacion 985', '2613594123'),
(12435106, 'Silvia', 'Peralta', '1958-01-16', 'San Martin 1622', '2614709440'),
(12436201, 'Julio', 'Monetti', '1958-06-01', 'San Martin 1598', '2615478246'),
(18452248, 'Miriam', 'Castillo', '1947-03-02', 'españa1555', '2614301955'),
(28345987, 'Martin', 'Vargas', '1960-05-04', 'Godoy Cruz 546', '2612589741');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`alu_dni`),
  ADD KEY `alumno_ibfk_1` (`alu_insc_cod`);

--
-- Indices de la tabla `carrera`
--
ALTER TABLE `carrera`
  ADD PRIMARY KEY (`car_cod`);

--
-- Indices de la tabla `cursado`
--
ALTER TABLE `cursado`
  ADD KEY `cursado_ibfk_3` (`cur_alu_dni`),
  ADD KEY `cursado_ibfk_2` (`cur_mat_cod`);

--
-- Indices de la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD PRIMARY KEY (`insc_cod`),
  ADD KEY `inscripcion_ibfk_1` (`insc_car_cod`);

--
-- Indices de la tabla `materia`
--
ALTER TABLE `materia`
  ADD PRIMARY KEY (`mat_cod`),
  ADD KEY `mat_prof_dni` (`mat_prof_dni`);

--
-- Indices de la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD PRIMARY KEY (`prof_dni`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carrera`
--
ALTER TABLE `carrera`
  MODIFY `car_cod` bigint(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `materia`
--
ALTER TABLE `materia`
  MODIFY `mat_cod` bigint(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `alumno_ibfk_1` FOREIGN KEY (`alu_insc_cod`) REFERENCES `inscripcion` (`insc_cod`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Filtros para la tabla `cursado`
--
ALTER TABLE `cursado`
  ADD CONSTRAINT `cursado_ibfk_2` FOREIGN KEY (`cur_mat_cod`) REFERENCES `materia` (`mat_cod`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cursado_ibfk_3` FOREIGN KEY (`cur_alu_dni`) REFERENCES `alumno` (`alu_dni`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `inscripcion`
--
ALTER TABLE `inscripcion`
  ADD CONSTRAINT `inscripcion_ibfk_1` FOREIGN KEY (`insc_car_cod`) REFERENCES `carrera` (`car_cod`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `materia`
--
ALTER TABLE `materia`
  ADD CONSTRAINT `materia_ibfk_1` FOREIGN KEY (`mat_prof_dni`) REFERENCES `profesor` (`prof_dni`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
