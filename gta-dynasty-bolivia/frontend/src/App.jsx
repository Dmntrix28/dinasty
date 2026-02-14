import { Navigate, Route, Routes } from 'react-router-dom'
import NavbarHUD from './components/NavbarHUD'
import ProtectedRoute from './components/ProtectedRoute'
import CreateEditPropertyPage from './pages/CreateEditPropertyPage'
import HomePage from './pages/HomePage'
import LoginPage from './pages/LoginPage'
import ProfilePage from './pages/ProfilePage'
import PropertyDetailsPage from './pages/PropertyDetailsPage'
import RegisterPage from './pages/RegisterPage'
import ReservationsPage from './pages/ReservationsPage'

function App() {
  return (
    <div className="app-shell">
      <NavbarHUD />
      <main>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/comprar" element={<HomePage mode="VENTA" />} />
          <Route path="/alquilar" element={<HomePage mode="ALQUILER" />} />
          <Route path="/propiedad/:id" element={<PropertyDetailsPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/publicar" element={<ProtectedRoute><CreateEditPropertyPage /></ProtectedRoute>} />
          <Route path="/mis-reservas" element={<ProtectedRoute><ReservationsPage /></ProtectedRoute>} />
          <Route path="/perfil" element={<ProtectedRoute><ProfilePage /></ProtectedRoute>} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </main>
    </div>
  )
}

export default App
