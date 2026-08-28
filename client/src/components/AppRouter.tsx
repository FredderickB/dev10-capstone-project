import { Navigate, createBrowserRouter, RouterProvider } from 'react-router-dom';
import Layout from './Layout'
import Landing from './page/Landing';
import GamePage from './page/GamePage';
import ProfilePage from './page/ProfilePage';
import NewGamePage from './page/NewGamePage';
import NotFound from './page/NotFound';
import LogoutPage from './page/LogoutPage';

export default function AppRouter() {

    const router = createBrowserRouter([
    {
      path: "",
      element: <Layout />,
      children: [
        {
          path: "/",
          element: <Landing />,
        },
        {
          path: "game/:gameId",
          element: <GamePage />,
        },
        {
          path: "newgame",
          element: <NewGamePage />,
        },
        {
          path: "profile",
          element: <ProfilePage />,
        },
        {
          path: "logout",
          element: <LogoutPage />,
        },
        {
          path: '*',
          element: <NotFound />,
        },
      ],
    },
  ]);

   return <RouterProvider router={router} />
}
