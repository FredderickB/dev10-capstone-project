import React, { useState } from 'react'
import '../../styles/EngineSelector.css'

interface props {
  value: number;
  onChange: (engineLevel: number) => void;
}

export default function EngineSelector({ value, onChange }: props) {

  const min = 400;
  const max = 2500;
  const step = 100;

  const handleDecrement = () => {
    if (value > min) onChange(value - step);
  };

  const handleIncrement = () => {
    if (value < max) onChange(value + step);
  };

  return (
    <div className="d-flex flex-column align-items-center justify-content-center text-center my-4">
      <label className="fs-5 fw-semibold text-parchment tracking-wider mb-3 text-uppercase">
        Engine Strength
      </label>

      <div className="d-flex align-items-center border border-gold rounded-3 p-1 dojo-stepper-bg">
        <button
          type="button"
          className="btn dojo-stepper-btn px-3 py-2 text-gold fs-4"
          onClick={handleDecrement}
          disabled={value <= min}
          aria-label="Decrease engine strength"
        >
          -
        </button>

        <div className="px-4 py-2 min-w-120">
          <span className="fs-4 fw-bold text-parchment font-serif">
            {value} Elo
          </span>
        </div>

        <button
          type="button"
          className="btn dojo-stepper-btn px-3 py-2 text-gold fs-4"
          onClick={handleIncrement}
          disabled={value >= max}
          aria-label="Increase engine strength"
        >
          +
        </button>
      </div>
    </div>
  )
}
