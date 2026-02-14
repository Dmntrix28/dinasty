import { useEffect, useState } from 'react'
import api from '../api/client'
import FiltersPanel from '../components/FiltersPanel'
import MapView from '../components/MapView'
import PropertyCard from '../components/PropertyCard'

const HomePage = ({ mode }) => {
  const [properties, setProperties] = useState([])
  const [filters, setFilters] = useState({ q: '', modalidad: mode || '', tipo: '', zona: '', minPrice: '', maxPrice: '', sort: 'newest' })

  const load = async () => {
    const { data } = await api.get('/api/properties/search', { params: filters })
    setProperties(data)
  }

  useEffect(() => { load() }, [mode])

  return (
    <div>
      <section className="hero">
        <h1>DYNASTY LA PAZ</h1>
        <p>Marketplace inmobiliario con estética GTA en Bolivia.</p>
      </section>
      <FiltersPanel filters={filters} setFilters={setFilters} onSearch={load} />
      <section className="split-view">
        <div className="list-grid">
          {properties.map((p) => <PropertyCard key={p.id} property={p} />)}
        </div>
        <MapView properties={properties} />
      </section>
    </div>
  )
}

export default HomePage
