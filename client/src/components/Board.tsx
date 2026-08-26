import { Chessboard } from 'react-chessboard';
import { useState } from 'react';

export default function Board() {

  const [fen] = useState('rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1');

  return (
    <div style={{ width: '500px' }}>
      <Chessboard
        options={{
          position: fen,
          allowDragging: false,
        }}
      />
    </div>
  );
}
