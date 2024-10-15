Bevor ich mit dem ganzen Anfange, muss ich sagen, dass ich sehr frustiert bin und daran zweifle wirklich ein Softwareentwickler zu werden.
Meine Applikation funktionierte einwandfrei, bis ich Docker eingerichtet habe.
Am letzten Tag ist mein ganzes Projekt abgestürzt und ich konnte es nicht mehr zum Laufen bringen.

Durch die Error-Meldungen die ich zum letzten Zeitpunkt bekommen habe, konnte ich nicht mehr herausfinden, was das genau das Problem war.
Dadurch ist auch mein "Clean-Code" verloren gegangen, da ich versucht habe, das Problem zu lösen.

Im Moment läuft sie wieder, jedoch sehr beschränkt. Für die Benotung dieser Applikation kann ich es nicht mehr retten,
jedoch werde ich es für die Präsentation wieder zum Laufen bringen.

Leider funktioniert jetzt Docker nicht.

Um die Applikation zu starten, müssen verschiedene Komponenten installiert und eingerichtet werden. Hier sind die notwendigen Schritte für die Installation und Ausführung einer Applikation, die ein Backend mit Java, Hibernate, PostgreSQL, Maven und ein Frontend mit React und npm umfasst:

1. Voraussetzungen installieren
   Java: Es wird mindestens Java 11 oder höher benötigt. Die Installation erfolgt über die Java-Website oder ein Paketmanagement-Tool.
   Maven: Maven kann von der offiziellen Website heruntergeladen und installiert werden. Nach der Installation sollte der Befehl mvn -v in der Konsole eine Versionsnummer anzeigen.
   PostgreSQL: Die neueste Version kann von der offiziellen PostgreSQL-Website heruntergeladen werden. Nach der Installation sollte die Datenbank mit den benötigten Zugangsdaten konfiguriert werden.
   Node.js und npm: Diese werden für das React-Frontend benötigt. Die offizielle Website bietet eine Installationsoption für beide.
2. Backend einrichten
   Hibernate und Java-Umgebung einrichten:
   Sicherstellen, dass die Applikation eine Konfigurationsdatei application.properties enthält, welche die Datenbankverbindung und Hibernate-Einstellungen definiert.
   Eine PostgreSQL-Datenbank erstellen und Zugangsdaten in der application.properties konfigurieren.
   Mit Maven installieren:
   Im Hauptverzeichnis des Backends (dort, wo die pom.xml-Datei liegt), den Befehl mvn clean install ausführen, um alle Abhängigkeiten herunterzuladen und das Backend zu kompilieren.
   Backend starten:
   Mit dem Befehl mvn spring-boot:run wird das Backend gestartet. Wenn die Konfiguration korrekt ist, läuft das Backend nun auf http://localhost:8080.
3. Frontend einrichten
   Node-Module installieren:
   Im Verzeichnis des Frontends (dort, wo sich die package.json-Datei befindet) den Befehl npm install ausführen, um alle Abhängigkeiten zu installieren.
   Frontend starten:
   Mit npm start wird das Frontend gestartet. Es ist nun über http://localhost:3000 erreichbar.
4. Anwendung verwenden
   Sicherstellen, dass das Backend bereits läuft, bevor das Frontend gestartet wird.
   Über den Browser auf http://localhost:3000 navigieren und die Applikation benutzen.
   Diese Schritte ermöglichen den erfolgreichen Start und die Nutzung der Applikation.

In der Applikation habe ich mir überlegt, eine solide Basis aufzubauen, um weitere Funktionen hinzufügen zu können.
Die Endnutzer können sich als PRIVATEUSER registrieren und anmelden, um danach Termine zu buchen. 
Die COMPANYUSER können sich registrieren und anmelden, um ihre Dienstleistungen anzubieten und Termine zu verwalten.

Jedoch funktioniert leider die Registrierung von CompanyUsern nicht mehr, da ich das Problem mit Docker nicht lösen konnte.

In der Datenbank ist jedoch ersichtlich, dass ich User hatte. 

In der Datenbank wurden leider mehrere Spalten hinzugefügt während der Problemlösung. Aufgrund Zeitmangel, konnte ich diese jedoch nicht mehr entfernen.

Wenn ich mehr Zeit hätte, würde ich die Applikation komplett neu aufsetzen und die Fehler beheben.

Ich hätte auch gerne die Möglichkeit gehabt, die Applikation mit Docker zu starten, jedoch konnte ich das Problem nicht lösen.

Die Funktionalitäten können ausgeweitet werden, um die Benutzerfreundlichkeit zu verbessern und die Anwendung nützlicher zu machen.

Mit dieser Frust und Enttäuschung, habe ich jedoch viel gelernt und werde in Zukunft besser darauf achten, dass ich meine Applikationen besser dokumentiere und regelmässig sichere.

Leider konnte ich auch nicht in einen früheren Stand zurückkehren, aufgrund Zeitdruck habe ich mich entschieden, die Applikation so abzugeben.

Nach grosser Freude, als die Applikation funktionierte und sogar auch mit Docker, kam dann am Abend die Enttäuschung.

Ich Entschuldige mich für solch eine schlechte Abgabe und hoffe, dass ich in der Präsentation die Applikation wieder zum Laufen bringen kann.

