import React from 'react'

interface props {
  errors: string[]
  engineResponse: string | undefined
  gameStatus: string | undefined
}

export default function MoveResponseContainer({ errors, engineResponse, gameStatus}: props) {
  return (
    <>
      {errors?.length > 0 ? (
        <ul>
          {errors.map((err, index) => (
            <li key={index}>{err}</li>
          ))}
        </ul>
      ) : (
        <div>
         engine responds with: {engineResponse}
         {gameStatus == 'IN_PROGRESS' ? null:<p>Game over: {gameStatus}</p>}
        </div>
      )}
    </>
  )
}
