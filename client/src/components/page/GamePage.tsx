import React from 'react'
import Board from '../Board'
import MatchInfo from '../MatchInfo'
import BoardActions from '../BoardActions'
import PlayerActions from '../PlayerActions'

export default function GamePage() {
  return (
    <div>
      <Board />
      <BoardActions />
      <MatchInfo />
      <PlayerActions />
    </div>
  )
}
