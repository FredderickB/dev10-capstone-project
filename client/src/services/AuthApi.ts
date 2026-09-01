import { makeResult, type Result } from "./utils/Result";

const API_URL = "http://localhost:8080/api/auth";

interface AuthResponse {
    token: string;
}

export async function fetchJwt(idToken: string): Promise<Result<AuthResponse>> {

    const config ={
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ idToken }),
      };

    const response = await fetch(`${API_URL}/google/login`, config);
    return await makeResult<AuthResponse>(response)
    
}