import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import api from '../api/client'

const empty = {
  titulo: '', descripcion: '', tipo: 'CASA', modalidad: 'VENTA', precioVenta: '', precioNoche: '', ciudad: 'La Paz', zona: '', lat: -16.5, lng: -68.15,
  dormitorios: 2, banos: 1, m2: 80, imagenes: 'https://picsum.photos/seed/new/900/500', disponible: true
}

const CreateEditPropertyPage = () => {
  const [form, setForm] = useState(empty)
  const navigate = useNavigate()

  const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value })

  const submit = async (e) => {
    e.preventDefault()
    const payload = { ...form, precioVenta: form.precioVenta ? Number(form.precioVenta) : null, precioNoche: form.precioNoche ? Number(form.precioNoche) : null, imagenes: form.imagenes.split(',').map(v => v.trim()) }
    await api.post('/api/properties', payload)
    navigate('/perfil')
  }

  return (
    <form className="auth-card" onSubmit={submit}>
      <h2>Publicar inmueble</h2>
      {Object.keys(empty).map((k) => k !== 'disponible' && <input key={k} name={k} value={form[k]} onChange={onChange} placeholder={k} required={['titulo','descripcion','zona'].includes(k)} />)}
      <button className="neon-btn" type="submit">Publicar</button>
    </form>
  )
}

export default CreateEditPropertyPage
