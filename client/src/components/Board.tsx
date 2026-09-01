import { Chessboard } from 'react-chessboard';
import { useState } from 'react';

interface props {
  fen: string | undefined;
  playerColor: string | undefined;
}
export default function Board({ fen, playerColor }:props) {

  const resolvedPlayerColor = playerColor === "WHITE"? "white": "black";

  return (
    <div style={{ width: '500px' }}>
      <Chessboard
        options={{
          position: fen,
          allowDragging: false,
          boardOrientation: resolvedPlayerColor
        }}
      />
    </div>
  );
}
