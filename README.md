# bookNow
Localhost: http://localhost:8080/swagger-ui/index.html

Um React zu nutzen:

# installs nvm (Node Version Manager)
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.0/install.sh | bash

# download and install Node.js (you may need to restart the terminal)
nvm install 20

# verifies the right Node.js version is in the environment
node -v # should print `v20.17.0`

# verifies the right npm version is in the environment
npm -v # should print `10.8.2`


Ich habe Spring Boot manuell über den Terminal installiert, jedoch gibt es eine Automatisierung über start.spring.io.

Die Standard-Dependencies sind:
- Spring Web
- Spring Data JPA
- Spring Boot DevTools
- Spring Security
- PostgreSQL Driver
- Lombok

Mind. diese werden gebraucht, um das Projekt zu starten. Weitere sind hinzugefügt worden, um die Funktionalität zu erweitern.


Start des React-Projekts:
npm start (im Verzeichnis des Projekts)

Es wird ein Versuch mit React gestartet, um auch React zu lernen.

Folgender Annotation wird verwendet, um die ID durch die Datenbank zu generieren:
@GeneratedValue(strategy = GenerationType.IDENTITY)

# **Anleitung zum Starten der Applikation**

Um die Applikation auszuführen, sind folgende Schritte zu befolgen:

**1. Repository klonen**

Das GitHub-Repository kann mit folgendem Befehl geklont werden:
_git clone <https://github.com/AkinKaradag/bookNow.git>_

**2. In das Projektverzeichnis wechseln**

Mit folgendem Befehl:
cd <projektverzeichnis>

**3. Docker installieren**

Falls Docker noch nicht installiert ist, kann es von der offiziellen Webseite heruntergeladen und installiert werden: https://www.docker.com/products/docker-desktop/

**4. Docker-Container starten**

Mit dem folgenden Befehl werden die Docker-Container gebaut und gestartet:
docker-compose up --build

**5. Applikation im Browser aufrufen**

Nach dem erfolgreichen Start der Container ist die Applikation im Browser unter http://localhost:3000 erreichbar.

**Hinweise:**

-Abhängigkeiten: Es wird vorausgesetzt, dass Docker und Docker Compose installiert sind.
-Konfiguration: Bei Bedarf können Konfigurationsdateien angepasst werden, um spezielle Einstellungen vorzunehmen.
-Datenbankzugriff: Die Applikation verwendet eine PostgreSQL-Datenbank, die in einem Docker-Container läuft und automatisch gestartet wird.

**Zusätzliche Befehle:**

Docker-Container stoppen:
docker-compose down

Logs der Container anzeigen:
docker-compose logs

**Troubleshooting:**

-Bei Problemen mit Port-Konflikten sicherstellen, dass die Ports 3000 (Frontend), 8080 (Backend) und 5432 (Datenbank) nicht von anderen Anwendungen belegt sind.
-Überprüfen, ob alle notwendigen Umgebungsvariablen korrekt gesetzt sind.