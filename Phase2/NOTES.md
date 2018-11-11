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










