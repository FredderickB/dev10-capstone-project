import { useNavigate, useParams } from 'react-router-dom';
import { useEffect } from 'react';

import Board from '../Board';
import MatchInfo from '../MatchInfo';
import BoardActions from '../BoardActions';
import PlayerActions from '../PlayerActions';

import { useAuth } from '../../contexts/AuthContext';
import MoveResponseContainer from '../MoveResponseContainer';
import { useGameSession } from '../../hooks/useGameSession';

export default function GamePage() {
  const { gameId: rawGameId } = useParams<{ gameId: string }>();
  const { token } = useAuth();
  const navigate = useNavigate()

  const {
    gameState,
    handleBoardToggle,
    handleInfoToggle,
    handleInputChange,
    handleMoveSubmission,
    handleResign
  } = useGameSession(rawGameId, token)

  useEffect(() => {
      if (!token) {
        navigate('/')
      }
    }, [token, navigate])

  return (
    <div>
      {gameState.viewBoard ? (
        <Board fen={gameState.fen} playerColor={gameState.gameDto?.playerColor}/>
      ) : (
        <p>[ board hidden ]</p>
      )}

        <BoardActions
          boardToggle={handleBoardToggle}
          infoToggle={handleInfoToggle}
          handleInputChange={handleInputChange}
          handleMoveSubmission={handleMoveSubmission}
          playerSan={gameState.playSan}
          gameStatus={gameState.gameStatus}
        />

      {gameState.viewInfo ? (
        <MatchInfo
          moveNumber={gameState.moveNumber}
          moveColor={gameState.colorToMove}
        />
      ) : (
        <p>[ match info hidden ]</p>
      )}

      <MoveResponseContainer errors={gameState.errors} engineResponse={gameState.engineSan} gameStatus={gameState.gameStatus} />
      <PlayerActions handleResign={handleResign} gameStatus={gameState.gameStatus}/>
    </div>
  );
}
