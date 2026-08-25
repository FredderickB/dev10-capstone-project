import { GoogleLogin } from '@react-oauth/google';
import type { CredentialResponse } from '@react-oauth/google';
import { useState } from 'react';

interface GoogleLoginButtonProps {
  onLoginSuccess?: (appJwt: string) => void;
}

export const GoogleLoginButton = ({ onLoginSuccess }: GoogleLoginButtonProps) => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleGoogleSuccess = async (credentialResponse: CredentialResponse) => {
    const idToken = credentialResponse.credential;

    if (!idToken) {
      setError('Google login failed: No credential returned');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const response = await fetch('http://localhost:8080/api/auth/google/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ idToken }),
      });

      if (!response.ok) {
        throw new Error(`Backend authentication failed: ${response.status}`);
      }

      const data = await response.json();
      const appJwt = data.token;

      localStorage.setItem('token', appJwt);

      if (onLoginSuccess) {
        onLoginSuccess(appJwt);
      }
    } catch (err: any) {
      console.error('Login error:', err);
      setError(err.message || 'Failed to authenticate');
    } finally {
      setLoading(false);
    }
  };

  const handleGoogleFailure = () => {
    setError('Google Sign-In was cancelled or failed.');
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '10px' }}>
      {loading ? (
        <p>Authenticating...</p>
      ) : (
        <GoogleLogin
          onSuccess={handleGoogleSuccess}
          onError={handleGoogleFailure}
          shape="rectangular"
          theme="outline"
          size="large"
          
        />
      )}

      {error && <p style={{ color: 'red', fontSize: '14px' }}>{error}</p>}
    </div>
  );
};