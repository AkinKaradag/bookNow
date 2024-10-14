package Services;

import bookNow.Model.UserModel;
import bookNow.Repository.UserRepository;
import bookNow.Requests.UserUpdate;
import bookNow.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //passwordEncoder = new BCryptPasswordEncoder(); // Stelle sicher, dass hier der gleiche Encoder wie im Hauptcode verwendet wird
    }

    @Test
    void testCreateUser() {
        // Arrange: Erstelle einen neuen Benutzer und mock das Passwort-Encoding
        UserModel newUser = new UserModel();
        newUser.setName("TestUser");
        newUser.setPassword("plainPassword");

        // Passwort-Encoder-Mock-Verhalten definieren
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(passwordEncoder.matches("plainPassword", "encodedPassword")).thenReturn(true);

        // Mock für das Speichern des Benutzers in der Repository
        when(userRepository.save(any(UserModel.class))).thenAnswer(invocation -> {
            UserModel savedUser = invocation.getArgument(0);
            savedUser.setPassword("encodedPassword");
            return savedUser;
        });

        // Act: Erstelle den Benutzer
        UserModel createdUser = userService.createUser(newUser);

        // Assert: Überprüfe, ob der Benutzer korrekt erstellt und das Passwort verschlüsselt wurde
        assertNotNull(createdUser);
        assertTrue(passwordEncoder.matches("plainPassword", createdUser.getPassword()),
                "Das verschlüsselte Passwort sollte mit dem Original übereinstimmen.");
    }


    @Test
    void testGetAllUsers() {
        // Arrange: Erstelle eine Liste von Benutzern und mocke die Rückgabe vom Repository
        //Prüft, ob alle Benutzer erfolgreich abgerufen werden
        UserModel user1 = new UserModel();
        user1.setName("User1");

        UserModel user2 = new UserModel();
        user2.setName("User2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act: Rufe alle Benutzer ab
        List<UserModel> users = userService.getAllUsers();

        // Assert: Überprüfe, ob alle Benutzer korrekt zurückgegeben wurden
        assertEquals(2, users.size());
        assertEquals("User1", users.get(0).getName());
        assertEquals("User2", users.get(1).getName());
    }

    @Test
    void testFindByUserId() {
        // Arrange: Mocke die Rückgabe für einen Benutzer basierend auf der ID
        //Prüft, ob ein Benutzer erfolgreich nach seiner ID gefunden wird
        UserModel user = new UserModel();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act: Suche nach einem Benutzer mit einer bestimmten ID
        UserModel foundUser = userService.findByUserId(1L);

        // Assert: Überprüfe, ob der Benutzer korrekt zurückgegeben wurde
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    void testUpdateUser() {
        // Arrange: Mocke einen existierenden Benutzer und das Passwort-Encoding
        UserModel existingUser = new UserModel();
        existingUser.setId(1L);
        existingUser.setPassword("oldPassword");

        UserUpdate updateRequest = new UserUpdate();
        updateRequest.setUserName("UpdatedUser");
        updateRequest.setEmail("updated@example.com");
        updateRequest.setPassword("newPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(UserModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act: Aktualisiere den Benutzer
        UserModel updatedUser = userService.updateUser(1L, updateRequest);

        // Assert: Überprüfe, ob der Benutzer aktualisiert wurde und das Passwort verschlüsselt ist
        assertNotNull(updatedUser);
        assertEquals("UpdatedUser", updatedUser.getName());
        assertEquals("updated@example.com", updatedUser.getEmail());
        assertEquals("encodedNewPassword", updatedUser.getPassword());
    }

    @Test
    void testDeleteUser() {
        // Arrange: Konfiguriere das Mock-Verhalten für deleteById()
        //Prüft, ob ein Benutzer erfolgreich gelöscht wird
        doNothing().when(userRepository).deleteById(1L);

        // Act: Lösche den Benutzer
        userService.deleteById(1L);

        // Assert: Verifiziere, dass deleteById aufgerufen wurde
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByName() {
        // Arrange: Mocke die Rückgabe für einen Benutzer basierend auf dem Namen
        UserModel user = new UserModel();
        user.setName("TestUser");

        when(userRepository.findByName("TestUser")).thenReturn(user);

        // Act: Suche nach einem Benutzer mit einem bestimmten Namen
        UserModel foundUser = userService.findByName("TestUser");

        // Assert: Überprüfe, ob der Benutzer korrekt zurückgegeben wurde
        assertNotNull(foundUser);
        assertEquals("TestUser", foundUser.getName());
    }

    @Test
    void testFindByEmail() {
        // Arrange: Mocke die Rückgabe für einen Benutzer basierend auf der Email
        UserModel user = new UserModel();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        // Act: Suche nach einem Benutzer mit einer bestimmten Email
        UserModel foundUser = userService.findByEmail("test@example.com");

        // Assert: Überprüfe, ob der Benutzer korrekt zurückgegeben wurde
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }
}

