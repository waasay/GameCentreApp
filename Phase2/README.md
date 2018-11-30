#Android Studio setup instructions
1. URL to clone: https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0548
2. Answer "Yes" to whether you want to create a new Android Studio project.
3. Select "Import project from external model", choose "Gradle" and click Next.
4. Add "Phase1/GameCenter" to the Gradle project path.
5. Choose "Add root" when getting the message "Unregistered VS root detected".
6. Create an Android Virtual Device within Android Studio. Select Pixel2 as the device to emulate,
specifying the device OS as Android 8.1 API 27.
7. Now you are able to run it


#List of Functionalities of the GameCenter App

GameCenter:
- Allow the user to sign in or sign up an account in the login in page.
- The user can sign up an account by providing app with a username and its password in the sign up page.
If the username existed, a toast will be display.
- The user can sign in with using the username and password it gave to the app. If the input credentials
are wrong a toast will be displayed.
- The user can see view his/her personal best score of different games at different complexities
in his personal scoreboard page, this can be access by clicking MY SCOREBOARD at the GameCenter homepage.

Common across all games:
- A scoreboard for different complexities, it will display the top 5 best player at that given game,
 at that given complexity. It is purposely set in such a way that any user can only  appear at any
 scoreboard once.

SlidingTiles:
- 3 Different complexities. After clicking the new game button in the game's homepage, the user
 can pick the complexity of the game. Easy gets 3 by 3 board, medium gets 4 by 4 and hard gets 5 by 5.
- However before the start of the game, the game will check whether the initially generated board
  is solvable or not, if not the game will keep generate a new one until the board's initial arrangement
  is solvable. The algorithm is based on
  https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
- The board will be autosaved every move, and if the user quits the app the user can load back to that
  the state the user quits the board at.
- Scoring depends on how many moves the user did, the lower the better.
- The score will only be saved if the user solved the board, and did better than him/her previously
  did.
- The initial allowed undo is 3, but the user can defined how many undo the user can maximum do.
- The implementation of undo is identical to that of most word processors such as Latex. For more
  detailed description go down to section ***** below.
- There is also the redo button that allow the user to undo the undo.

ColourGuess:
- For a detailed description of what ColourGuess is go to section ##### below.
- 3 Different complexities. After clicking the new game button in the game's homepage, the user
 can pick the complexity of the game. Easy gets 3 by 3 board, medium gets 4 by 4 and hard gets 5 by 5.
- Scoring depends on how many boards the user solved, the higher the better.
- The score will be saved if the timer reaches 0 or the user failed at solving certain boards.
- Likewise the user's new score will only be saved if he/she out preform his/her's best record.

2048:
- 3 Different complexities. After clicking the new game button in the game's homepage, the user
  can pick the complexity of the game. Easy gets 5 by 5 board, medium gets 4 by 4 and hard gets 3 by 3.
- The board will be autosaved every move, and if the user quits the app the user can load back to that
  the state the user quits the board at.
- Scoring depends on how many moves the user did, the lower the better.
- The score will only be saved if the user solved the board, and did better than him/her previously
  did.
- The initial allowed undo is 3, but the user can defined how many undo the user can maximum do.
- The implementation of undo again is identical to  most word processors. Go to section ***** below
  for more detailed descriptions.
- There is also the redo button that allow the user to undo the undo.


#Description and walk through of the Functionality of the GameCenter App
When launch the app will bring the user to the login page, and ask the user for inputs
for username and password

If the user don't have an account, the sign up button will lead the user to the sign up
page and the user can create an account there.

Likewise at the sign up, the user will be asked to input username and password to create
an account, and the app will go through its files of users to make sure the input username
is unique, otherwise creating an account fails, and a toast will be displayed. Upon
successful the app will lead back the user to the login in page.

If the user successfully input a username with its corresponding matching password prior
to clicking the sign in button, the app will lead the user to gamecentre page, which allows
the user to pick which game to play and see the user's top score for all the available games at all
complexities. To see the user's best score for all games at all complexity, simply click the
MY SCOREBOARD button. The available game are (SlidingTiles, ColourGuess and 2048)

