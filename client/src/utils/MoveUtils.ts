import type { Move, PgnTurn } from "../services/utils/DTOs/MoveDtos";

export function formatMovesToPgn(moves: Move[]): PgnTurn[] {
  if (!moves || moves.length === 0) return [];

  const pgn: PgnTurn[] = [];

  for (let i = 0; i < moves.length; i += 2) {
    const white = moves[i];
    const black = moves[i+1];

    pgn.push({
      turnNumber: Math.floor(i/2) + 1,
      whiteMove: white ? white.moveSan : '',
      blackMove: black ? black.moveSan : '...'
    })
  }

  return pgn;

}