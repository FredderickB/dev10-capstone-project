import React from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../../contexts/AuthContext'

interface props {
  handleResign: () => void
  gameStatus: string
}

export default function PlayerActions({ handleResign, gameStatus }: props) {

  const { token } = useAuth();
  return (
   <div className="d-flex align-items-center justify-content-around gap-2 w-100">
      {/* Resign Button */}
      {gameStatus === 'IN_PROGRESS' ? (
        <button
          type="button"
          onClick={handleResign}
          className="btn btn-outline-danger flex-fill fw-semibold text-uppercase py-2 tracking-wider fs-7 shadow-sm"
        >
          Resign
        </button>
      ) : (
        <button
          type="button"
          disabled
          className="btn btn-outline-secondary flex-fill fw-semibold text-uppercase py-2 tracking-wider fs-7 opacity-50"
        >
          Game Over
        </button>
      )}

      {/* Play Later / Dashboard Link */}
      {token && (
        <Link
          to="/"
          className="btn btn-dojo-gold flex-fill fw-semibold text-uppercase py-2 tracking-wider fs-7 text-center text-decoration-none shadow-sm"
        >
          Play Later
        </Link>
      )}
    </div>
  )
}
