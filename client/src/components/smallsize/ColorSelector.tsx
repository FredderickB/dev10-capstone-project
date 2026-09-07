import React from 'react';
import '../../styles/ColorSelector.css'

interface ColorSelectorProps {
  value: string;
  onChange: (color: string) => void;
}

export default function ColorSelector({ value, onChange }: ColorSelectorProps) {
  const options = [
    { label: 'White', value: 'white', icon: '♔' },
    { label: 'Random', value: 'random', icon: '☯' },
    { label: 'Black', value: 'black', icon: '♚' },
  ];

  return (
    <div className="d-flex flex-column align-items-center justify-content-center text-center my-4">
      <label className="fs-5 fw-semibold text-parchment tracking-wider mb-3 text-uppercase">
        Choose Side
      </label>

      <div className="btn-group dojo-toggle-group p-1 border border-gold rounded-3" role="group">
        {options.map((opt) => {
          const isActive = value.toLowerCase() === opt.value;
          return (
            <button
              key={opt.value}
              type="button"
              className={`btn dojo-toggle-btn px-4 py-2 text-uppercase fw-semibold tracking-wider ${
                isActive ? 'active' : ''
              }`}
              onClick={() => onChange(opt.value)}
            >
              <span className="me-2 fs-5">{opt.icon}</span>
              {opt.label}
            </button>
          );
        })}
      </div>
    </div>
  );
}