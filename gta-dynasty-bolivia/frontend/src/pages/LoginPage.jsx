import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const LoginPage = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const { login } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    try {
      await login({ email, password })
      navigate('/perfil')
    } catch (err) {
      setError(err?.response?.data?.error || 'Error de login')
    }
  }

  return (
    <form className="auth-card" onSubmit={handleSubmit}>
      <h2>Login</h2>
      <input placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
      <input placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
      {error && <p className="error">{error}</p>}
      <button className="neon-btn" type="submit">Entrar</button>
    </form>
  )
}

export default LoginPage
