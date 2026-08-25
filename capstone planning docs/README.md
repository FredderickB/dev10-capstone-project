### project proposal: blind fold chess trainer

Mandatory:
- user stories (ideally split into mvp + post-mvp)
- schema diagram
- wireframes
 
Suggested:
- controller endpoints
- frontend component tree
- backend class diagram

## general overview

- blind fold chess training app built with react type script and java spring boot security, oauth, stockfish api, chesslib

## domain knowledge
- SAN: move notation used by humans. ex: Nf3, dxe5
- PGN: list of SAN moves representing a full game. ex: 1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O Be7...
- FEN: line of text to describe state of game. ex: rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1
    - capital letters represent white pieces, numbers are consecutive empty space. 
    - whos turn it is to move
    - castle rights
    - en passant target square
    - halfclock move count since last pawn move or capture
    - full move number, increments after every black move
- UCI: move notation used by machine, specifies a piece location and their next location. ex: e2e4, g1f3