SlidingTiles: After pressing the SlidingTiles button in gamecentre
Within the Sliding Tile homepage the user can click to the new game button to start a new
game, which then lead the user to the choosing complexity page, the user can first set the
maximum undo moves, otherwise if unset the default maximum undo moves would be 3. The user
then can pick which difficulty of the sliding tile will be play by clicking on any of the
difficulty button.

The easy, medium, and hard button will generate a 3 by 3, 4 by 4 and 5 by 5 board respectively.
However before the board and the tiles, are display the game will check whether the board is
solvable, if the board is not solvable, the game will keep on generating new boards until there is
a starting configuration where the board is solvable. The algorithm to determine whether the game
is solvable is found on:
https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html

Once the board and the tiles are display, the can touch any tiles that are adjacent to an empty
tile to move that tile that is touched to the empty tile. The score will be recorded. The score
indicates how many moves are done.

*****
The user can also undo a move if the undo button is clicked. The user can also redo to the state
prior to the undo button is clicked if the user click on the redo button. The user can undo the
specified input or default maximum undo times. Once any arbitrary number of undo is done then
the user moves a tile, redo will not be possible anymore. In addition also to prevent the user
from cheating his/her way back to the beginning of the game. Only the maximum undo number plus
1(the current board) number states of the board will be store in a container, only states that
is store in that container can be undo to, once an undo is done, any number of times, to one of
the past states, and a new move is done, all the future moves after the state that is undo till
will be deleted. (Like most word processors, such as MS word and latex)

The score will be saved after the puzzle is solve, and if the user reach a new lowest record
(the lower the score the better) and the maximum undo move is 3. The score will be saved. One
lowest score will be saved for each difficulty given the maximum undo is 3. If the score the
user got is the top 5 lowest among all the accounts created, it will be displayed on the
scoreboard, of that specific difficulty the user solved.

In addition, once the puzzle is solved, no more moves or undos can be done.

The user can access the scoreboard at sliding tile main page by clicking on the scoreboard
button, the user then can choose the scoreboard to be display by clicking on the corresponding
difficulty button.

Back at the game, if the user quit game, the game will be auto saved, and back at the sliding
tile main page, the user can click load saved game to access that game again. Or alternatively,
the user can also go back the the sliding tile main page and clicked save game to save game,
and access that game again by clicking load game.


ColourGuess: After pressing the ColourGuess button in gamecentre
#####
ColourGuess is a custom designed memory game. Where the game shows the user a board full of randomly
generated coloured tiles,the user have to memorize the colour of all the tiles at all locations under
5 seconds. Then the game will direct the user to a board full white tiles, and the game will ask the
user on where the randomly generated specific colour were on, on the previous board. The user will
have a full minute to solve as many boards as possible, during the 5 seconds viewing phase,
the 1 minute timer won't go down.

At the ColourGuess homepage, the user can start a new game, by clicking the NEW GAME button. Likewise,
there are 3 complexities and by clicking the complexities buttons a different board will be generated,
easy will be a 3 by 3 board, medium will be a 4 by 4 and hard will be a 5 by 5. Then the game will go
on as described above. Again the user got 1 minute to solve as many boards as possible, but if the user
fails or the time is up, the user's score will be saved. The score is based on how many boards the user
solved (The higher the better). And can be accessed in the game's homepage by clicking SCOREBOARD,
the user then click on the corresponding complexity to button the view the scoreboard at that complexity.

2048: After pressing the 2048 button in gamecentre
Likewise compared to SlidingTiles all functionalities s are essentially identical. Autosave per move,
undo implemented in a way similar to most word processors such as Latex. Redo button to undo the undo.
Custom defined undo. Viewing the scoreboard at different complexities. Score depends on how many moves
the user did, the lower the better. The score will only be saved if the user won.
However for complexity, easy gets a 5 by 5 board, medium gets 4 by 4 and hard gets 3 by 3.
The puzzle is considered to be solved if a 2048 tile is created, and for the puzzle to be considered
lost, there should no empty tiles on the board and no tiles could merge when swiping any directions.