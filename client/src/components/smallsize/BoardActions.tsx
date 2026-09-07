import React, { type ChangeEventHandler, type FormEvent, type SubmitEventHandler } from 'react'
import '../../styles/BoardActions.css'

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
    <div className="d-flex align-items-center justify-content-between gap-2 w-100">
    
    {/* Toggle Peek Button */}
      <button
        type="button"
        onClick={boardToggle}
        className="dojo-peek-btn fw-semibold text-uppercase px-3 py-2 text-nowrap rounded-3 shadow-sm"
      >
        Toggle Peek
      </button>

      {/* Toggle Peek Button */}
      <button
        type="button"
        onClick={infoToggle}
        className="dojo-peek-btn fw-semibold text-uppercase px-3 py-2 text-nowrap rounded-3 shadow-sm"
      >
        Toggle Info
      </button>

      {/* Move Input Form */}
      {gameStatus === 'IN_PROGRESS' && (
        <form 
          onSubmit={handleMoveSubmission} 
          className="d-flex align-items-center gap-2 flex-grow-1 ms-auto"
          style={{ maxWidth: '280px' }}
        >
          <div className="input-group">
            <span className="input-group-text bg-dark border-gold text-parchment font-serif fs-7 d-none d-sm-inline">
              Enter Move:
            </span>
            <input
              id="move-input"
              type="text"
              value={playerSan}
              onChange={handleInputChange}
              className="form-control form-control-sm dojo-move-input text-parchment fw-bold tracking-wider"
              autoComplete="off"
            />
          </div>
        </form>
      )}
    </div>
  )
}
