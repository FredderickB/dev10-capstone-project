import React from 'react'
import type { GameSummaryDto } from '../../services/utils/DTOs/GameDtos'
import { formatDate } from '../../utils/DateUtils'
import { Link } from 'react-router-dom'
import DeleteButton from '../smallsize/DeleteButton'

interface props {
  games: GameSummaryDto[] | null
  ConfirmDeleteClick : (gameId: number) => void
}

export default function GamesTable({ games, ConfirmDeleteClick }: props) {

  if (games === null) {
    return (
      <div className="text-center p-4">
        <p className="text-parchment opacity-50 fst-italic font-serif mb-0">Loading games...</p>
      </div>
    );
  }

  if (games.length === 0) {
    return (
      <div className="text-center p-4 border border-secondary border-opacity-25 rounded-3 bg-dark bg-opacity-50">
        <p className="text-parchment opacity-50 fst-italic font-serif mb-0">No games played yet.</p>
      </div>
    );
  }

  return (
    <div className="table-responsive border border-gold rounded-3 bg-dark bg-opacity-75 p-3 shadow-lg">
      <caption className="caption-top text-uppercase text-gold font-serif fw-bold tracking-wider fs-6 mb-2 ps-1">
        Your Games
      </caption>
      <table 
        className="table table-dark table-hover align-middle mb-0 font-serif"
        style={{ tableLayout: 'fixed' }}
      >
        <thead>
          <tr className="border-bottom border-gold text-gold text-uppercase fs-7 tracking-wider">
            <th scope="col" className="bg-transparent">Date</th>
            <th scope="col" className="bg-transparent" style={{ width: '120px' }}>Engine ELO</th>
            <th scope="col" className="bg-transparent" style={{ width: '85px' }}>Color</th>
            <th scope="col" className="bg-transparent" style={{ width: '85px' }}>Peeks</th>
            <th scope="col" className="bg-transparent">Status</th>
            <th scope="col" className="bg-transparent">Moves</th>
            <th scope="col" className="bg-transparent text-end" style={{ width: '150px' }}>Action</th>
          </tr>
        </thead>
        <tbody>
          {games.map((game) => (
            <tr key={game.gameId} className="border-bottom border-secondary border-opacity-25 fs-7">
              <td className="bg-transparent text-parchment">{formatDate(game.createdAt)}</td>
              <td className="bg-transparent text-parchment">{game.engineLevel}</td>
              <td className="bg-transparent text-uppercase text-parchment">{game.playerColor}</td>
              <td className="bg-transparent text-parchment">{game.boardPeaks}</td>
              <td className="bg-transparent">
                <span className={`badge ${game.status === 'IN_PROGRESS' ? 'bg-success text-white' : 'bg-secondary bg-opacity-50 text-parchment'}`}>
                  {game.status.replace(/_/g, ' ')}
                </span>
              </td>
              <td className="bg-transparent text-parchment">{game.totalMoves}</td>
              <td className="bg-transparent text-end">
                {game.status === 'IN_PROGRESS' ? (
                  <Link
                    to={`/game/${game.gameId}`}
                    className="btn btn-outline-warning btn-sm text-uppercase fw-bold fs-7 tracking-wider"
                  >
                    Continue
                  </Link>
                ) : (
                  <DeleteButton gameId={game.gameId} ConfirmDeleteClick={ConfirmDeleteClick} />
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
