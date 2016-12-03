-- @version First Fall 2016
--

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys.
DROP TABLE IF EXISTS Date;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS Match;
DROP TABLE IF EXISTS StudentPicture;
DROP TABLE IF EXISTS Student;

-- Create the schema.
CREATE TABLE Student (
	CalvinID varchar(50) PRIMARY KEY, 
	password varchar(50),
	first varchar(50),
	last varchar(50),
	username varchar(50),
	collegeStartYear integer,
	calvinGradYear integer,
	calvinGradMonth varchar(50),
	birthday date,
	homeCity varchar(50),
	homeState varchar(50),
	homeCountry varchar(50),
	major varchar(100),
	gender varchar(50),
	religion varchar(50),
	mbti varchar(50),
	hasJob boolean,
	job varchar(50),
	calvinT boolean,
	calvinU boolean,
	calvinL boolean,
	calvinI boolean,
	calvinP boolean,
	hangout varchar(50),
	hateHope integer,
	bQuiv integer,
	diningPreference varchar(50),
	sports varchar(50),
	bunHate integer,
	vocation varchar(200),
	studySpot varchar(50),
	chapelDay varchar(50),
	loft boolean,
	oceanO integer,
	oceanC integer,
	oceanE integer,
	oceanA integer,
	oceanN integer,
	height integer,
	nationality varchar(50)
	);

CREATE TABLE StudentPicture (
	CalvinID varchar(50) REFERENCES Student(CalvinID),
	picture blob
	);

CREATE TABLE Match (
	aCalvinID varchar(50) REFERENCES Student(CalvinID),
	bCalvinID varchar(50) REFERENCES Student(CalvinID),
	reason varchar(200),
	aValid integer,
	bValid integer,
	date date
	);
	
CREATE TABLE Message (
    	ID integer PRIMARY KEY,
	timestamp timestamp,
	toID varchar(50) REFERENCES Student(CalvinID),
	fromID varchar(50) REFERENCES Student(CalvinID),
	message varchar(1000)
	);
	
CREATE TABLE Date(
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
GRANT SELECT ON StudentPicture TO PUBLIC;
GRANT SELECT ON Match TO PUBLIC;
GRANT SELECT ON Message TO PUBLIC;
GRANT SELECT ON Date TO PUBLIC;

-- Add sample records.
INSERT INTO Student VALUES ('jgb23', 'alphabet', 'Jay', 'Bigelow', 'meliornox', 2013, 2018, 'May', '1995-06-05', 'Grand Rapids', 'Michigan', 'United States of America', 'Computer Science', 'Demiguy', 'None', 'INTJ', false, '', false, false, false, false, false, 'CS Lounge', 5, 0, 'Commons', '', 5, 'To me, vocation is a word that has lost all meaning due to its overuse, its primary function is to pad motivational speeches.', 'Library', '', false, 0, 0, 0, 0, 0, 69, 'White');
INSERT INTO Student VALUES ('jsk44', 'gocalvin', 'Joust', 'Knight', 'secretshrek', 2001, 2013, 'September', '1990-10-17', 'Swamp', '', 'Duloc', 'Film Studies with a minor in International Relations', 'Male', 'None', 'ISTP', true, 'Scaring villagers', false, false, false, false, false, 'Home', 10, 0, 'Knollcrest', 'Hockey', 10, 'Being with my family', 'Swamp', 'Wednesday', false, 50, 26, 35, 87, 1, 86, 'Ogre');
INSERT INTO Student VALUES ('aaa11', 'password', 'Muhammed', 'Lee', 'generic', 2000, 2004, 'May', '1992-01-01', 'Grand Rapids', 'Michigan', 'United States of America', 'English', 'None', 'None', '', false, '', false, false, false, false, false, '', 5, 0, '', '', 5, '', '', '', false, 0, 0, 0, 0, 0, 65, 'Chinese');

INSERT INTO StudentPicture VALUES ('jgb23', E'\\000');
INSERT INTO StudentPicture VALUES ('jsk44', E'\\000');
INSERT INTO StudentPicture VALUES ('aaa11', E'\\000');

INSERT INTO Match VALUES ('jgb23', 'aaa11', 'Both born in Grand Rapids, Michigan, United States of America', 0, 1, '2016-11-02');
INSERT INTO Match VALUES ('jgb23', 'jsk44', 'Why not', 2, 0, '2016-11-01');
INSERT INTO Match VALUES ('jsk44', 'aaa11', 'Both do not know TULIP', 0, 0, '2016-10-31');

INSERT INTO Message VALUES (1, CURRENT_TIMESTAMP, 'jsk44', 'aaa11', 'How are things?');
INSERT INTO Message VALUES (2, CURRENT_TIMESTAMP, 'aaa11', 'jsk44', '');
INSERT INTO Message VALUES (3, CURRENT_TIMESTAMP, 'jsk44', 'aaa11', 'Okay...');

INSERT INTO Date VALUES ('jgb23', 'aaa11', true, false, 'Johnnys', 'Eat Lunch', '2016-11-08 12:00:00');
INSERT INTO Date VALUES ('jgb23', 'jsk44', false, false, 'Fish House', 'Get Coffee', '2116-12-25 00:00:00');
INSERT INTO Date VALUES ('jsk44', 'aaa11', true, false, 'Spoelhof Center', 'Work Out', '2016-11-12 13:14:15');
