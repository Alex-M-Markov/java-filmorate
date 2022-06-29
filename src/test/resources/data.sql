INSERT INTO Films (Name, Description, Release_Date, Duration, Mpa_ID)
VALUES ('Ghostbusters',
        'American supernatural comedy film directed and produced by Ivan Reitman',
        '1984-06-08', '105', '3');

INSERT INTO Films (Name, Description, Release_Date, Duration, Mpa_ID)
VALUES ('Avatar',
        'American[7][8] epic science fiction film directed, written, produced, and co-edited by James Cameron',
        '2009-12-10', '162', '5');

INSERT INTO Films (Name, Description, Release_Date, Duration, Mpa_ID)
VALUES ('Good Will Hunting',
        'American drama film written by Ben Affleck and Matt Damon',
        '1997-12-02', '126', '5');

INSERT INTO Users (Login, Name, Email, Birth_Date)
VALUES ('Pete123', 'Peter', 'woo@yahoo.com', '1977-05-22');

INSERT INTO Users (Login, Name, Email, Birth_Date)
VALUES ('frankie', 'Frank', 'fk@google.ca', '2010-12-25');

INSERT INTO Users (Login, Name, Email, Birth_Date)
VALUES ('LCUK', 'Lucie', 'lucie@lucie.com', '1988-11-02');

INSERT INTO Friendship (User_ID, Friend_ID, Approval)
VALUES (1, 3, true);