# User stories
blind chess trainer is an app that allows you to play blindfold chess against a bot with varying levels. 

## mvp stories
this is the stories of the minimun viable project. 

### Logged in flows

> user can choose a engine level, and play a match. 

> user can view past games. 

> user can delete past games. 

### logged out flows

> user can sign with with google oauth. 

> user can choose a engine level, blindness difficulty, and play a match. 

### playing a match

> user can toggle board view to view board, after playing move board will disappear. 

> user can type in a legal move and engine will respond. 

> user can type in an illegal move and app will warn user and reprompt. 

> user can exit match and come back later, moves are saved.

> user can resign during a match and game will be saved. 

> user can be checkmated and game will be saved.

> user can win by checkmate and game will be save

> user can draw with 50 move repetition

> user can draw with stalemate

## post-mvp stories
more user stories expanding the minimum viable product

### logged in flows
> user can see profile metrics like total games played, elo level, win rate, peak elo

### playing a match
 > user can choose a blindess difficulty, capping board view toggle till a number of moves played.

 > blindness difficulty 0: board toggle every move

 > blindness difficulty 1: 1 toggle every 2 moves

 > blindness difficulty 2: 1 toggle every 3 ... and so on 

 > user can choose a bot to play against (better ui for choosing engine difficulty)

 > user can press hint button on first press it will suggest a peice that you should move, on second press it will suggest all of its legal moves, on third press it will give you the best engine move.


 ### piece training

 > user can play piece puzzles, like move knight to certain square in 3 moves

 > user can play piece quiz, like can queen on d5 see h5, and do as many as possible in 30 seconds






