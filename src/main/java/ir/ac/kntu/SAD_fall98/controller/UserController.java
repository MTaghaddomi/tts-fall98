package ir.ac.kntu.SAD_fall98.controller;

import ir.ac.kntu.SAD_fall98.domain.UserLoginDto;
import ir.ac.kntu.SAD_fall98.model.User;
import ir.ac.kntu.SAD_fall98.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserLoginDto saveUser(@RequestBody @Valid UserLoginDto userLoginDto) {
        User user = convertDtoToEntity(userLoginDto);
        System.out.println("user = " + user);
        userService.save(user);
        return userLoginDto;
    }

    @GetMapping("/users")
    public List<UserLoginDto> findAll() {
        List<User> users = userService.findAll();
        List<UserLoginDto> usersDto = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return usersDto;
    }


    @GetMapping("/users/{userId}")
    public UserLoginDto getUser(@PathVariable @Min(0) long userId) {
        User user = userService.findById(userId);
        UserLoginDto userLoginDto = convertToDto(user);
        return userLoginDto;
    }

    private UserLoginDto convertToDto(User user) {
        UserLoginDto userLoginDto = UserLoginDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .id(user.getId())
                .email(user.getEmail())
                .build();
        return userLoginDto;
    }

    private User convertDtoToEntity(UserLoginDto userLoginDto) {
        User user = User.builder()
                .username(userLoginDto.getUsername())
                .password(userLoginDto.getPassword())
                .id(userLoginDto.getId())
                .email(userLoginDto.getEmail())
                .build();

        return user;
    }

}
