import React from 'react'

interface ColorSelectorProps {
  value: string;
  onChange: (color: string) => void;
}

export default function ColorSelector({value, onChange}: ColorSelectorProps) {
  return (

    <>
        <label htmlFor='color-select'> Select your piece color</label>
        <select value={value} name='color-select' onChange={(e) => onChange(e.target.value)}>
            <option> White </option>
            <option> Black </option>
            <option> Random </option>
        </select>
    </>
  )
}
