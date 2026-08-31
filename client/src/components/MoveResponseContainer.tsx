import React from 'react';

interface Props {
  errors: string[];
  engineResponse: string | undefined;
  gameStatus: string | undefined;
}

export default function MoveResponseContainer({ errors, engineResponse, gameStatus }: Props) {
  
  if (errors?.length > 0) {
    return (
      <ul>
        {errors.map((err, index) => (
          <li key={index}>{err}</li>
        ))}
      </ul>
    );
  }

  const isGameOver = gameStatus && gameStatus !== 'IN_PROGRESS';

  if (!engineResponse && !isGameOver) {
    return null; 
  }

  return (
    <div>
      {engineResponse && <p>Engine responds with: {engineResponse}</p>}
      {isGameOver && <p>Game over: {gameStatus}</p>}
    </div>
  );
}