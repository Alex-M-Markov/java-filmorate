# Warmly welcome at the Filmorate!

**This application will lead you into the world of the best films and series. 
You can choose them by name, genre, release date, rating or many other features.**

*If you are interested in the database chart, please have a look at the image below:*

![Database Chart](src/main/resources/database/FilmorateDatabaseChart.png)

*You can use SQL requests to get all information needed.*

Example 1. Get all films:</br>
SELECT * </br>
from Film;

Example 2. Get all users:</br>
SELECT * </br>
from User;

Example 3. Get most popular films:</br>
SELECT Film.Name, </br>
       COUNT(fu.User_ID) </br>
FROM Film </br>
LEFT JOIN Film_User_Likes as fu ON Film.Film_ID = fu.Film_ID </br>
GROUP BY Film.Name </br>
LIMIT 10; </br>

Example 4. Get Mutual friends list:</br>
SELECT User_ID </br>
FROM Friendship_Approved as FA </br>
JOIN Friendship_Approved as FA2 ON FA2.User_id = FA.Friend_ID </br>
WHERE FA.User_id = #add ID#;