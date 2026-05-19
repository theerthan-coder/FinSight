import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/employee': 'http://localhost:8081',
      '/users': 'http://localhost:8081',
      '/transaction': 'http://localhost:8081',
      '/function': 'http://localhost:8081'
    }
  }
})
