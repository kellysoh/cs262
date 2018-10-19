--
-- This SQL script builds a monopoly database, deleting any pre-existing version.
--
-- @author kvlinden
-- @version Summer, 2015
--

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys.
DROP TABLE IF EXISTS PlayerGame;
DROP TABLE IF EXISTS PlayingGame;
DROP TABLE IF EXISTS Property;
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Player;



-- Create the schema.
CREATE TABLE Game (
	ID integer PRIMARY KEY, 
	Starttime timestamp,
    EndTime timestamp
	);

CREATE TABLE Player (
	ID integer PRIMARY KEY, 
	emailAddress varchar(50) NOT NULL,
	name varchar(50)
	);

CREATE TABLE PlayerGame (
	gameID integer REFERENCES Game(ID), 
	playerID integer REFERENCES Player(ID),
	score integer
	);

CREATE TABLE PlayingGame (
    gameID integer REFERENCES Game(ID),
    playerID integer REFERENCES Player(ID),
    cash integer,
    location varchar(50)
);

CREATE TABLE Property (
	gameID integer REFERENCES Game(ID),
	playerID integer REFERENCES Player(ID),
	property varchar(50),
	house integer CHECK (house <=4),
	hotel boolean
); 

-- Allow users to select data from the tables.
GRANT SELECT ON Game TO PUBLIC;
GRANT SELECT ON Player TO PUBLIC;
GRANT SELECT ON PlayerGame TO PUBLIC;
GRANT SELECT ON PlayingGame TO PUBLIC;
GRANT SELECT ON Property TO PUBLIC; 

-- Add sample records.
INSERT INTO Game VALUES (1, '2006-06-27 08:00:00', '2006-06-27 10:00:00');
INSERT INTO Game VALUES (2, '2006-06-28 13:20:00', '2006-06-28 14:00:00');
INSERT INTO Game VALUES (3, '2006-06-29 18:41:00', '2006-06-29 20:00:00');
INSERT INTO Game VALUES (4, '2018-10-12 20:00:00');

INSERT INTO Player(ID, emailAddress) VALUES (1, 'me@calvin.edu');
INSERT INTO Player VALUES (2, 'king@gmail.edu', 'The King');
INSERT INTO Player VALUES (3, 'dog@gmail.edu', 'Dogbreath');

INSERT INTO PlayerGame VALUES (1, 1, 0.00);
INSERT INTO PlayerGame VALUES (1, 2, 0.00);
INSERT INTO PlayerGame VALUES (1, 3, 2350.00);
INSERT INTO PlayerGame VALUES (2, 1, 1000.00);
INSERT INTO PlayerGame VALUES (2, 2, 0.00);
INSERT INTO PlayerGame VALUES (2, 3, 500.00);
INSERT INTO PlayerGame VALUES (3, 2, 0.00);
INSERT INTO PlayerGame VALUES (3, 3, 5500.00);

INSERT INTO PlayingGame VALUES (4, 1, 100, 'GRAND RAPIDS');
INSERT INTO PlayingGame VALUES (4, 2, 1000,  'RUSSIA');

INSERT INTO Property VALUES (4, 1, 'SEOUL', 2, TRUE);
INSERT INTO Property VALUES (4, 1, 'HELSINKI', 3, FALSE); 
INSERT INTO Property VALUES (4, 2, 'ULAANBAATAR', 4, TRUE );

SELECT * FROM Game;
SELECT * FROM PlayerGame;
SELECT * FROM PlayingGame;
SELECT COUNT(*) AS PLAYER_COUNT FROM Player;
SELECT * FROM Player;
SELECT *  FROM PlayingGame a 
INNER JOIN Property b
ON a.GameID = b.GameID 
AND a.PlayerID = b.PlayerID; 