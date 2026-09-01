import type { Move, PgnTurn } from "../services/utils/DTOs/MoveDtos";



export function formatMovesToPgn(moves: Move[]): PgnTurn[] {
  if (!moves || moves.length === 0) return [];

  const turnsMap = new Map<number, PgnTurn>();

  moves.forEach((move) => {
    const fenParts = move.fenAfter.trim().split(/\s+/);
    const nextColorToMove = fenParts[1]?.toLowerCase();
    const isWhiteMove = nextColorToMove === 'b';
    const turnNumber = move.moveNumber;

    const existingTurn: PgnTurn = turnsMap.get(turnNumber) ?? { turnNumber };

    if (isWhiteMove) {
      existingTurn.whiteMove = move.moveSan;
    } else {
      existingTurn.blackMove = move.moveSan;
    }

    turnsMap.set(turnNumber, existingTurn);
  });

  return Array.from(turnsMap.values()).sort((a, b) => a.turnNumber - b.turnNumber);
}