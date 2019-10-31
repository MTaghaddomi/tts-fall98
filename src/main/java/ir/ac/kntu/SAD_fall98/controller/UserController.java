package ir.ac.kntu.SAD_fall98.controller;

import ir.ac.kntu.SAD_fall98.domain.UserDto;
import ir.ac.kntu.SAD_fall98.model.User;
import ir.ac.kntu.SAD_fall98.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/users")
    public UserDto saveUser(@RequestBody @Valid UserDto userDto) {
        User user = convertDtoToEntity(userDto);
        userService.save(user);
        return userDto;
    }

    @GetMapping("/users")
    public List<UserDto> findAll() {
        List<User> users = userService.findAll();
        List<UserDto> usersDto = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return usersDto;
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @GetMapping("/users/{userId}")
    public UserDto getUser(@PathVariable @Min(0) long userId) {
        User user = userService.findById(userId);
        UserDto userDto = convertToDto(user);
        return userDto;
    }


    private User convertDtoToEntity(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .build();
        return user;
    }

}
