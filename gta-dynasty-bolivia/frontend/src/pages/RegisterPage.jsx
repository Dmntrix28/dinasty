import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const RegisterPage = () => {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const { register } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      await register({ name, email, password })
      navigate('/perfil')
    } catch (err) {
      setError(err?.response?.data?.error || 'Error de registro')
    }
  }

  return (
    <form className="auth-card" onSubmit={handleSubmit}>
      <h2>Registro</h2>
      <input placeholder="Nombre" value={name} onChange={(e) => setName(e.target.value)} required />
      <input placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
      <input placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
      {error && <p className="error">{error}</p>}
      <button className="neon-btn" type="submit">Crear cuenta</button>
    </form>
  )
}

export default RegisterPage
