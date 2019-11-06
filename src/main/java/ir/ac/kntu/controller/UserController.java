package ir.ac.kntu.controller;

import ir.ac.kntu.domain.UserLoginDto;
import ir.ac.kntu.domain.UserRegisterDto;
import ir.ac.kntu.model.User;
import ir.ac.kntu.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public UserRegisterDto registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        User user = convertRegisterDtoToEntity(userRegisterDto);
        System.out.println("user = " + user);
        User savedUser = userService.save(user);
        return convertToRegisterDto(savedUser);

    }

    private UserRegisterDto convertToRegisterDto(User savedUser) {
        UserRegisterDto user = UserRegisterDto.builder()
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .email(savedUser.getEmail())
                .id(savedUser.getId())
                .phoneNumber(savedUser.getPhoneNumber())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .birthday(savedUser.getBirthday().toString())
                .subscribed(savedUser.isSubscribed())
                .build();

        return user;
    }

    private User convertRegisterDtoToEntity(UserRegisterDto userRegisterDto) {
        String birthday = userRegisterDto.getBirthday();
        LocalDate formattedDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        User user = User.builder()
                .username(userRegisterDto.getUsername())
                .password(userRegisterDto.getPassword())
                .email(userRegisterDto.getEmail())
                .id(userRegisterDto.getId())
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .firstName(userRegisterDto.getFirstName())
                .lastName(userRegisterDto.getLastName())
                .birthday(formattedDate)
                .subscribed(userRegisterDto.isSubscribed())
                .build();
        return user;
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
