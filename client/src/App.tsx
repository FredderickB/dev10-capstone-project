import { AuthProvider } from './components/AuthContext'
import AppRouter from './components/AppRouter'


function App() {

  return (
    <>
    <AuthProvider>
      <AppRouter />
    </AuthProvider>
    </>
  )
}

export default App
