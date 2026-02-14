const FiltersPanel = ({ filters, setFilters, onSearch }) => {
  const update = (e) => setFilters({ ...filters, [e.target.name]: e.target.value })

  return (
    <div className="filters-panel">
      <input name="q" placeholder="Buscar título/desc" value={filters.q} onChange={update} />
      <select name="modalidad" value={filters.modalidad} onChange={update}>
        <option value="">Modalidad</option>
        <option value="VENTA">Venta</option>
        <option value="ALQUILER">Alquiler</option>
      </select>
      <select name="tipo" value={filters.tipo} onChange={update}>
        <option value="">Tipo</option>
        <option value="CASA">Casa</option>
        <option value="DEPTO">Depto</option>
        <option value="TERRENO">Terreno</option>
        <option value="LOCAL">Local</option>
      </select>
      <input name="zona" placeholder="Zona" value={filters.zona} onChange={update} />
      <input name="minPrice" type="number" placeholder="Precio min" value={filters.minPrice} onChange={update} />
      <input name="maxPrice" type="number" placeholder="Precio max" value={filters.maxPrice} onChange={update} />
      <select name="sort" value={filters.sort} onChange={update}>
        <option value="newest">Más nuevas</option>
        <option value="price_asc">Precio asc</option>
        <option value="price_desc">Precio desc</option>
      </select>
      <button className="neon-btn" onClick={onSearch}>Aplicar</button>
    </div>
  )
}

export default FiltersPanel
