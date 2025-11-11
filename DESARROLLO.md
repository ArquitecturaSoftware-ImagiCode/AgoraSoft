# Modo Desarrollo - AgoraSoft

## 🚀 Configuración para Desarrollo con Hot Reload

### Backend (Spring Boot)

El backend ya tiene **Spring Boot DevTools** configurado para hot reload automático.

#### Ejecutar Backend:
```bash
cd backend
./mvnw spring-boot:run
```

**O desde tu IDE:**
- IntelliJ IDEA: Run `AgoraSoftApplication.java`
- VS Code: Run Spring Boot App

#### Características:
- ✅ Hot reload automático al guardar archivos
- ✅ Reinicio rápido del contexto Spring
- ✅ No necesitas reconstruir la aplicación

---

### Frontend (Angular)

El frontend tiene **Hot Module Reloading (HMR)** integrado.

#### Ejecutar Frontend:
```bash
cd frontend
npm install  # Solo la primera vez
npm run dev  # Con proxy configurado
```

**O sin proxy:**
```bash
npm start
```

#### Características:
- ✅ Hot reload automático al guardar archivos
- ✅ Proxy configurado para API (`/api` → `http://localhost:8085`)
- ✅ Recompilación automática

---

## 🧪 Probar el Sistema

### 1. Iniciar Backend
```bash
cd backend
./mvnw spring-boot:run
```
**Backend disponible en:** `http://localhost:8085`

### 2. Iniciar Frontend
```bash
cd frontend
npm run dev
```
**Frontend disponible en:** `http://localhost:4200`

### 3. Probar APIs

#### Crear productos de ejemplo:
```bash
Invoke-WebRequest -Uri "http://localhost:8085/api/productos/poblar-datos" -Method POST
```

#### Crear inventario para usuario:
```bash
Invoke-WebRequest -Uri "http://localhost:8085/api/inventarios" -Method POST -ContentType "application/json" -Body '{"usuarioId": 200, "items": []}'
```

#### Agregar producto al inventario:
```bash
Invoke-WebRequest -Uri "http://localhost:8085/api/items-inventario/agregar?inventarioId=7&productoId=1&cantidad=5" -Method POST
```

### 4. Ver en el Frontend
Navega a: `http://localhost:4200/operador/inventario`

---

## 📁 Estructura del Proyecto

```
AgoraSoft/
├── backend/                 # Spring Boot API
│   ├── src/main/java/      # Código Java
│   └── pom.xml             # Dependencias Maven
├── frontend/               # Angular App
│   ├── src/app/           # Código Angular
│   └── package.json       # Dependencias NPM
└── docker/                # Configuración Docker (para producción)
```

---

## 🔧 APIs Disponibles

### Productos
- `GET /api/productos` - Listar productos
- `POST /api/productos/poblar-datos` - Crear productos de ejemplo

### Inventarios
- `GET /api/inventarios/usuario/{usuarioId}` - Obtener inventario por usuario
- `POST /api/inventarios` - Crear inventario

### Items de Inventario
- `GET /api/items-inventario/inventario/{inventarioId}` - Listar items
- `POST /api/items-inventario/agregar` - Agregar producto
- `PUT /api/items-inventario/cantidad` - Actualizar cantidad
- `DELETE /api/items-inventario/{id}` - Eliminar item

---

## 💡 Tips de Desarrollo

1. **Backend**: Cada vez que guardes un archivo `.java`, Spring reinicia automáticamente
2. **Frontend**: Cada vez que guardes un archivo `.ts/.html/.css`, Angular recompila automáticamente
3. **API**: Usa Postman o CURL para probar las APIs directamente
4. **Base de datos**: Se reinicia automáticamente con cada cambio en el backend

---

## 🐛 Solución de Problemas

### Backend no inicia:
- Verifica que el puerto 8085 esté libre
- Revisa los logs en la consola

### Frontend no conecta con Backend:
- Verifica que el backend esté corriendo en puerto 8085
- Revisa el archivo `proxy.conf.json`

### Cambios no se reflejan:
- Reinicia el proceso correspondiente
- Verifica que no haya errores en la consola
