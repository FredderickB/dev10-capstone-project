import React from 'react'
import MatchStatus from './MatchStatus'
import MatchMoveList from './MatchMoveList'
import MoveResponseContainer from './MoveResponseContainer'

export default function MatchInfo() {
  return (
    <div>
      <MatchStatus />
      <MatchMoveList />
      <MoveResponseContainer />
    </div>
  )
}
