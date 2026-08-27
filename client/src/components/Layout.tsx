import { NavLink, Outlet } from 'react-router-dom'
import { useAuth } from './AuthContext'

export default function Layout() {

    const { token } = useAuth();

    return (
        <div>

            <NavLink to='/'>
                <h1>Blind Chess</h1>
            </NavLink>
            <ul>
                {token ?
                    <li>
                        <NavLink to='profile'>
                            Profile
                        </NavLink>
                    </li> :
                    null}
            </ul>
            <Outlet />
        </div>
    )
}
