import { useState, useEffect, useCallback, type ChangeEvent, type FormEvent } from 'react';
import { fetchGames, sendResignation } from '../services/GameApi';
import type { GameSummaryDto } from '../services/utils/DTOs/GameDtos';

export interface profilePageState {
 
    games: GameSummaryDto[] | null
}

export function useProfileState(token: string | null) {
  const [profileState, setProfileState] = useState<profilePageState>({
  games: []
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


  


  return {
    profileState
  };
}