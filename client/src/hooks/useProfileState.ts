import { useState, useEffect, useCallback, type ChangeEvent, type FormEvent } from 'react';
import { deleteGame, fetchGames, sendResignation } from '../services/GameApi';
import type { GameSummaryDto } from '../services/utils/DTOs/GameDtos';

export interface profilePageState {
 
    games: GameSummaryDto[] | null

}

export function useProfileState(token: string | null) {
  const [profileState, setProfileState] = useState<profilePageState>({
  games: [],
  });

  useEffect(() => {
    async function loadGames() {
      if (!token) return;

      const result = await fetchGames(token);

      if (result.success && result.data) {
        setProfileState((prev) => ({
            ...prev, games: result.data
        })
        )
      } else {
        
      }
    }

    loadGames();
  }, [token]);


  
    const ConfirmDeleteClick = useCallback(
      async (gameId: number) => {

        const result = await deleteGame(token, gameId)

        if (result.success) {
          setProfileState((prev) => ({
            ...prev,
            games: prev.games ? prev.games.filter((game) => game.gameId !== gameId) : [] 
          }));
        } else {
          console.error("failed to handle resign", result.errors);
        }
      }, [token]
    )


  return {
    profileState,
    ConfirmDeleteClick
  };
}