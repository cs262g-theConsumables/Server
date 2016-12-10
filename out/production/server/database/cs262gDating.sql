-- @version First Fall 2016
--

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys.
DROP TABLE IF EXISTS Date;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS Match;
DROP TABLE IF EXISTS Student;

-- Create the schema.
CREATE TABLE Student (
	CalvinID varchar(50) PRIMARY KEY,
	password varchar(50),
	picture blob,
	first varchar(50),
	last varchar(50),
	username varchar(50),
	classYear varchar(50),
	birthday date,
	homeCity varchar(50),
	homeState varchar(50),
	homeCountry varchar(50),
	major varchar(100),
	majorDepartment varchar(50),
	majorNumber varchar(50),
	gender varchar(50),
	genderWant varchar(50),
	religion varchar(50),
	mbti varchar(5),
	hasJob boolean,
	job varchar(50),
	tulip boolean,
	hangout varchar(50),
	hateHope integer,
	bQuiv varchar(50),
	diningPreference varchar(50),
	sports varchar(50),
	bunHate integer,
	studySpot varchar(50),
	chapelDay varchar(50),
	loft char(1),
	height integer,
	nationality varchar(50),
	vocation varchar(200),
	aboutMe varchar(200),
	status varchar(200)
	);

CREATE TABLE Match (
	aCalvinID varchar(50) REFERENCES Student(CalvinID),
	bCalvinID varchar(50) REFERENCES Student(CalvinID),
	reason varchar(200),
	--Unvalidated is a 0, validated is a 1, invalidated is a 2.
	aValid integer,
	bValid integer,
	);

CREATE TABLE Message (
  ID integer PRIMARY KEY,
	timestamp timestamp,
	toID varchar(50) REFERENCES Student(CalvinID),
	fromID varchar(50) REFERENCES Student(CalvinID),
	message varchar(1000)
	);

CREATE TABLE Datedate(
	ID integer PRIMARY KEY,
	aCalvinID varchar(50) REFERENCES Student(CalvinID),
	bCalvinID varchar(50) REFERENCES Student(CalvinID),
	aAccept boolean,
	bAccept boolean,
	place varchar(50),
	activity varchar(50),
	time timestamp
	);

-- Allow users to select data from the tables.
GRANT SELECT ON Student TO PUBLIC;
GRANT SELECT ON Match TO PUBLIC;
GRANT SELECT ON Message TO PUBLIC;
GRANT SELECT ON Date TO PUBLIC;

-- Add sample records.
INSERT INTO Student VALUES ('jgb23', 'alphabet', E'\\000', 'Jay', 'Bigelow', 'meliornox', 2013, 2018, 'May', '1995-06-05', 'Grand Rapids', 'Michigan', 'United States of America', 'Computer Science', 'Demiguy', 'None', 'INTJ', false, '', false, false, false, false, false, 'CS Lounge', 5, 0, 'Commons', '', 5, 'To me, vocation is a word that has lost all meaning due to its overuse, its primary function is to pad motivational speeches.', 'Library', '', false, 0, 0, 0, 0, 0, 69, 'White');
INSERT INTO Student VALUES ('jsk44', 'gocalvin', E'\\000', 'Joust', 'Knight', 'secretshrek', 2001, 2013, 'September', '1990-10-17', 'Swamp', '', 'Duloc', 'Film Studies with a minor in International Relations', 'Male', 'None', 'ISTP', true, 'Scaring villagers', false, false, false, false, false, 'Home', 10, 0, 'Knollcrest', 'Hockey', 10, 'Being with my family', 'Swamp', 'Wednesday', false, 50, 26, 35, 87, 1, 86, 'Ogre');
INSERT INTO Student VALUES ('aaa11', 'password', E'\\000', 'Muhammed', 'Lee', 'generic', 2000, 2004, 'May', '1992-01-01', 'Grand Rapids', 'Michigan', 'United States of America', 'English', 'None', 'None', '', false, '', false, false, false, false, false, '', 5, 0, '', '', 5, '', '', '', false, 0, 0, 0, 0, 0, 65, 'Chinese');

INSERT INTO Match VALUES ('jgb23', 'aaa11', 'Both born in Grand Rapids, Michigan, United States of America', 0, 1, '2016-11-02');
INSERT INTO Match VALUES ('jgb23', 'jsk44', 'Why not', 2, 0, '2016-11-01');
INSERT INTO Match VALUES ('jsk44', 'aaa11', 'Both do not know TULIP', 0, 0, '2016-10-31');

INSERT INTO Message VALUES (1, CURRENT_TIMESTAMP, 'jsk44', 'aaa11', 'How are things?');
INSERT INTO Message VALUES (2, CURRENT_TIMESTAMP, 'aaa11', 'jsk44', '');
INSERT INTO Message VALUES (3, CURRENT_TIMESTAMP, 'jsk44', 'aaa11', 'Okay...');

INSERT INTO Datedate VALUES (1, 'jgb23', 'aaa11', true, false, 'Johnnys', 'Eat Lunch', '2016-11-08 12:00:00');
INSERT INTO Datedate VALUES (2, 'jgb23', 'jsk44', false, false, 'Fish House', 'Get Coffee', '2116-12-25 00:00:00');
INSERT INTO Datedate VALUES (3, 'jsk44', 'aaa11', true, false, 'Spoelhof Center', 'Work Out', '2016-11-12 13:14:15');
