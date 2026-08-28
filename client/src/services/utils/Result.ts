export interface Result<T = any> {

    success: boolean;
    status: number;
    data: T | null;
    errors: string[];

}

export async function makeResult<T = any>(response: Response): Promise<Result<T>> {
  if (response.status === 204) {
    return {
      success: true,
      status: 204,
      data: null,
      errors: [],
    };
  }

  let body: any = null;
  try {
    body = await response.json();
  } catch {
    body = await response.text();
  }

  if (!response.ok) {
    let parsedErrors: string[] = [];

    if (Array.isArray(body)) {
      parsedErrors = body.map(String);
    } else if (body && Array.isArray(body.errors)) {
      parsedErrors = body.errors.map(String);
    } else if (body?.message) {
      parsedErrors = [String(body.message)];
    } else {
      parsedErrors = [typeof body === 'string' && body ? body : `Request failed with status ${response.status}`];
    }

    return {
      success: false,
      status: response.status,
      data: null,
      errors: parsedErrors,
    };
  }

  return {
    success: true,
    status: response.status,
    data: body as T,
    errors: [],
  };
}