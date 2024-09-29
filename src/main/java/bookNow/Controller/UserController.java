package bookNow.Controller;

import bookNow.Model.UserModel;
import bookNow.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping
    public UserModel createUser(@RequestBody UserModel newUser) {
        return userService.createUser(newUser);
    }

    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserModel findByUserId(@PathVariable Long userId) {
        //custom exception
        return userService.findByUserId(userId);
    }

    @PutMapping("/{userId}")
    public UserModel updateUser(@PathVariable Long userId, @RequestBody UserModel updatedUser) {
        return userService.updateUser(userId, updatedUser);

    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    // Weitere Endpunkte für Update, Delete usw.
}

