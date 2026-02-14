import { useEffect, useState } from 'react'
import api from '../api/client'

const ProfilePage = () => {
  const [profile, setProfile] = useState(null)

  useEffect(() => {
    api.get('/api/profile').then(({ data }) => setProfile(data))
  }, [])

  if (!profile) return <p>Cargando perfil...</p>

  return (
    <section>
      <h2>Perfil: {profile.user.name}</h2>
      <p>{profile.user.email}</p>
      <h3>Mis publicaciones ({profile.myPublications.length})</h3>
      <ul>{profile.myPublications.map(p => <li key={p.id}>{p.titulo}</li>)}</ul>
      <h3>Mis compras ({profile.myTransactions.length})</h3>
      <ul>{profile.myTransactions.map(t => <li key={t.id}>{t.property.titulo} - ${t.precio}</li>)}</ul>
      <h3>Mis reservas ({profile.myReservations.length})</h3>
      <ul>{profile.myReservations.map(r => <li key={r.id}>{r.property.titulo} - {r.startDate}</li>)}</ul>
    </section>
  )
}

export default ProfilePage
