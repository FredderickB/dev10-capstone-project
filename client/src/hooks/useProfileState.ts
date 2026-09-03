import { useState, useEffect, useCallback, type ChangeEvent, type FormEvent } from 'react';
import { deleteGame, fetchGames, sendResignation } from '../services/GameApi';
import type { GameSummaryDto } from '../services/utils/DTOs/GameDtos';

export interface profilePageState {
 
    games: GameSummaryDto[] | null

}

export function useProfileState(token: string | null) {
  const [profileState, setProfileState] = useState<profilePageState>({
  games: null,
  });

 const loadGames = useCallback(async () => {
    if (!token) return;

    const result = await fetchGames(token);

    if (result.success && result.data) {
      setProfileState({ games: result.data });
    } else {
      setProfileState({ games: [] });
    }
  }, [token]);

  useEffect(() => {
    loadGames();
  }, [loadGames]);


  
    const ConfirmDeleteClick = useCallback(
      async (gameId: number) => {

        const result = await deleteGame(token, gameId)

        if (result.success) {
         await loadGames();
        } else {
          console.error("failed to handle delete", result.errors);
        }
      }, [token]
    )


  return {
    profileState,
    ConfirmDeleteClick
  };
}