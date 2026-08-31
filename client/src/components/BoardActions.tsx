import React, { type ChangeEventHandler, type FormEvent, type SubmitEventHandler } from 'react'

interface props {
  boardToggle: () => void;
  handleMoveSubmmission: SubmitEventHandler<HTMLFormElement>;
  handleInputChange: ChangeEventHandler
  playerSan: string
}

export default function BoardActions({boardToggle, handleInputChange, handleMoveSubmmission, playerSan}: props) {
  return (
    <>
      <button onClick={boardToggle}>toggle board</button>
      <form onSubmit={handleMoveSubmmission}>
        <label htmlFor='move-input' > enter your move </label>
        <input id='move-input' type='text' value={playerSan} onChange={handleInputChange}/>
      </form>
    </>
  )
}
