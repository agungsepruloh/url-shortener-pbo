-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 07 Jan 2020 pada 16.00
-- Versi server: 10.1.36-MariaDB
-- Versi PHP: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `url-shortener`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `auth_role`
--

CREATE TABLE `auth_role` (
  `auth_role_id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  `role_desc` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `auth_role`
--

INSERT INTO `auth_role` (`auth_role_id`, `role_name`, `role_desc`) VALUES
(1, 'SUPER_USER', 'This user has ultimate rights for everything'),
(2, 'ADMIN_USER', 'This user has admin rights for administrative work'),
(3, 'SITE_USER', 'This user has access to site, after login - normal user');

-- --------------------------------------------------------

--
-- Struktur dari tabel `auth_user`
--

CREATE TABLE `auth_user` (
  `auth_user_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `auth_user`
--

INSERT INTO `auth_user` (`auth_user_id`, `first_name`, `last_name`, `email`, `password`, `status`) VALUES
(1, 'admin', 'admin', 'admin@google.com', '$2a$10$4EgLcbTEIRHA9FXC2d1xuuqDyOF36xc9LaglYtPNxEE6ynqYjWVaa', 'VERIFIED');

-- --------------------------------------------------------

--
-- Struktur dari tabel `auth_user_role`
--

CREATE TABLE `auth_user_role` (
  `auth_user_id` int(11) NOT NULL,
  `auth_role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `auth_user_role`
--

INSERT INTO `auth_user_role` (`auth_user_id`, `auth_role_id`) VALUES
(1, 1),
(1, 2),
(1, 3);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `auth_role`
--
ALTER TABLE `auth_role`
  ADD PRIMARY KEY (`auth_role_id`);

--
-- Indeks untuk tabel `auth_user`
--
ALTER TABLE `auth_user`
  ADD PRIMARY KEY (`auth_user_id`);

--
-- Indeks untuk tabel `auth_user_role`
--
ALTER TABLE `auth_user_role`
  ADD PRIMARY KEY (`auth_user_id`,`auth_role_id`),
  ADD KEY `FK_user_role` (`auth_role_id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `auth_role`
--
ALTER TABLE `auth_role`
  MODIFY `auth_role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `auth_user`
--
ALTER TABLE `auth_user`
  MODIFY `auth_user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `auth_user_role`
--
ALTER TABLE `auth_user_role`
  ADD CONSTRAINT `FK_auth_user` FOREIGN KEY (`auth_user_id`) REFERENCES `auth_user` (`auth_user_id`),
  ADD CONSTRAINT `FK_auth_user_role` FOREIGN KEY (`auth_role_id`) REFERENCES `auth_role` (`auth_role_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
