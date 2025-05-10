-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 10, 2025 at 04:21 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `postly`
--

-- --------------------------------------------------------

--
-- Table structure for table `postlets`
--

CREATE TABLE `postlets` (
  `id` int(10) UNSIGNED NOT NULL,
  `content` text NOT NULL,
  `user` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `is_flagged` tinyint(1) NOT NULL DEFAULT 0,
  `flagged_reason` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `postlets`
--

INSERT INTO `postlets` (`id`, `content`, `user`, `created_at`, `is_flagged`, `flagged_reason`) VALUES
(1, 'Welcome to our bulletin board system!', 1, '2025-05-09 23:00:09', 0, NULL),
(2, 'Admin test post - please ignore', 2, '2025-05-09 23:00:09', 0, NULL),
(3, 'Hello everyone! Just testing the system.', 5, '2025-05-09 23:00:09', 0, NULL),
(4, 'This is a flagged post example', 3, '2025-05-09 23:00:09', 1, 'Inappropriate content'),
(5, 'Another user post for testing', 4, '2025-05-09 23:00:09', 0, NULL),
(6, 'hello buddy', 6, '2025-05-10 00:35:19', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_login` timestamp NULL DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `is_admin`, `created_at`, `last_login`, `is_active`) VALUES
(1, 'admintest', 'admin123', 1, '2025-05-09 23:00:09', NULL, 1),
(2, 'mikalah', 'mikalah123', 1, '2025-05-09 23:00:09', NULL, 1),
(3, 'nacion', 'nacion123', 1, '2025-05-09 23:00:09', NULL, 1),
(4, 'alaan', 'alaan123', 1, '2025-05-09 23:00:09', NULL, 1),
(5, 'regular_user', 'password1', 0, '2025-05-09 23:00:09', NULL, 1),
(6, 'amedesu', 'amedesuwa', 0, '2025-05-09 23:25:26', NULL, 1),
(7, 'ohirume', 'sarada123', 0, '2025-05-10 01:17:12', NULL, 1),
(8, 'jougan', 'boruto123', 0, '2025-05-10 01:18:08', NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `postlets`
--
ALTER TABLE `postlets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_user` (`user`),
  ADD KEY `idx_created` (`created_at`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `idx_username` (`username`),
  ADD KEY `idx_admin_status` (`is_admin`,`is_active`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `postlets`
--
ALTER TABLE `postlets`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `postlets`
--
ALTER TABLE `postlets`
  ADD CONSTRAINT `fk_postlets_user` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
