import { Chessboard } from 'react-chessboard';
import { useState } from 'react';

interface props {
  fen: string | undefined;
}
export default function Board({ fen }:props) {

  const [ viewBoard, setViewBoard ] = useState<boolean>(false)

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
