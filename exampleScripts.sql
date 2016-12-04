-- @version First Fall 2016
--Sample queries
--Return the matches for a student that haven't been invalidated (neither student's validation code is 2 or greater)
SELECT *
FROM Match
WHERE aValid < 2 AND bValid < 2 AND ( aCalvinID = 'aaa11' OR bCalvinID = 'aaa11');

--return a student's profile record and their photo
SELECT *
FROM Student, StudentPicture
WHERE Student.CalvinID = 'aaa11';

--return all of a student's messages in chronological order with most recent first
SELECT *
FROM Message
WHERE toID = 'aaa11' OR fromID = 'aaa11'
ORDER BY timestamp DESC;

