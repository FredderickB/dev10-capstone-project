import React, { useState } from 'react'
import { useProfileState } from '../../hooks/useProfileState';
import { useAuth } from '../../contexts/AuthContext';

interface props {
    gameId: number
    ConfirmDeleteClick: (gameId: number) => void
}

export default function DeleteButton({ gameId, ConfirmDeleteClick }: props) {

    const [confirm, setConfirm] = useState<boolean>(false);

    function handleDeleteClick() {

        const currView = confirm;
        setConfirm(!currView);

    }

    return (
        <div className="d-inline-flex justify-content-end align-items-center gap-1 w-100">
            {confirm ? (
                <>
                    <button
                        type="button"
                        onClick={() => ConfirmDeleteClick(gameId)}
                        className="btn btn-danger btn-sm text-uppercase fw-bold fs-7 tracking-wider px-2 py-1 text-nowrap"
                    >
                        Confirm
                    </button>
                    <button
                        type="button"
                        onClick={handleDeleteClick}
                        className="btn btn-outline-secondary btn-sm text-uppercase fw-bold fs-7 tracking-wider px-2 py-1 text-nowrap"
                    >
                        No
                    </button>
                </>
            ) : (
                <button
                    type="button"
                    onClick={handleDeleteClick}
                    className="btn btn-outline-danger btn-sm text-uppercase fw-bold fs-7 tracking-wider px-2 py-1 text-nowrap"
                >
                    Delete
                </button>
            )}
        </div>
    );
}
