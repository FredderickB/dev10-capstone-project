import { useState, useEffect, useCallback, type ChangeEvent, type FormEvent } from 'react';
import { fetchGame, sendResignation } from '../services/GameApi';
import { sendMove } from '../services/MoveApi';
import { normalizeSan } from '../utils/sanNormalizer';
import { getColorToMove, getMoveNumber } from '../utils/fenUtils';
import type { GameResponseDto } from '../services/utils/DTOs/GameDtos';
import type { MoveRequestDto } from '../services/utils/DTOs/MoveDtos';

export interface GamePageState {
  viewBoard: boolean;
  viewInfo: boolean;
  errors: string[];
  gameId: number;
  gameDto: GameResponseDto | null;
  fen: string | undefined;
  playSan: string;
  engineSan: string | undefined;
  peakedAtBoard: boolean;
  moveNumber: number;
  gameStatus: string;
  colorToMove: string;
}

export function useGameSession(rawGameId: string | undefined, token: string | null) {
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
  });

  const loadGame = useCallback(

    async () => {
      if (!rawGameId) return;
  
      const parsedGameId = parseInt(rawGameId, 10);
      if (isNaN(parsedGameId)) {
        setGameState((prev) => ({ ...prev, errors: ['Invalid Game ID'] }));
        return;
      }
  
      const result = await fetchGame(token, parsedGameId);
  
      if (result.success && result.data) {
        const initialFen = result.data.fen;
        const playerColor = result.data.playerColor;
  
        const initialMoveNumber = getMoveNumber(initialFen)
  
        setGameState((prev) => ({
          ...prev,
          moveNumber: initialMoveNumber,
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
    }, [token]
  )
 
  
  
  useEffect(() => {

    loadGame();
  }, [rawGameId, token]);

  const handleBoardToggle = useCallback(() => {
    setGameState((prev) => ({
      ...prev,
      viewBoard: !prev.viewBoard,
      peakedAtBoard: true,
    }));
  }, []);

  const handleInfoToggle = useCallback(() => {
    setGameState((prev) => ({ ...prev, viewInfo: !prev.viewInfo }));
  }, []);

  const handleInputChange = useCallback((event: ChangeEvent<HTMLInputElement>) => {
    const san = normalizeSan(event.target.value);
    setGameState((prev) => ({ ...prev, playSan: san }));
  }, []);

  const handleMoveSubmission = useCallback(
    async (event: FormEvent<HTMLFormElement>) => {
      event.preventDefault();

      const { gameId, gameDto, fen, moveNumber, playSan, peakedAtBoard } = gameState;

      if (!gameId || !gameDto || !fen || moveNumber === undefined) {
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
        const nextMoveNum = result.data.moveNumber + 1;

        setGameState((prev) => ({
          ...prev,
          errors: [],
          engineSan: result.data?.engineSan,
          playSan: '',
          peakedAtBoard: false,
          viewBoard: false
        }));

        loadGame();
      }
    },
    [gameState, token]
  );

  const handleResign = useCallback(async () => {
    const result = await sendResignation(token, gameState.gameId);

    if (!result.success) {
      setGameState((prev) => ({
        ...prev,
        errors: result.errors ?? ['Failed to execute resignation'],
      }));
    } else {
      setGameState((prev) => ({ ...prev, gameStatus: 'RESIGN' }));
    }
  }, [token, gameState.gameId]);

  return {
    gameState,
    handleBoardToggle,
    handleInfoToggle,
    handleInputChange,
    handleMoveSubmission,
    handleResign,
  };
}