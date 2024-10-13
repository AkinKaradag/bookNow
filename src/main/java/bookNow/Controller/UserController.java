package bookNow.Controller;

import bookNow.Model.UserModel;
import bookNow.Requests.UserUpdate;
import bookNow.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller für die Verwaltung der Benutzerentitäten.
 */
@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * Erstellt einen neuen Benutzer.
     * @param newUser Das Benutzerobjekt mit den Benutzerdetails.
     * @return Das erstellte Benutzermodell.
     */
    @PostMapping
    public UserModel createUser(@RequestBody UserModel newUser) {
        return userService.createUser(newUser);
    }

    /**
     * Ruft alle Benutzer ab.
     * @return Eine Liste aller Benutzermodelle.
     */
    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Ruft einen Benutzer anhand seiner ID ab.
     * @param userId Die ID des Benutzers.
     * @return Das Benutzermodell des gesuchten Benutzers.
     */
    @GetMapping("/{userId}")
    public UserModel findByUserId(@PathVariable Long userId) {
        //custom exception
        return userService.findByUserId(userId);
    }

    /**
     * Aktualisiert die Daten eines Benutzers.
     * @param userId Die ID des zu aktualisierenden Benutzers.
     * @param updatedUser Die neuen Benutzerdaten.
     * @return Das aktualisierte Benutzermodell.
     */
    @PutMapping("/{userId}")
    public UserModel updateUser(@PathVariable Long userId, @RequestBody UserUpdate updatedUser) {
        return userService.updateUser(userId, updatedUser);

    }

    /**
     * Löscht einen Benutzer anhand seiner ID.
     * @param userId Die ID des zu löschenden Benutzers.
     */
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

}

