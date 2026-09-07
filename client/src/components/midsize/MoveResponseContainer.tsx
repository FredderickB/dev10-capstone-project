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
    return <p className="text-parchment opacity-50 fst-italic text-center my-1 fs-7">[ check move history ]</p> 
  }

  return (
    <div>
      {engineResponse && <p className='text-danger'>Engine responds with: {engineResponse}</p>}
      {isGameOver && <p>Game over: {gameStatus}</p>}
    </div>
  );
}