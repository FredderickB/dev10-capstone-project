import React from 'react'
import MatchMoveList from './MatchMoveList'
interface props {
  moveColor: string
  moveNumber: number | undefined

}
export default function MatchInfo({ moveColor, moveNumber}: props) {
  return (
    <div className="d-flex align-items-center justify-content-between font-serif">
      <span className="text-parchment fs-6">
        <strong className="text-gold text-capitalize">{moveColor || 'White'}</strong> turn
      </span>
      <span className="text-parchment opacity-75 fs-6">
        Move <strong className="text-gold">{moveNumber ?? 1}</strong>
      </span>
    </div>
  );
}
