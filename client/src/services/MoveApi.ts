import type { MoveResponseDto, MoveRequestDto } from "./utils/DTOs/MoveDtos";
import type { Result } from "./utils/Result";
import { makeResult } from "./utils/Result";


const API_URL = "http://localhost:8080/api/move";

export async function sendMove(jwtToken: string | null, moveRequest: MoveRequestDto): Promise<Result<MoveResponseDto>>{
    

    if (!jwtToken) {
        jwtToken = ''
      }
    
      const config = {
        method: 'POST',
        headers: {
          'Authorization': jwtToken,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(moveRequest),
      }
    
      const response = await fetch(`${API_URL}`, config)
      return await makeResult<MoveResponseDto>(response);


}