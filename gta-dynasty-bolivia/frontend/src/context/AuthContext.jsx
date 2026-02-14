import { createContext, useContext, useEffect, useState } from 'react'
import api from '../api/client'

const AuthContext = createContext(null)

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const init = async () => {
      const token = localStorage.getItem('token')
      if (token) {
        try {
          const { data } = await api.get('/api/users/me')
          setUser(data)
        } catch {
          localStorage.removeItem('token')
        }
      }
      setLoading(false)
    }
    init()
  }, [])

  const login = async (payload) => {
    const { data } = await api.post('/api/auth/login', payload)
    localStorage.setItem('token', data.token)
    setUser(data.user)
  }

  const register = async (payload) => {
    const { data } = await api.post('/api/auth/register', payload)
    localStorage.setItem('token', data.token)
    setUser(data.user)
  }

  const logout = () => {
    localStorage.removeItem('token')
    setUser(null)
  }

  return <AuthContext.Provider value={{ user, loading, login, register, logout }}>{children}</AuthContext.Provider>
}

export const useAuth = () => useContext(AuthContext)
