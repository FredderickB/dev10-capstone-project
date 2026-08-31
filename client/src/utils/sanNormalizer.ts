export function normalizeSan(input: string): string {
  if (!input) return '';

  let san = input.trim().replace(/\s+/g, '');

  if (/^(0-0-0|o-o-o|O-O-O)$/i.test(san)) return 'O-O-O';
  if (/^(0-0|o-o|O-O)$/i.test(san)) return 'O-O';

  san = san.replace(/^[nrqk]/, (match) => match.toUpperCase());

  // Piece Moves & Captures 
  const pieceMoveRegex = /^([NBRQK])([a-h1-8]{1,2})?(x)?([a-h][1-8])(=[NBRQKnbrqk])?([\+#])?$/i;
  const pieceMatch = san.match(pieceMoveRegex);

  if (pieceMatch) {
    const piece = pieceMatch[1]; // Already uppercase (N, B, R, Q, K)
    const disambiguation = pieceMatch[2] ? pieceMatch[2].toLowerCase() : '';
    const capture = pieceMatch[3] ? 'x' : '';
    const target = pieceMatch[4].toLowerCase();
    const promotion = pieceMatch[5] ? pieceMatch[5].toUpperCase() : '';
    const checkOrMate = pieceMatch[6] || '';

    return `${piece}${disambiguation}${capture}${target}${promotion}${checkOrMate}`;
  }

  // 5. Standard Pawn Push 
  const pawnPushRegex = /^([a-h][1-8])=?([nbrqNBRQ])?([\+#])?$/i;
  const promoMatch = san.match(pawnPushRegex);

  if (promoMatch) {
    const target = promoMatch[1].toLowerCase();
    const promotedPiece = promoMatch[2] ? `=${promoMatch[2].toUpperCase()}` : '';
    const checkOrMate = promoMatch[3] || '';

    return `${target}${promotedPiece}${checkOrMate}`;
  }

  return san;
}