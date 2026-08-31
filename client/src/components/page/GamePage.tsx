import React, { useEffect, useState, type ChangeEvent, type FormEventHandler, type FormEvent } from 'react'
import Board from '../Board'
import MatchInfo from '../MatchInfo'
import BoardActions from '../BoardActions'
import PlayerActions from '../PlayerActions'
import { useParams } from 'react-router-dom'
import { useAuth } from '../AuthContext'
import { fetchGame } from '../../services/GameApi'
import type { GameResponseDto } from '../../services/utils/DTOs/GameDtos'
import type { MoveRequestDto } from '../../services/utils/DTOs/MoveDtos'
import { sendMove } from '../../services/MoveApi'
import { normalizeSan } from '../../utils/sanNormalizer'

export default function GamePage() {

  const [viewBoard, setViewBoard] = useState<boolean>(false)
  const [errors, setErrors] = useState<string[]>([])
  const { gameId } = useParams();
  const { token } = useAuth();


  const [game, setGame] = useState<GameResponseDto | null>(null);
  const [playerSan, setPlayerSan] = useState<string>("");
  const [engineSan, setEngineSan] = useState<string | undefined>("");
  const [moveNumber, setMoveNumber] = useState<number>(0);
  const [peakedAtBoard, setPeakedAtBoard] = useState<boolean>(false);
  const [fen, setFen] = useState<string | undefined>(game?.fen);
  const [gameStatus, setGameStatus] = useState()

  function handleBoardToggle() {

    let currentView = viewBoard;
    setViewBoard(!currentView);
    setPeakedAtBoard(true);

  }

  function handleInputChange(event: ChangeEvent<HTMLInputElement>) {
    const san = normalizeSan(event.target.value);
    setPlayerSan(san)
  }

  const handleMoveSubmission = async (event: FormEvent<HTMLFormElement>) => {

    event.preventDefault();

    if (!gameId) {
      console.log("gameId undefined");
      return;
    }
    const parsedGameId = parseInt(gameId, 10);

    if (!game) {
      console.log("game undefined");
      return;
    }

    if (!fen) {
      console.log("fen not defined")
      return
    }

    const dto: MoveRequestDto = {
      gameId: parsedGameId,
      moveNumber: moveNumber,
      playerSan: playerSan,
      engineLevel: game.engineLevel,
      currentFen: fen,
      peakedAtBoard: peakedAtBoard
    };

    const result = await sendMove(token, dto);

    if (!result.success) {
      setErrors(result.errors)
    } else if (result.data) {
      setErrors([])
      setEngineSan(result.data.engineSan)
      setFen(result.data.updatedFen)
      setGameStatus(gameStatus)
    }
    setPlayerSan("")

  }

  useEffect(() => {

    async function loadGame() {

      if (!gameId) return;

      const parsedGameId = parseInt(gameId, 10);
      if (isNaN(parsedGameId)) {
        setErrors(['Invalid Game ID']);
        return;
      }

      const result = await fetchGame(token, parsedGameId);

      if (result.success) {
        setGame(result.data);
        setFen(result.data?.fen);
      } else {
        setErrors(result.errors);
      }

    }

    loadGame();
  }, [gameId, fen])

  return (

    <div>
      {viewBoard ? (
        <Board fen={fen} />
      ) : (
        <p>[ board hidden ]</p>
      )}
      <BoardActions boardToggle={handleBoardToggle} handleInputChange={handleInputChange} handleMoveSubmmission={handleMoveSubmission} playerSan={playerSan} />
      <MatchInfo errors={errors} engineResponse={engineSan} gameStatus={game?.status} />
      <PlayerActions />
    </div>

  );
}
