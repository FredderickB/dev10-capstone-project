
import type { UserDto } from "./utils/DTOs/UserDtos";
import { makeResult } from "./utils/Result";
import type { Result } from "./utils/Result";

const API_URL = "http://localhost:8080/api/user";

export async function fetchUserName(jwtToken: string | null): Promise<Result<UserDto>> {

    if (!jwtToken) {
        jwtToken = ''
    }

    const config = {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${jwtToken}`,
        },
    }

    const response = await fetch(`${API_URL}`, config);

    return await makeResult<UserDto>(response);

}