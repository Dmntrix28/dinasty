# GTA Dynasty Bolivia (La Paz Edition)

Marketplace inmobiliario full-stack inspirado visualmente en Dynasty (GTA style), ambientado en La Paz, Bolivia. Incluye compra/venta, alquiler temporal tipo AirBnB, autenticación JWT, mapa interactivo con Leaflet y dashboard de perfil.

## Estructura

```
/gta-dynasty-bolivia/
  /frontend  (React + Vite)
  /backend   (Spring Boot + H2/JWT)
  README.md
```

## Demo users (seed)
- Owner: `owner@dynasty.bo` / `123456`
- User: `user@dynasty.bo` / `123456`

## Requisitos
- Node.js 18+
- Java 17+
- Maven 3.9+

## Backend (Spring Boot)
```bash
cd backend
mvn spring-boot:run
```
API base: `http://localhost:8080`

### Base de datos
- Por defecto usa H2 en memoria.
- Consola H2: `http://localhost:8080/h2-console`
- JDBC URL default: `jdbc:h2:mem:dynastydb`

### PostgreSQL opcional
Configura variables:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

## Frontend (React)
```bash
cd frontend
npm install
npm run dev
```
Frontend local: `http://localhost:5173`

Variables de entorno:
- `VITE_API_URL` (default `http://localhost:8080`)

## Deploy
### Frontend en GitHub Pages
El proyecto usa `HashRouter` + `vite build` con `base: './'` para SPA compatible con Pages.

```bash
cd frontend
npm run build
npm run deploy
```

También puedes automatizar con GitHub Actions (archivo sugerido en `.github/workflows/pages.yml`).

### Backend
GitHub Pages **no ejecuta Java**. Opciones:
- Render (free tier)
- Railway
- Backend local + frontend pages

## Endpoints principales
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/users/me`
- CRUD ` /api/properties`
- `GET /api/properties/search`
- `POST /api/properties/{id}/buy`
- `POST /api/reservations`
- `GET /api/reservations/me`
- `GET /api/owner/reservations`
- `GET /api/profile`

## Capturas sugeridas
1. Home con split list + mapa La Paz.
2. Detalle propiedad con galería.
3. Perfil con publicaciones / compras / reservas.

> Imágenes y datos son placeholders/dummy para demo.
