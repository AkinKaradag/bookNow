package Response;

import bookNow.Model.UserModel;
import bookNow.Response.UserResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testklasse für die UserResponse.
 */
public class UserResponseTest {

    @Test
    public void testUserResponseInitialization() {
        // Arrange: Erstellen eines Mock UserModel-Objekts mit Beispieldaten
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("Test User");
        userModel.setEmail("testuser@example.com");
        userModel.setPassword("testPassword");

        // Act: Initialisierung einer UserResponse basierend auf dem Mock-Objekt
        UserResponse userResponse = new UserResponse(userModel);

        // Assert: Überprüfen, ob alle Felder korrekt initialisiert wurden
        assertEquals(1L, userResponse.getUserId());
        assertEquals("Test User", userResponse.getUserName());
        assertEquals("wrongtestuser@example.com", userResponse.getEmail());
        assertEquals("testPassword", userResponse.getPassword());
    }
}
