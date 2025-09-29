# ---------- Etapa de build Angular ----------
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
# SSR + prerender (según lo que tienes). Si más tarde usas build:docker, cambia el comando.
RUN npm run build -- --configuration production

# ---------- Etapa runtime: Nginx sirve estáticos prerender (browser bundle) ----------
FROM nginx:1.27-alpine
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/app.conf
COPY --from=build /app/dist/frontend/browser/ /usr/share/nginx/html/
EXPOSE 80
