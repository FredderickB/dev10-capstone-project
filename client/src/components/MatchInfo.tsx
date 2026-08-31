import React from 'react'
import MatchStatus from './MatchStatus'
import MatchMoveList from './MatchMoveList'
import MoveResponseContainer from './MoveResponseContainer'

interface props {

  errors: string[]
  engineResponse: string | undefined
  gameStatus: string | undefined

}
export default function MatchInfo({ errors, engineResponse, gameStatus }: props) {
  return (
    <div>
      <MatchStatus />
      <MatchMoveList />
      <MoveResponseContainer errors={errors} engineResponse={engineResponse} gameStatus={gameStatus} />
    </div>
  )
}
