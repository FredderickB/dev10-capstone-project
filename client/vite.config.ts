import react, { reactCompilerPreset } from '@vitejs/plugin-react'
import babel from '@rolldown/plugin-babel'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
    babel({ presets: [reactCompilerPreset()] })
  ],
   server: {
    headers: {
      // 'Cross-Origin-Opener-Policy': 'same-origin-allow-popups',
      'Referrer-Policy': 'no-referrer-when-downgrade',
    },
  },
})
