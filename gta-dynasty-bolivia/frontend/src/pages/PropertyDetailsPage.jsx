import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import api from '../api/client'
import { useAuth } from '../context/AuthContext'

const PropertyDetailsPage = () => {
  const { id } = useParams()
  const { user } = useAuth()
  const navigate = useNavigate()
  const [property, setProperty] = useState(null)
  const [index, setIndex] = useState(0)
  const [dates, setDates] = useState({ startDate: '', endDate: '' })

  useEffect(() => {
    api.get(`/api/properties/${id}`).then(({ data }) => setProperty(data))
  }, [id])

  const buy = async () => {
    if (!user) return navigate('/login')
    await api.post(`/api/properties/${id}/buy`)
    alert('Compra simulada realizada')
    navigate('/perfil')
  }

  const reserve = async () => {
    if (!user) return navigate('/login')
    await api.post('/api/reservations', { propertyId: Number(id), ...dates })
    alert('Reserva confirmada')
    navigate('/mis-reservas')
  }

  if (!property) return <p>Cargando...</p>

  return (
    <section className="detail-page">
      <img src={property.imagenes?.[index]} className="hero-image" />
      <div className="thumb-row">
        {property.imagenes?.map((img, i) => <img key={img} src={img} onClick={() => setIndex(i)} />)}
      </div>
      <h2>{property.titulo}</h2>
      <p>{property.descripcion}</p>
      <p>{property.zona} · {property.ciudad}</p>
      <div className="actions">
        {property.precioVenta && <button className="neon-btn" onClick={buy}>Comprar</button>}
      </div>
      {property.precioNoche && (
        <div className="reservation-box">
          <input type="date" onChange={(e) => setDates({ ...dates, startDate: e.target.value })} />
          <input type="date" onChange={(e) => setDates({ ...dates, endDate: e.target.value })} />
          <button className="neon-btn" onClick={reserve}>Reservar</button>
        </div>
      )}
    </section>
  )
}

export default PropertyDetailsPage
