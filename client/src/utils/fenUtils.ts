
export function getColorToMove(fen: string | undefined): string | null {
  if (!fen) return null;
  const parts = fen.trim().split(/\s+/);
  if (parts.length < 2) return null;

  const activeColor = parts[1].toLowerCase();

  if (activeColor === 'w') {
    return 'white';
  }

  if (activeColor === 'b') {
    return 'black';
  }

  return null;
}

export function getMoveNumber(fen: string | undefined): number | undefined {
  if (!fen) return undefined;
  const parts = fen.trim().split(/\s+/);
  if (parts.length < 5) return undefined;

  const moveNumber =  parseInt(parts[5]);
  console.log(moveNumber)

  return moveNumber;
}