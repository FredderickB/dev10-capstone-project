import { useState, useEffect, useCallback, type ChangeEvent, type FormEvent } from 'react';
import { deleteGame, fetchGames, sendResignation } from '../services/GameApi';
import type { GameSummaryDto } from '../services/utils/DTOs/GameDtos';
import type { UserDto } from '../services/utils/DTOs/UserDtos';
import { fetchUserName } from '../services/UserApi';

export interface profilePageState {

  userName: UserDto | null;
  games: GameSummaryDto[] | null

}

export function useProfileState(token: string | null) {
  const [profileState, setProfileState] = useState<profilePageState>({
    userName: null,
    games: null,
  });

  const loadGames = useCallback(async () => {
    if (!token) return;

    const result = await fetchGames(token);

    if (result.success && result.data) {
      setProfileState((prev) => ({ ...prev, games: result.data }));
    } else {
      setProfileState((prev) => ({ ...prev, games: result.data }));
    }
  }, [token]);

  useEffect(() => {
    loadGames();
    loadUserName();
  }, [loadGames]);

  const loadUserName = useCallback(async () => {
    if (!token) return;

    const result = await fetchUserName(token)

    if (result.success && result.data) {
      setProfileState((prev) => ({...prev, userName: result.data}))

    }
  }, [token])

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