-- @version First Fall 2016
--

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys.
DROP TABLE IF EXISTS DATEDATE CASCADE;
DROP TABLE IF EXISTS MESSAGE CASCADE;
DROP TABLE IF EXISTS MATCH CASCADE;
DROP TABLE IF EXISTS STUDENT CASCADE;

-- Create the schema
CREATE TABLE STUDENT (
	CalvinID varchar(50) PRIMARY KEY NOT NULL,
	password varchar(50) NOT NULL,
	picture varchar(100),
	first varchar(50),
	last varchar(50),
	username varchar(50) NOT NULL,
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

CREATE TABLE MATCH (
	aCalvinID varchar(50) REFERENCES Student(CalvinID),
	bCalvinID varchar(50) REFERENCES Student(CalvinID),
	reason varchar(200),
	--Unvalidated is a 0, validated is a 1, invalidated is a 2.
	aValid integer,
	bValid integer
	);

CREATE TABLE MESSAGE (
    ID integer PRIMARY KEY,
	"timestamp" timestamp,
	toID varchar(50) REFERENCES Student(CalvinID),
	fromID varchar(50) REFERENCES Student(CalvinID),
	message varchar(1000)
	);

CREATE TABLE DATEDATE (
	ID integer PRIMARY KEY,
	aCalvinID varchar(50) REFERENCES Student(CalvinID),
	bCalvinID varchar(50) REFERENCES Student(CalvinID),
	aAccept boolean,
	bAccept boolean,
	place varchar(50),
	activity varchar(50),
	"time" timestamp
	);

-- Allow users to select data from the tables.
GRANT SELECT ON TABLE STUDENT TO PUBLIC;
GRANT SELECT ON TABLE MATCH TO PUBLIC;
GRANT SELECT ON TABLE MESSAGE TO PUBLIC;
GRANT SELECT ON TABLE DATEDATE TO PUBLIC;

-- Add sample records.major varchar(100),
INSERT INTO STUDENT VALUES ('jgb23','alphabet','https://postimg.org/image/k1mulom33/','Jay','Bigelow','meliornox','Junior','1995-06-05','Grand Rapids','Michigan','United States of America','Computer Science','Science, Technology, Engineering & Mathematics','one','other','other','None','INTJ',false,'',false,'CS Lounge',5, 'always','Commons','',5,'Library','friday','n',69,'White','To me, vocation is a word that has lost all meaning due to its overuse, its primary function is to pad motivational speeches.','I love having fun','I am looking for a fun person');
INSERT INTO STUDENT VALUES ('jsk44', 'gocalvin', 'https://postimg.org/image/k1mulom33/', 'Joust', 'Knight', 'secretshrek', 'Senior+', '1990-10-17', 'Swamp', '', 'Duloc', 'Film Studies with a minor in International Relations','Communication & Media Studies', '1', 'Male', 'Female', 'None', 'ISTP', true, 'Scaring villagers', false, 'Home', 10, 'sometimes', 'Knollcrest', 'Hockey', 10, 'Swamp', 'Wednesday', 's', 100, 'Ogre', 'Eh, I guess I have purpose in life', 'I do life like its a job', 'I want a girl who likes me');
INSERT INTO STUDENT VALUES ('aaa11', 'password', 'https://postimg.org/image/k1mulom33/', 'Muhammed', 'Lee', 'generic', 'Freshman', '1992-01-01', 'Grand Rapids', 'Michigan', 'United States of America', 'English and Reading', 'humanities', 'two' ,'male', 'female', 'Catholic', '' , false, '', true, '', 5, 'never', '', '', 5, '', '', 'a', 68, 'Chinese', 'I do life well. That is vocation', 'Date me!', 'If you are interested, lets talk');

INSERT INTO MATCH VALUES ('jgb23', 'aaa11', 'Both born in Grand Rapids, Michigan, United States of America', 0, 1);
INSERT INTO MATCH VALUES ('jgb23', 'jsk44', 'Why not', 2, 0);
INSERT INTO MATCH VALUES ('jsk44', 'aaa11', 'Both do not know TULIP', 0, 0);

INSERT INTO MESSAGE VALUES (1, CURRENT_TIMESTAMP, 'jsk44', 'aaa11', 'How are things?');
INSERT INTO MESSAGE VALUES (2, CURRENT_TIMESTAMP, 'aaa11', 'jsk44', '');
INSERT INTO MESSAGE VALUES (3, CURRENT_TIMESTAMP, 'jsk44', 'aaa11', 'Okay...');

INSERT INTO DATEDATE VALUES (1, 'jgb23', 'aaa11', true, false, 'Johnnys', 'Eat Lunch', '2016-11-08 12:00:00');
INSERT INTO DATEDATE VALUES (2, 'jgb23', 'jsk44', false, false, 'Fish House', 'Get Coffee', '2116-12-25 00:00:00');
INSERT INTO DATEDATE VALUES (3, 'jsk44', 'aaa11', true, false, 'Spoelhof Center', 'Work Out', '2016-11-12 13:14:15');
