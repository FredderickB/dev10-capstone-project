import { createContext, useCallback, useContext, useState, type ReactNode } from 'react';
import { fetchGuestToken, fetchJwt } from '../services/AuthApi';
import { useEffect } from 'react';

interface AuthContextType {
  token: string | null;
  isRegisteredUser: boolean;
  loginWithGoogle: (idToken: string) => Promise<boolean>;
  logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem('token'));
  const [isRegisteredUser, setIsRegisteredUser] = useState<boolean>(false)

  const updateToken = (newToken: string | null) => {
    if (newToken) {
      localStorage.setItem('token', newToken);
    }
    else {
      localStorage.removeItem('token');
    }
    setToken(newToken);
  };

  const initGuestSession = useCallback(async () => {
    const result = await fetchGuestToken();
    if (result.success && result.data) {
      updateToken(result.data.token);
      setIsRegisteredUser(false)
    } else {
      updateToken(null);
      setIsRegisteredUser(false);
    }
  }, [])

  useEffect(() => {
    async function initAuth() {
      const savedToken = localStorage.getItem('token');
      if (savedToken) {
        setToken(savedToken);
        setIsRegisteredUser(true);
      } else {
        await initGuestSession();
      }
    }

    initAuth();
  }, [initGuestSession]);

  const loginWithGoogle = async (idToken: string): Promise<boolean> => {
    const result = await fetchJwt(idToken);
    if (result.success && result.data) {
      const registeredToken = result.data.token;
      
      localStorage.setItem('token', registeredToken);
      setToken(registeredToken);
      setIsRegisteredUser(true);
      return true;
    }
    return false;
  };

  const logout = async () => {
    localStorage.removeItem('token');
    await initGuestSession();
  };

  return (
    <AuthContext.Provider value={{ token, loginWithGoogle, isRegisteredUser, logout}}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within AuthProvider');
  return context;
};