## Auth controller
- post auth/login

## user controller
- get user/profile
    user dashboard with win rate and total games

## game controller
- get game/mygames 
    all games of current user.

- post game
    start a new game

- get game/gameid
    get this games fen state

- post game/gameid/resign
    resign the match

- delete game/gameid


## move controller (nested under /game)

- get gameid/moves 
    log of all moves of this game

- post gameid/move
    submit a move to this game, reply is stockfish reply, new fen
