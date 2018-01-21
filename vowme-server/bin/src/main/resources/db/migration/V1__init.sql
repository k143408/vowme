--
-- Database: `vowme`
--

-- --------------------------------------------------------

--
-- Table structure for table `approvals`
--

CREATE TABLE `approvals` (
  `id` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `actionby` int(11) NOT NULL,
  `description` text NOT NULL,
  `updated_at` int(11),
  `created_at` int(11)
);

-- --------------------------------------------------------

--
-- Table structure for table `backout`
--

CREATE TABLE `backout` (
  `id` int(11) NOT NULL,
  `causeid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `description` text NOT NULL,
  `updated_at` int(11),
  `created_at` int(11)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--

-- Table structure for table `boardcast`
--

CREATE TABLE `boardcast` (
  `id` int(11) NOT NULL,
  `causeid` int(11) NOT NULL,
  `emailid` int(11) NOT NULL,
   `updated_at` int(11),
  `created_at` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `causetype` (
  `id` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
   `updated_at` int(11),
  `created_at` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `email`
--

CREATE TABLE `email` (
  `id` int(11) NOT NULL,
  `email` varchar(200),
  `title` varchar(100),
  `message` text NOT NULL,
  `success` bit(1) NOT NULL,
  `updated_at` int(11),
  `created_at` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `cause` (
  `id` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `visibilitystatus` bit(1) NOT NULL,
  `registrationdate` int(11) NOT NULL,
  `info` VARCHAR(1000) NOT NULL,
  `registrationdeadline` int(11) NOT NULL,
  `startdate` int(11) NOT NULL,
  `enddate` int(11) NOT NULL,
  `starthour` int(4) NOT NULL,
  `startminute` int(4) NOT NULL,
  `endhour` int(4) NOT NULL,
  `endminute` int(4) NOT NULL,
  `description` text NOT NULL,
  `location` VARCHAR(100) NOT NULL,
  `address` VARCHAR(200) NOT NULL,
  `city` VARCHAR(50) NOT NULL,
  `zipcode` int(7) NOT NULL,
  `wwwaddress` VARCHAR(200) NOT NULL,
  `phone` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `eventType` int(5) NOT NULL,
  `guestsallowed` bit(1) NOT NULL,
  `guestsinvitationallowed` bit(1) NOT NULL,
  `guestbringalongs` bit(1) NOT NULL,
  `maxattendees` int(10) NOT NULL,
  `updated_at` int(11), 
  `created_at` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `participates`
--

CREATE TABLE `participates` (
  `id` int(11) NOT NULL,
  `causeid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `updated_at` int(11), 
  `created_at` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45)  NULL,
  `lastname` varchar(45)  NULL,
  `username` varchar(45)  NULL,
  `password` varchar(45)  NULL,
  `email` varchar(100)  NULL,
  `cnic` varchar(45)  NULL,
  `email_verified` bit(1)  NULL,
  `cnic_verified` bit(1)  NULL,
  `is_active` bit(1)  NULL,
  `created_at` int(11)  NULL,
  `updated_at` int(11)  NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
  );
--
-- Indexes for dumped tables
--

--
-- Indexes for table `approvals`
--
ALTER TABLE `approvals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userid` (`userid`),
  ADD KEY `actionby` (`actionby`);

--
-- Indexes for table `backout`
--
ALTER TABLE `backout`
  ADD PRIMARY KEY (`id`),
  ADD KEY `causeid` (`causeid`),
  ADD KEY `userid` (`userid`);

--
-- Indexes for table `boardcast`
--
ALTER TABLE `boardcast`
  ADD PRIMARY KEY (`id`),
  ADD KEY `causeid` (`causeid`),
  ADD KEY `emailid` (`emailid`);

--
-- Indexes for table `email`
--
ALTER TABLE `email`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `event`
--
ALTER TABLE `cause`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userid` (`userid`);

--
-- Indexes for table `participates`
--
ALTER TABLE `participates`
  ADD PRIMARY KEY (`id`),
  ADD KEY `causeid` (`causeid`),
  ADD KEY `userid` (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `approvals`
--
ALTER TABLE `approvals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `backout`
--
ALTER TABLE `backout`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `boardcast`
--
ALTER TABLE `boardcast`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `email`
--
ALTER TABLE `email`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `cause`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `participates`
--
ALTER TABLE `participates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
--
-- Constraints for dumped tables
--

--
-- Constraints for table `approvals`
--
ALTER TABLE `approvals`
  ADD CONSTRAINT `user_fk` FOREIGN KEY (`userid`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `user_fk2` FOREIGN KEY (`actionby`) REFERENCES `user` (`id`);

--
-- Constraints for table `backout`
--
ALTER TABLE `backout`
  ADD CONSTRAINT `eventfk` FOREIGN KEY (`causeid`) REFERENCES `cause` (`id`),
  ADD CONSTRAINT `userfk` FOREIGN KEY (`userid`) REFERENCES `user` (`id`);

--
-- Constraints for table `boardcast`
--
ALTER TABLE `boardcast`
  ADD CONSTRAINT `boardcast_ibfk_1` FOREIGN KEY (`causeid`) REFERENCES `cause` (`id`),
  ADD CONSTRAINT `boardcast_ibfk_3` FOREIGN KEY (`emailid`) REFERENCES `email` (`id`);


--
-- Constraints for table `participates`
--
ALTER TABLE `participates`
  ADD CONSTRAINT `participates_ibfk_1` FOREIGN KEY (`causeid`) REFERENCES `cause` (`id`),
  ADD CONSTRAINT `participates_ibfk_2` FOREIGN KEY (`userid`) REFERENCES `user` (`id`);
