import { Link, NavLink } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

const NavbarHUD = () => {
  const { user, logout } = useAuth()

  return (
    <header className="hud-nav">
      <Link to="/" className="logo">DYNASTY BOLIVIA</Link>
      <nav>
        <NavLink to="/">Home</NavLink>
        <NavLink to="/comprar">Comprar</NavLink>
        <NavLink to="/alquilar">Alquilar</NavLink>
        <NavLink to="/publicar">Publicar inmueble</NavLink>
        <NavLink to="/mis-reservas">Mis reservas</NavLink>
        <NavLink to="/perfil">Perfil</NavLink>
      </nav>
      <div>
        {user ? (
          <button className="neon-btn" onClick={logout}>Logout</button>
        ) : (
          <Link className="neon-btn" to="/login">Login</Link>
        )}
      </div>
    </header>
  )
}

export default NavbarHUD
