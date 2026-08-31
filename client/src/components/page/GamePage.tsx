import React, { useEffect, useState, type ChangeEvent, type FormEvent } from 'react';
import { useParams } from 'react-router-dom';

import Board from '../Board';
import MatchInfo from '../MatchInfo';
import BoardActions from '../BoardActions';
import PlayerActions from '../PlayerActions';

import { useAuth } from '../AuthContext';
import { fetchGame } from '../../services/GameApi';
import { sendMove } from '../../services/MoveApi';
import { normalizeSan } from '../../utils/sanNormalizer';

import type { GameResponseDto } from '../../services/utils/DTOs/GameDtos';
import type { MoveRequestDto } from '../../services/utils/DTOs/MoveDtos';
import MoveResponseContainer from '../MoveResponseContainer';

interface GamePageState {
  viewBoard: boolean;
  viewInfo: boolean;
  errors: string[];
  gameId: number;
  gameDto: GameResponseDto | null;
  fen: string | undefined;
  playSan: string;
  engineSan: string | undefined;
  peakedAtBoard: boolean;
  moveNumber: number | undefined;
  gameStatus: string;
  colorToMove: string;
  moveList: string[];
}

export default function GamePage() {
  const { gameId: rawGameId } = useParams<{ gameId: string }>();
  const { token } = useAuth();

  const [gameState, setGameState] = useState<GamePageState>({
    viewBoard: false,
    viewInfo: false,
    errors: [],
    gameId: 0,
    gameDto: null,
    fen: undefined,
    playSan: '',
    engineSan: undefined,
    peakedAtBoard: false,
    moveNumber: 1,
    gameStatus: 'IN_PROGRESS',
    colorToMove: 'white',
    moveList: [],
  });

  const handleBoardToggle = () => {
    setGameState((prev) => ({
      ...prev,
      viewBoard: !prev.viewBoard,
      peakedAtBoard: true,
    }));
  };

  const handleInfoToggle = () => {
    setGameState((prev) => ({
      ...prev,
      viewInfo: !prev.viewInfo,
    }));
  };

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const san = normalizeSan(event.target.value);
    setGameState((prev) => ({
      ...prev,
      playSan: san,
    }));
  };

  const handleMoveSubmission = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const { gameId, gameDto, fen, moveNumber, playSan, peakedAtBoard } = gameState;

    if (!gameId || !gameDto || !fen || !moveNumber) {
      console.log('Missing game context for submission');
      return;
    }

    const dto: MoveRequestDto = {
      gameId,
      moveNumber,
      playerSan: playSan,
      engineLevel: gameDto.engineLevel,
      currentFen: fen,
      peakedAtBoard,
    };

    const result = await sendMove(token, dto);

    if (!result.success) {
      setGameState((prev) => ({
        ...prev,
        errors: result.errors ?? ['Failed to execute move'],
      }));
    } else if (result.data) {
      const updatedFen = result.data.updatedFen;
      const nextColor = getColorToMove(updatedFen) ?? 'white';
      const moveNum = getMoveNumber(updatedFen) ?? 1;

      setGameState((prev) => ({
        ...prev,
        errors: [],
        moveNumber: moveNum,
        engineSan: result.data?.engineSan,
        fen: updatedFen,
        colorToMove: nextColor,
        playSan: '',
        peakedAtBoard: false,
        viewBoard: false,
        moveList: [...prev.moveList, playSan, ...(result.data?.engineSan ? [result.data.engineSan] : [])],
      }));
    }
  };

  useEffect(() => {
    async function loadGame() {
      if (!rawGameId) return;

      const parsedGameId = parseInt(rawGameId, 10);
      if (isNaN(parsedGameId)) {
        setGameState((prev) => ({
          ...prev,
          errors: ['Invalid Game ID'],
        }));
        return;
      }

      const result = await fetchGame(token, parsedGameId);

      if (result.success && result.data) {
        const initialFen = result.data.fen;
        const moveNum = getMoveNumber(initialFen) ?? 1;
        setGameState((prev) => ({
          ...prev,
          moveNumber: moveNum,
          gameId: parsedGameId,
          gameDto: result.data,
          fen: initialFen,
          gameStatus: result.data?.status ?? 'IN_PROGRESS',
          colorToMove: getColorToMove(initialFen) ?? 'white',
          errors: [],
        }));
      } else {
        setGameState((prev) => ({
          ...prev,
          errors: result.errors ?? ['Failed to load game'],
        }));
      }
    }

    loadGame();
  }, [rawGameId, token]);

  return (
    <div>
      {gameState.viewBoard ? (
        <Board fen={gameState.fen} />
      ) : (
        <p>[ board hidden ]</p>
      )}

      <BoardActions
        boardToggle={handleBoardToggle}
        infoToggle={handleInfoToggle}
        handleInputChange={handleInputChange}
        handleMoveSubmission={handleMoveSubmission}
        playerSan={gameState.playSan}
      />

      {gameState.viewInfo ? (
        <MatchInfo
        moveNumber={gameState.moveNumber}
        moveColor={gameState.colorToMove}
        moveList={gameState.moveList}
      />
      ) : (
        <p>[ match info hidden ]</p>
      )}
      
      <MoveResponseContainer errors={gameState.errors} engineResponse={gameState.engineSan} gameStatus={gameState.gameStatus} />
      <PlayerActions />
    </div>
  );
}

function getColorToMove(fen: string | undefined): string | null {
  if (!fen) return null;
  const parts = fen.trim().split(/\s+/);
  if (parts.length < 2) return null;

  const activeColor = parts[1].toLowerCase();

  if (activeColor === 'w') {
    return 'white';
  }

  if (activeColor === 'b') {
    return 'black';
  }

  return null;
}

function getMoveNumber(fen: string | undefined): number | undefined {
  if (!fen) return undefined;
  const parts = fen.trim().split(/\s+/);
  if (parts.length < 5) return undefined;

  const moveNumber =  parseInt(parts[5]);
  console.log(moveNumber)

  return moveNumber;
}