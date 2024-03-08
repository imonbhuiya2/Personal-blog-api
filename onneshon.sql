-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 30, 2023 at 07:39 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `onneshon`
--

-- --------------------------------------------------------

--
-- Table structure for table `blogs`
--

CREATE TABLE `blogs` (
  `id` int(11) NOT NULL,
  `added_date` datetime(6) DEFAULT NULL,
  `blog_content` varchar(5000) NOT NULL,
  `blog_image` varchar(500) NOT NULL,
  `blog_title` varchar(500) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `blogs`
--

INSERT INTO `blogs` (`id`, `added_date`, `blog_content`, `blog_image`, `blog_title`, `category_id`, `user_id`) VALUES
(17, '2023-04-12 16:36:52.000000', 'Spring boot is a Java Framework ', 'test.png', 'Java Spring Boot', 4, 2),
(18, '2023-04-12 16:37:44.000000', 'Windows are written by C++ programming language', 'test.png', 'C++', 4, 2),
(19, '2023-04-12 22:00:06.000000', 'Updated', 'test.png', 'Updated', 5, 2),
(20, '2023-04-12 22:00:52.000000', 'Noone teach us for sex Education in India, Bangladesh', 'test.png', 'Poor Sex Education in ASIA?!', 5, 2),
(21, '2023-04-12 22:02:05.000000', 'Students are frustrated because of the FALTU EDUCATION SYSTEM', 'test.png', 'Student cums into the book', 1, 2),
(22, '2023-04-12 22:02:53.000000', 'Organization just need the skills NOT the education', 'test.png', 'NO MORE EDUCATION?!', 1, 2),
(23, '2023-04-12 22:03:22.000000', 'Nongra rajniti', 'test.png', 'Politics in BELKUCHI', 6, 2),
(25, '2023-04-14 11:24:38.000000', 'Karim Benzema and Marco Asensio struck for Real Madrid as they dominated 10-man Chelsea and opened up a 2-0 Champions League quarter-final first leg lead on Wednesday.The record 14-time winners and reigning champions eased to a comfortable win against Frank Lampard\'s side, who had Ben Chilwell sent off in the second half for pulling down Rodrygo Goes as he ran through on goal.Benzema netted from close range after 21 minutes after Kepa Arrizabalaga tipped a Vinicius Junior effort into his path. Despite controlling the game, Madrid had to wait until the 74th minute for their second, with Asensio drilling home from the edge of the box after a short corner routine.', 'test.png', 'Champions League Dominant Madrid earn solid lead on Chelsea', 6, 2),
(26, '2023-04-14 11:25:19.000000', 'Mohammedan team management informed that Shakib will feature in both games despite having a ‘family emergency’ which prompted him to pull ou', 'test.png', 'Shakib to keep playing for Mohammedan despite ‘family emergency’', 6, 2),
(27, '2023-04-14 11:25:50.000000', 'Skipper Rohit Sharma smashed 65 as Mumbai Indians edged out Delhi Capitals by six wickets in a last-ball IPL thriller to register their first win of the season on Tuesday. Mumbai\'s chase of 173 moved to the final delivery when impact player Tim David and Cameron Green got the winning runs to hand hosts Delhi their fourth straight loss in as many matches. Delhi, led by David Warner and coached by Ricky Ponting, have endured a horrendous run in the T20 tournament to be in danger of an early exit from the play-off race.', 'test.png', 'IPL Mumbai win first match in tense chase against Delhi', 6, 2),
(28, '2023-04-14 11:27:29.000000', 'Arsenal manager Mikel Arteta bemoaned the Gunners\' lack of ruthlessness as they surrendered a two-goal lead in a pulsating 2-2 draw at Liverpool on Sunday to blow the Premier League title race wide open.Arteta\'s men edge six points ahead of Manchester City at the top of the table, but the defending champions now have the destiny of the title in their own hands. City have a game in hand and host the Gunners at the Etihad later this month. Goals from Gabriel Martinelli and Gabriel Jesus appeared to have Arsenal cruising towards an eighth consecutive league win. But Mohamed Salah pulled a goal back before half-time to spark a Liverpool fightback. Salah missed a second-half penalty before Roberto Firmino headed in the equaliser three minutes from time. Jurgen Klopp\'s men then missed a series of chances to complete the comeback as Arsenal goalkeeper Aaron Ramsdale salvaged a point for his side.', 'test.png', 'Arsenal held by Liverpool in blow to Premier League title bid', 6, 2),
(29, '2023-04-14 11:28:14.000000', 'Mrittunjony came in place of Taskin Ahmed, who is still recovering from the side strain injury he suffered during the recently concluded home serie', 'test.png', 'Uncapped Mrittunjoy called up for Ireland series, Taskin misses out', 6, 2),
(30, '2023-04-14 11:28:38.000000', 'Mrittunjony came in place of Taskin Ahmed, who is still recovering from the side strain injury he suffered during the recently concluded home serie', 'test.png', 'Uncapped Mrittunjoy called up for Ireland series, Taskin misses out', 6, 3);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL,
  `category_description` varchar(450) NOT NULL DEFAULT 'John',
  `category_title` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `category_description`, `category_title`) VALUES
