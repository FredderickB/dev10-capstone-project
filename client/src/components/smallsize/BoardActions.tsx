import React, { type ChangeEventHandler, type FormEvent, type SubmitEventHandler } from 'react'

interface props {
  boardToggle: () => void;
  infoToggle: () => void;
  handleMoveSubmission: SubmitEventHandler<HTMLFormElement>
  handleInputChange: ChangeEventHandler
  playerSan: string
  gameStatus: string
}

export default function BoardActions({ boardToggle, infoToggle, handleInputChange, handleMoveSubmission, playerSan, gameStatus }: props) {
  return (
    <>
      <button onClick={boardToggle}>toggle board</button>
      <button onClick={infoToggle}>toggle match info</button>
      {gameStatus === 'IN_PROGRESS' ?
        <form onSubmit={handleMoveSubmission}>
          <label htmlFor='move-input' > enter your move </label>
          <input id='move-input' type='text' value={playerSan} onChange={handleInputChange} />
        </form>
        :
        null
      }
    </>
  )
}
