import React from 'react'
import { GoogleLoginButton } from '../smallsize/GoogleLoginButton'
import { useAuth } from '../../contexts/AuthContext'
import { Link } from 'react-router-dom'

export default function Landing() {

    const { isRegisteredUser } = useAuth()
    return (
        <>
            <div className='container d-flex flex-column align-items-center justify-content-center text-center py-5 min-vh-80'>
                <h1 className='display-6 fw-bold text-parchment tracking-wider mb-3'> Practice BlindFold Chess </h1>
                <div className='d-flex flex-column align-items-center gap-3 w-100 max-w-xs'>
                    <Link to='newgame' className='btn btn-dojo-gold btn-lg w-100 text-uppercase shadow-sm tracking-wider'>
                        Start a new match
                    </Link>
                    {isRegisteredUser ? null :
                        <GoogleLoginButton />
                    }
                </div>
            </div>
        </>
    )
}
