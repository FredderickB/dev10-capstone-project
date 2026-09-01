import { GoogleLogin } from '@react-oauth/google';
import type { CredentialResponse } from '@react-oauth/google';
import { useState } from 'react';
import { fetchJwt } from '../services/AuthApi';
import { useAuth } from '../contexts/AuthContext';

export const GoogleLoginButton = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string[] | null>(null);
  const { token, setToken } = useAuth();

  const handleGoogleSuccess = async (credentialResponse: CredentialResponse) => {
    const idToken = credentialResponse.credential;

    if (!idToken) {
      setError(['Google login failed: No credential returned']);
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const result = await fetchJwt(idToken);

      if (!result.success) {
        setError(result.errors)
      } else {
        
        const appJwt = result.data?.token;
        if (appJwt) {
          setToken(appJwt);
        }

      }

    } catch (err: any) {
      console.error('Login error:', err);
      setError(err.message || 'Failed to authenticate');
    } finally {
      setLoading(false);
    }
  };

  const handleGoogleFailure = () => {
    setError(['Google Sign-In was cancelled or failed.']);
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