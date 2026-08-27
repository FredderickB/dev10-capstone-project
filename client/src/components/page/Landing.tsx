import React from 'react'
import { GoogleLoginButton } from '../GoogleLoginButton'
import { useAuth } from '../AuthContext'
import { Link } from 'react-router-dom'

export default function Landing() {

    const { token } = useAuth()
    return (
        <>
            <Link to='newgame'> Start a new match </Link>
            {
                token ? 
                null :
                <GoogleLoginButton />
            }
        </>
    )
}
