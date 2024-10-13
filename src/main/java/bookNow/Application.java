package bookNow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Die Hauptklasse der Spring Boot-Anwendung.
 *
 * Diese Klasse ist der Einstiegspunkt für die Anwendung und startet den integrierten Spring Boot-Server.
 * Die Annotation @SpringBootApplication aktiviert die automatische Konfiguration, Komponenten-Scan und die Unterstützung für Spring Boot-Anwendungen.
 */
@SpringBootApplication
public class Application {
    /**
     * Die main-Methode ist der Einstiegspunkt der Anwendung.
     *
     * @param args Kommandozeilenargumente, die beim Start der Anwendung übergeben werden.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    } // Startet die Spring Boot-Anwendung
}

