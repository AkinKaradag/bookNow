package Controller;

import bookNow.Controller.UserController;
import bookNow.Model.UserModel;
import bookNow.Requests.UserUpdate;
import bookNow.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        // Arrange: Erstelle ein Mock-UserModel
        UserModel user = new UserModel();
        user.setName("Test User");

        when(userService.createUser(any(UserModel.class))).thenReturn(user);

        // Act: Rufe die createUser-Methode auf
        UserModel result = userController.createUser(user);

        // Assert: Überprüfe, dass das Ergebnis nicht null ist und der Name stimmt
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        verify(userService, times(1)).createUser(user);
    }

    @Test
    void testGetAllUsers() {
        // Arrange: Setze ein Mock-Response für Benutzer
        List<UserModel> users = new ArrayList<>();
        users.add(new UserModel());

        when(userService.getAllUsers()).thenReturn(users);

        // Act: Rufe die getAllUsers-Methode auf
        List<UserModel> result = userController.getAllUsers();

        // Assert: Überprüfe, ob die Liste nicht leer ist
        assertFalse(result.isEmpty());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testFindByUserId_found() {
        // Arrange: Setze ein Mock-Response für einen Benutzer
        UserModel user = new UserModel();
        user.setId(1L);

        when(userService.findByUserId(1L)).thenReturn(user);

        // Act: Rufe die findByUserId-Methode auf
        UserModel result = userController.findByUserId(1L);

        // Assert: Überprüfe, ob das Ergebnis nicht null ist und die ID stimmt
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userService, times(1)).findByUserId(1L);
    }

    @Test
    void testUpdateUser_found() {
        // Arrange: Setze ein Mock-UserUpdate und ein Mock-UserModel
        UserUpdate updateRequest = new UserUpdate();
        updateRequest.setUserName("Updated User");

        UserModel user = new UserModel();
        user.setName("Updated User");

        when(userService.updateUser(1L, updateRequest)).thenReturn(user);

        // Act: Rufe die updateUser-Methode auf
        UserModel result = userController.updateUser(1L, updateRequest);

        // Assert: Überprüfe, dass das Ergebnis nicht null ist und der Name stimmt
        assertNotNull(result);
        assertEquals("Updated User", result.getName());
        verify(userService, times(1)).updateUser(1L, updateRequest);
    }

    @Test
    void testDeleteUser() {
        // Arrange: Definiere das Verhalten für userService.deleteById()
        doNothing().when(userService).deleteById(1L);

        // Act: Rufe die deleteUser-Methode auf
        userController.deleteUser(1L);

        // Assert: Verifiziere, dass deleteById mit der korrekten ID aufgerufen wurde
        verify(userService, times(1)).deleteById(1L);
    }
}

