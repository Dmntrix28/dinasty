import L from 'leaflet'
import { MapContainer, Marker, Popup, TileLayer } from 'react-leaflet'
import { Link } from 'react-router-dom'

const icon = new L.Icon({
  iconUrl: 'https://cdn-icons-png.flaticon.com/512/619/619153.png',
  iconSize: [34, 34]
})

const MapView = ({ properties }) => (
  <MapContainer center={[-16.5, -68.15]} zoom={12} className="map">
    <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" attribution='&copy; OpenStreetMap' />
    {properties.map((p) => (
      <Marker key={p.id} position={[p.lat, p.lng]} icon={icon}>
        <Popup>
          <strong>{p.titulo}</strong>
          <p>{p.zona}</p>
          <Link to={`/propiedad/${p.id}`}>Ver detalle</Link>
        </Popup>
      </Marker>
    ))}
  </MapContainer>
)

export default MapView
