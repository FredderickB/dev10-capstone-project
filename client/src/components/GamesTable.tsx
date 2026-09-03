import React from 'react'
import type { GameSummaryDto } from '../services/utils/DTOs/GameDtos'
import { formatDate } from '../utils/DateUtils'
import { Link } from 'react-router-dom'
import DeleteButton from './DeleteButton'

interface props {
  games: GameSummaryDto[] | null
  ConfirmDeleteClick : (gameId: number) => void
}

export default function GamesTable({ games, ConfirmDeleteClick }: props) {

  if (games === null) {
    return <p>Loading games</p>
  }

  if (games.length === 0) {
    return <p>No games played yet.</p>
  }

  return (
    <table>
      <caption> all your games </caption>
      <thead>
        <th>date</th>
        <th>engine elo</th>
        <th>color</th>
        <th>board peaks</th>
        <th>status</th>
        <th>move number</th>
      </thead>
      <tbody>
        {games.map((game) => (
          <tr key={game.gameId}>
            <td>{formatDate(game.createdAt)}</td>
            <td>{game.engineLevel}</td>
            <td>{game.playerColor}</td>
            <td>{game.boardPeaks}</td>
            <td>{game.status}</td>
            <td>{game.totalMoves}</td>
            <td>{game.status === 'IN_PROGRESS' ?
              <Link to={`/game/${game.gameId}`}>continue</Link>
              :
              <DeleteButton gameId={game.gameId} ConfirmDeleteClick={ConfirmDeleteClick}/>
            }</td>
          </tr>
        )).toReversed()}
      </tbody>
    </table>
  )
}
