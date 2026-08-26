import { Navigate, createBrowserRouter, RouterProvider } from 'react-router-dom';
import Layout from './Layout'
import Landing from './Landing';
import GamePage from './GamePage';
import ProfilePage from './ProfilePage';
import NewGamePage from './NewGamePage';
import NotFound from './NotFound';

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
          path: "game",
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
          path: '*',
          element: <NotFound />,
        },
      ],
    },
  ]);

   return <RouterProvider router={router} />
}
