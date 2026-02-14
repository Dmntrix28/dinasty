import { useEffect, useState } from 'react'
import api from '../api/client'

const ReservationsPage = () => {
  const [items, setItems] = useState([])

  useEffect(() => {
    api.get('/api/reservations/me').then(({ data }) => setItems(data))
  }, [])

  return (
    <section>
      <h2>Mis reservas</h2>
      <div className="list-grid">
        {items.map(r => (
          <article key={r.id} className="property-card">
            <div className="content">
              <h3>{r.property?.titulo}</h3>
              <p>{r.startDate} - {r.endDate}</p>
              <strong>Total: ${r.total}</strong>
            </div>
          </article>
        ))}
      </div>
    </section>
  )
}

export default ReservationsPage
