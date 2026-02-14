const properties = [
  { id: 1, title: 'Penthouse Neón Sopocachi', zone: 'Sopocachi', type: 'Depto', mode: 'VENTA', price: 145000, lat: -16.511, lng: -68.131, img: 'https://picsum.photos/seed/lpz1/400/240' },
  { id: 2, title: 'Casa Premium Calacoto', zone: 'Calacoto', type: 'Casa', mode: 'VENTA', price: 220000, lat: -16.539, lng: -68.088, img: 'https://picsum.photos/seed/lpz2/400/240' },
  { id: 3, title: 'Loft Urbano San Miguel', zone: 'San Miguel', type: 'Depto', mode: 'ALQUILER', price: 65, lat: -16.541, lng: -68.079, img: 'https://picsum.photos/seed/lpz3/400/240' },
  { id: 4, title: 'Local Comercial Miraflores', zone: 'Miraflores', type: 'Local', mode: 'VENTA', price: 120000, lat: -16.498, lng: -68.119, img: 'https://picsum.photos/seed/lpz4/400/240' },
  { id: 5, title: 'Suite Ejecutiva Obrajes', zone: 'Obrajes', type: 'Depto', mode: 'ALQUILER', price: 48, lat: -16.531, lng: -68.111, img: 'https://picsum.photos/seed/lpz5/400/240' },
  { id: 6, title: 'Terreno Vista Valle Irpavi', zone: 'Irpavi', type: 'Terreno', mode: 'VENTA', price: 98000, lat: -16.523, lng: -68.084, img: 'https://picsum.photos/seed/lpz6/400/240' },
  { id: 7, title: 'Residencia Achumani Elite', zone: 'Achumani', type: 'Casa', mode: 'VENTA', price: 255000, lat: -16.557, lng: -68.087, img: 'https://picsum.photos/seed/lpz7/400/240' },
  { id: 8, title: 'Studio Dinasty Centro', zone: 'Centro', type: 'Depto', mode: 'ALQUILER', price: 39, lat: -16.496, lng: -68.133, img: 'https://picsum.photos/seed/lpz8/400/240' }
]

const list = document.getElementById('list')
const search = document.getElementById('search')
const modeFilter = document.getElementById('modeFilter')
const typeFilter = document.getElementById('typeFilter')
const sortFilter = document.getElementById('sortFilter')

const map = L.map('map', { zoomControl: true }).setView([-16.5, -68.15], 12)
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  attribution: '&copy; OpenStreetMap contributors'
}).addTo(map)

const icon = L.icon({ iconUrl: 'https://cdn-icons-png.flaticon.com/512/684/684908.png', iconSize: [28, 28] })
let markers = []

function formatPrice(p) {
  return p.mode === 'ALQUILER' ? `$${p.price}/noche` : `$${p.price.toLocaleString()}`
}

function filteredData() {
  const q = search.value.toLowerCase().trim()
  let data = properties.filter(p =>
    (modeFilter.value === 'ALL' || p.mode === modeFilter.value) &&
    (typeFilter.value === 'ALL' || p.type === typeFilter.value) &&
    (`${p.title} ${p.zone}`.toLowerCase().includes(q))
  )
  if (sortFilter.value === 'asc') data.sort((a,b) => a.price - b.price)
  if (sortFilter.value === 'desc') data.sort((a,b) => b.price - a.price)
  return data
}

function render() {
  const data = filteredData()
  list.innerHTML = data.map(p => `
    <article class="card">
      <img src="${p.img}" alt="${p.title}" />
      <div class="meta">
        <h3>${p.title}</h3>
        <div class="mode">${p.mode} · ${p.type} · ${p.zone}</div>
        <div class="price">${formatPrice(p)}</div>
      </div>
    </article>
  `).join('')

  markers.forEach(m => map.removeLayer(m))
  markers = data.map(p => L.marker([p.lat, p.lng], { icon })
    .bindPopup(`<strong>${p.title}</strong><br>${p.zone}<br><span style="color:#1ea75a">${formatPrice(p)}</span>`)
    .addTo(map))
}

;[search, modeFilter, typeFilter, sortFilter].forEach(el => el.addEventListener('input', render))
render()
