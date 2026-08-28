import type { Result } from "./utils/Result";
import { makeResult } from "./utils/Result";
import type { GameRequestDto, GameResponseDto } from "./utils/DTOs/GameDtos";

const API_URL = "http://localhost:8080/api/games";


export async function createGame(jwtToken : string, requestDto : GameRequestDto) : Promise<Result<GameResponseDto>> {

    const config = {
        method: 'POST',
      headers: {
        'Authorization': jwtToken,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestDto),
    }

    const response = await fetch(`${API_URL}`, config)
    return await makeResult<GameResponseDto>(response);
    
}
