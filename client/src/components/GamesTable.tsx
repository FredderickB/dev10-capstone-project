import React from 'react'
import type { GameSummaryDto } from '../services/utils/DTOs/GameDtos'

interface props {
  games: GameSummaryDto[] | null
}

export default function GamesTable({ games }: props) {

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
            <td>{game.createdAt ? new Date(game.createdAt).toLocaleDateString() : 'N/A'}</td>
            <td>{game.engineLevel}</td>
            <td>{game.playerColor}</td>
            <td>{game.boardPeaks}</td>
            <td>{game.status}</td>
            <td>{game.totalMoves}</td>
          </tr>
        ))}
      </tbody>
    </table>
  )
}
