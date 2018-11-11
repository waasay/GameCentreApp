#Android Studio setup instructions
1. URL to clone: https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0548
2. Answer "Yes" to whether you want to create a new Android Studio project.
3. Select "Import project from external model", choose "Gradle" and click Next.
4. Add "Phase1/GameCenter" to the Gradle project path.
5. Choose "Add root" when getting the message "Unregistered VS root detected".
6. Create an Android Virtual Device within Android Studio. Select Pixel2 as the device to emulate,
specifying the device OS as Android 8.1 API 27.
7. Now you are able to run it


#Functionalities of the GameCenter App
When launch the app will bring the user to the login page, and ask the user for inputs
for username and password

If the user don't have an account, the sign up button will lead the user to the sign up
page and the user can create an account there.

Likewise at the sign up, the user will be asked to input username and password to create
an account, and the app will go through its files of users to make sure the input username
is unique, otherwise creating an account fails, and a toast will be displayed. Upon
successful the app will lead back the user to the login in page.

If the user successfully input a username with its corresponding matching password prior
to clicking the sign in button, the app will lead the user to gamecenter page, which allows
the user to pick which game to play. (Currently there is only sliding tiles).

Within the Sliding Tile homepage the user can click to the new game button to start a new
game, which then lead the user to the choosing complexity page, the user can first set the
maximum undo moves, otherwise if unset the default maximum undo moves would be 3. The user
then can pick which difficulty of the sliding tile will be play by clicking on any of the
difficulty button.

The easy, medium, and hard button will generate a 3 by 3, 4 by 4 and 5 by 5 board respectively.

Once the board and the tiles are display, the can touch any tiles that are adjacent to an empty
tile to move that tile that is touched to the empty tile. The score will be recorded. The score
indicates how many moves are done.

The user can also undo a move if the undo button is clicked. The user can also redo to the state
prior to the undo button is clicked if the user click on the redo button. The user can undo the
specified input or default maximum undo times. Once any arbitrary number of undo is done then
the user moves a tile, redo will not be possible anymore. In addition also to prevent the user
from cheating his/her way back to the beginning of the game. Only the maximum undo number plus
1(the current board) number states of the board will be store in a container, only states that
is store in that container can be undo to, once an undo is done, any number of times, to one of
the past states, and a new move is done, all the future moves after the state that is undo till
will be deleted.

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









.