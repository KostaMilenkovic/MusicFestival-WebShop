-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8001
-- Generation Time: Feb 15, 2017 at 07:12 PM
-- Server version: 5.6.33
-- PHP Version: 7.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `pia_project_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `festival`
--

CREATE TABLE `festival` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `place` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `cost_day` int(11) NOT NULL,
  `cost_all` int(11) NOT NULL,
  `user_ticket_day` int(11) NOT NULL,
  `num_tickets_day` int(11) NOT NULL,
  `status` varchar(255) NOT NULL,
  `num_visits` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `festival_comment`
--

CREATE TABLE `festival_comment` (
  `id` int(11) NOT NULL,
  `festival` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `text` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `festival_performer`
--

CREATE TABLE `festival_performer` (
  `id` int(11) NOT NULL,
  `festival` int(11) NOT NULL,
  `performer` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `festival_rating`
--

CREATE TABLE `festival_rating` (
  `id` int(11) NOT NULL,
  `festival` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `rating` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `performer`
--

CREATE TABLE `performer` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `ticket` int(11) NOT NULL,
  `status` varchar(255) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `social_network`
--

CREATE TABLE `social_network` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `link` varchar(255) NOT NULL,
  `festival` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

CREATE TABLE `ticket` (
  `id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `festival` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `role` int(11) NOT NULL,
  `num_res_attempts` int(11) NOT NULL,
  `approved` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `festival`
--
ALTER TABLE `festival`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `festival_comment`
--
ALTER TABLE `festival_comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_festivals` (`festival`),
  ADD KEY `id_users` (`user`);

--
-- Indexes for table `festival_performer`
--
ALTER TABLE `festival_performer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_festivals` (`festival`),
  ADD KEY `id_performers` (`performer`);

--
-- Indexes for table `festival_rating`
--
ALTER TABLE `festival_rating`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_festival_2` (`festival`,`user`),
  ADD KEY `id_festival` (`festival`),
  ADD KEY `id_user` (`user`);

--
-- Indexes for table `performer`
--
ALTER TABLE `performer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_2` (`user`,`ticket`),
  ADD KEY `user` (`user`),
  ADD KEY `ticket` (`ticket`);

--
-- Indexes for table `social_network`
--
ALTER TABLE `social_network`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_festivals` (`festival`);

--
-- Indexes for table `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_festivals` (`festival`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `role` (`role`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `festival`
--
ALTER TABLE `festival`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `festival_comment`
--
ALTER TABLE `festival_comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `festival_performer`
--
ALTER TABLE `festival_performer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `festival_rating`
--
ALTER TABLE `festival_rating`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `performer`
--
ALTER TABLE `performer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `social_network`
--
ALTER TABLE `social_network`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `ticket`
--
ALTER TABLE `ticket`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `festival_comment`
--
ALTER TABLE `festival_comment`
  ADD CONSTRAINT `festival_comment_ibfk_1` FOREIGN KEY (`festival`) REFERENCES `festival` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `festival_comment_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `festival_performer`
--
ALTER TABLE `festival_performer`
  ADD CONSTRAINT `festival_performer_ibfk_1` FOREIGN KEY (`performer`) REFERENCES `performer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `festival_performer_ibfk_2` FOREIGN KEY (`festival`) REFERENCES `festival` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `festival_rating`
--
ALTER TABLE `festival_rating`
  ADD CONSTRAINT `festival_rating_ibfk_1` FOREIGN KEY (`festival`) REFERENCES `festival` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `festival_rating_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`ticket`) REFERENCES `ticket` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `social_network`
--
ALTER TABLE `social_network`
  ADD CONSTRAINT `social_network_ibfk_1` FOREIGN KEY (`festival`) REFERENCES `festival` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`festival`) REFERENCES `festival` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role`) REFERENCES `user_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