(1, 'Fucked by this Education System', 'Education'),
(2, 'Baler rajniti 2', 'Politics'),
(3, 'No Liberty!', 'Liberty'),
(4, 'Do programming', 'Programming'),
(5, 'Have sex every seconds', 'Sex Education'),
(6, 'Everything about sports', 'Sports');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `comment_id` int(11) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `blog_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `role`) VALUES
(1001, 'ROLE_NORMAL'),
(1002, 'ROLE_ADMIN');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `about` varchar(1000) NOT NULL,
  `email` varchar(200) NOT NULL,
  `image` varchar(400) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `about`, `email`, `image`, `name`, `password`) VALUES
(2, 'Hridu Da', 'hrid@mail.com', 'img.jpg', 'Hridoy Debnath', '$2a$10$8n5VRRDBmC.0cSNNPjE.eOfBwHTWslPf7iGDi82ROUg.NrBB9Dz6q'),
(3, 'I\'m the developer of this Project', 'sdn@gmail.com', 'img.jpg', 'Shubrato Debnath', '$2a$10$8n5VRRDBmC.0cSNNPjE.eOfBwHTWslPf7iGDi82ROUg.NrBB9Dz6q'),
(102, 'I\'m the developer of this Project', 'kdn@gmail.com', 'img.jpg', 'Keshab Debnath', '1234'),
(152, 'I\'m the developer of this Project', 'kdnmail.com', 'img.jpg', '', ''),
(202, 'I\'m Sourav Debnath from CSE', 'srv@mail.com', 'img.jpg', 'Sourav Debnath', '1234'),
(209, 'I\'m the developer of this webstie', 'shubratodn44985@mail.com', 'User_Image_f953aadcd31e50fa124e_1682618991838_.jpg', 'Shubrato Debnath', '$2a$10$guY7I442S8j1I85AiSVj6.JzIJAlTAHnYsTIQXSA6lGpI4/auNHMm');

-- --------------------------------------------------------

--
-- Table structure for table `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `roles_id`) VALUES
(209, 1001);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blogs`
--
ALTER TABLE `blogs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdby46brdac2ug8ul7b57ea81h` (`category_id`),
  ADD KEY `FKpg4damav6db6a6fh5peylcni5` (`user_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `FK9aakob3a7aghrm94k9kmbrjqd` (`blog_id`),
  ADD KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD KEY `FKa62j07k5mhgifpp955h37ponj` (`roles_id`),
  ADD KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `blogs`
--
ALTER TABLE `blogs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1003;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=210;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `blogs`
--
ALTER TABLE `blogs`
  ADD CONSTRAINT `FKdby46brdac2ug8ul7b57ea81h` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
  ADD CONSTRAINT `FKpg4damav6db6a6fh5peylcni5` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK9aakob3a7aghrm94k9kmbrjqd` FOREIGN KEY (`blog_id`) REFERENCES `blogs` (`id`);

--
-- Constraints for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKa62j07k5mhgifpp955h37ponj` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
