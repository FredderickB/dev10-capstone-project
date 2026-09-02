import React, { useState } from 'react'
import { useProfileState } from '../hooks/useProfileState';
import { useAuth } from '../contexts/AuthContext';

interface props {
    gameId: number
    ConfirmDeleteClick: (gameId: number) => void
}

export default function DeleteButton({gameId, ConfirmDeleteClick}: props) {

    const [confirm, setConfirm] = useState<boolean>(false);

    function handleDeleteClick() {

        const currView = confirm;
        setConfirm(!currView);

    }

    return (
        <>
            {confirm ?
                <>
                    <button onClick={() => ConfirmDeleteClick(gameId)}>confirm</button><button onClick={handleDeleteClick}>no</button>
                </>:
                <button onClick={handleDeleteClick}>Delete</button>
            }
        </>
    )
}
