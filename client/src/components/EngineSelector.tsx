import React, { useState } from 'react'

interface props {
  value : number;
  onChange: (engineLevel: number) => void;
}

export default function EngineSelector({ value, onChange}: props) {

  
  const max = 2500;
  const step = 100;

  const options = Array.from({
    length: max / step + 1}, (_, i) => i * step
  );

  return (
    <>
      <div >
      <label htmlFor="number-select">Select engine strength</label>
      <select
        id="number-select"
        value={value}
        onChange={(e) => onChange( Number (e.target.value))}
      >
        {options.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
    </div>
    </>
  )
}
