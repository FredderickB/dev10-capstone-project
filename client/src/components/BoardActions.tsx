import React from 'react'

interface props {
  boardToggle: () => void;
}

export default function BoardActions({boardToggle}: props) {
  return (
    <>
      <button onClick={boardToggle}>toggle board</button>
      <label htmlFor='move-input'> enter your move </label>
      <input id='move-input' type='text'/>
    </>
  )
}
