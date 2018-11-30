# Phase 1
## October 23 Meeting
Went through each functionality and specified ambiguities in the project handout relating to
specific functionalities. Decided we will autosave after every move and that we will also implement
a redo button. Briefly went discussed how a user would be represented and how we would store users.

## October 25 Meeting
Decided how UI would need to be changed to account for custom undo and complexity after starting
activity look starting from. Went through each functionality and considered various way it could
be implemented from a high level. Each group member was given a functionality to think
about its implementation at a lower level. Functionalities were assigned as follows:

Yiqing Jin: Scoreboard

Alexander Shum: Undo,Redo

Waasay Shehzad: Auto Save

Afzal Patel: Game Complexity

Didar Ibrahim: Game Complexity

## October 29 Meeting
Further discussed each group members ideas on how to implement functionalities. Came to a common
agreement on our implementation. Created a UML to concretely represent our implementation and make
sure everyone was on same page. CRC cards were made and given to each member corresponding to the
functionality they were given in the previous meeting. Classes closely related to each other
(such as classes corresponding to the same functionality) were were assigned to each member.
Core implementation (excluding UIs) was to be completed by next meeting, Saturday Novemeber 3rd.

## November 2 Meeting
Coded together to fix problems we encountered due to assumptions we made about functionalities
and their corresponding implementations. Integrated the changes. Discussed game launch center and
the user login and signup process in detail: how the UI will look in detail?, what new activities
we would need to create?, how will we process users that login and add save them if they signup?.
Decided we need three new activities before starting activity: one for login, one for signup,
one for game launch center all of which will have an expected layout for their purpose.
Decided we will need to have a users manager that manages adding a new user, checking if login
credentials match a user, setting the current user, etc. Decided we will also need multiple UIs
for scoreboard and realized we needed to further develop it due to different complexities of the
game. By next meeting on November 4 the following tasks were to be completed by the corresponding
members in addition to fixing unexpected results from our previous tasks:

Yiqing Jin: Scoreboard UIs and functionality accounting for different complexities and interaction
with UsersManager.

Alexander Shum: Design User class.

Waasay Shehzad: Specify how login and signup would work with UserManager, and implement it
accordingly.

Afzal Patel: Create UIs for game launch center, login, and sign up.

Didar Ibrahim: Create UIs for game launch center, login, and sign up.

## November 4 Meeting
Integrated our implementation. Had to change some code for integration. Fixed errors regarding
filepath to read and write to for users. Fixed small details overall in app.

## November 5 Meeting
Cleaned code, confirmed JavaDocs were specified, fixed warnings.

# Phase 2

## November 12 Meeting
Came together to discuss game ideas for Phase 2, went over code from Phase 1 to discuss anything
we can fix and make as abstract as possible. We discussed which games relate to Sliding tiles in
terms of complexity, which game would allow a sufficient undo and made UML design to plan out our
abstract classes



## November 15 Meeting
Came to an agreement of choosing Colour guess as our first game of choice, discussed functionalities
required for the game, created a UML to represent our game, the classes, activities, and design.
Discussed whether having a timer or moves would be better to calculate score for users, the number
of coloured tiles we should have, how to distinguish between the difficulties, what new activities
we would need to create, and if colour guess should have undo or not. CRC cards were made and given
to each member to work on the functionality discussed while making UML diagrams. Classes closely
related to each other(such as classes corresponding to the same functionality) were were assigned
to each member. Also discussed ways to make Sliding Tiles solvable and tasks were given out to a
few members to work on making it solvable.

Waasay Shehzad - Colour Guess Manager, Colour Guess Board, Colour Guess Tile, SlidingTiles Manager

Afzal Patel - SignUp, Login, Colour Guess Unittests

Yiqing Jin: All activities required for Colour Guess Game, Scoreboard and Auto-Save

Didar Ibrahim - Tiles for Colour Guess Game, Colour Guess Unittests, SignUp

Alexander Shum - User, States and  UserManager all for Colour Guess Game, SlidingTIles Unittests



## November 19 Meeting
Met up to go over the code each member was assigned to do for Colour Guess Game, fixed any bugs or
issues we had. Discussed ways to make code more efficient and integrated any changes necessary.
Discussed which game we should implement for our second game, was a tough decision as some of the
we had in mind could not process a sufficient undo. After many different game ideas presented by
each group member we came to an agreement of going with 2048.

## November 21 Meeting
Discussed each group members ideas on how to implement functionalities for 2048. Came to a common
agreement on our implementation. Created a UML to concretely represent our implementation and make
sure everyone was on same page. Discussed how to implement swipe up, down, left and right, what new
activities we would need to create, how to make activities with least amount of logic.
CRC cards were made and given to each member corresponding to the functionality they were given in
the previous meeting. Classes closely related to each other(such as classes corresponding to the
same functionality) were were assigned to each member.

Waasay Shehzad - TwentyBoard, TwentyManager, TwentyTile,

Afzal Patel - SignUp, Login, TwentyGame Unittests, GameCentreActivity

Yiqing Jin: All activities required for 2048

Didar Ibrahim - Tiles for TwentyGame, TwentyGame Unittests, SignUp, GameCentreActivity

Alexander Shum - Undo, Redo, Custom Undo, User, States and  UserManager all for 2048

## November 26 Meeting
Came together to discuss the work each member worked on throughout the week, fixed any errors and
resolved any issues we had with the implementation of our game. Fixed tile generating class for
SLiding Tile to make it more abstract. Fixed unittests to generate as much code coverage as possible.
Fixed issues we had with undo and redo for 2048 which was messing up our UserManager. Tested out
both our games to make sure we had no bugs and had all test cases covered.

## November 29 Meeting
Came together for a final meeting to make sure everything was working properly and everyone was up
to speed. Made minor changes to our code and fixed any bugs we encountered. Fixed issues with the
code coverage of unittests, where each member was getting different code coverage %. Worked together
to form an outline of our presentation, cleaned code and did JavaDocs.













