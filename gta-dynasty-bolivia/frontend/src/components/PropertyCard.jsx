import { Link } from 'react-router-dom'

const PropertyCard = ({ property }) => (
  <article className="property-card">
    <img src={property.imagenes?.[0]} alt={property.titulo} />
    <div className="content">
      <h3>{property.titulo}</h3>
      <p>{property.zona}, {property.ciudad}</p>
      <p>{property.dormitorios} dorm · {property.banos} baños · {property.m2} m²</p>
      <strong>
        {property.precioVenta ? `$${property.precioVenta.toLocaleString()} venta` : ''}
        {property.precioNoche ? ` | $${property.precioNoche}/noche` : ''}
      </strong>
      <Link className="neon-btn" to={`/propiedad/${property.id}`}>Ver detalle</Link>
    </div>
  </article>
)

export default PropertyCard
