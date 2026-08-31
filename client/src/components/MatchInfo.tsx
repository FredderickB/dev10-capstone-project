import React from 'react'
import MatchMoveList from './MatchMoveList'
import MoveResponseContainer from './MoveResponseContainer'

interface props {
  moveColor: string
  moveList: string[]
  moveNumber: number | undefined

}
export default function MatchInfo({ moveColor, moveNumber }: props) {
  return (
    <div>
      <div>
        {moveColor} to move | move number: {moveNumber}
      </div>
      <MatchMoveList />
    </div>
  )
}
