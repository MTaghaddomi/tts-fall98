package ir.ac.kntu.controller;

import ir.ac.kntu.domain.*;
import ir.ac.kntu.service.UserService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserRestController {
    @Autowired
    private UserService1 userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignUpResponseDTO signUp(@RequestBody UserSignUpRequestDTO userDTO){
        return userService.signUp(userDTO);
    }

    @PostMapping("/login")
    public UserSignInResponseDTO signIn(@RequestBody UserSignInRequestDTO userDTO){
        return userService.signIn(userDTO);
    }

    @GetMapping("/{username}")
    public UserProfileDTO getProfile(@PathVariable String username){
        return userService.profile(username);
    }

    @PutMapping("/{username}")
    public UserProfileDTO editProfile(@PathVariable String username,
                                      @RequestBody UserEditableProfileDTO profileDTO){
        return userService.editProfile(username, profileDTO);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus deleteAccount(@PathVariable String username){
        return userService.deleteUser(username);
    }
}
