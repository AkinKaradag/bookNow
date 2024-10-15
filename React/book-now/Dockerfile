# Stage 1: Anwendung bauen
FROM node:20 AS build

WORKDIR /app

# Abhängigkeiten installieren
COPY package.json package-lock.json ./
RUN npm install

# Anwendungscode kopieren und bauen
COPY . .
RUN npm run build

# Stage 2: Anwendung mit Nginx bereitstellen
FROM nginx:latest

# Gebaute Anwendung kopieren
COPY --from=build /app/build /usr/share/nginx/html

# Nginx-Konfiguration kopieren
COPY default.conf /etc/nginx/conf.d/default.conf

# Port 80 freigeben
EXPOSE 80

# Nginx starten
CMD ["nginx", "-g", "daemon off;"]
