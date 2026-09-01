import React from 'react'
import MatchMoveList from './MatchMoveList'
interface props {
  moveColor: string
  moveNumber: number | undefined

}
export default function MatchInfo({ moveColor, moveNumber}: props) {
  return (
    <div>
      <div>
        {moveColor} to move | move number: {moveNumber}
      </div>
      <MatchMoveList moveNumber={moveNumber}/>
    </div>
  )
}
