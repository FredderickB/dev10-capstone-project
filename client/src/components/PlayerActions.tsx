import React from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from './AuthContext'

interface props {
  handleResign: () => void
  gameStatus: string
}

export default function PlayerActions({ handleResign, gameStatus }: props) {

  const { token } = useAuth();
  return (
    <>
      {
        gameStatus === 'IN_PROGRESS' ?
          <button onClick={handleResign}> resign </button>
          :
          null
      }

      {
        token &&
        <Link to='/'> play later </Link>
      }
    </>
  )
}
