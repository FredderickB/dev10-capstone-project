import React, { useState } from 'react'
import Board from '../Board'
import MatchInfo from '../MatchInfo'
import BoardActions from '../BoardActions'
import PlayerActions from '../PlayerActions'
import { useParams } from 'react-router-dom'

export default function GamePage() {

  const [viewBoard, setViewBoard] = useState<boolean>(false)
  const { gameId } = useParams();

  function handleBoardToggle() {

    let currentView = viewBoard;
    setViewBoard(!currentView);

  }

  return (
    <div>
      {viewBoard ?
        <Board />
        :
        <p>[ board hidden ]</p>
      }
      <BoardActions boardToggle={handleBoardToggle}/>
      <MatchInfo />
      <PlayerActions />
    </div>
  )
}
