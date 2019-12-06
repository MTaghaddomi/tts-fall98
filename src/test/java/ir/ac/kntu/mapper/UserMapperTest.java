package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.user.UserProfileDTO;
import ir.ac.kntu.domain.user.UserSignInRequestDTO;
import ir.ac.kntu.model.User;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void userToSignInRequestDto() {
        User user = User.builder()
                .username("morteza")
                .password("taghaddomi")
                .build();

        UserSignInRequestDTO userSignInRequestDTO = mapper.convertSignUpRequestDto(user);

        assertEquals(user.getUsername(), userSignInRequestDTO.getUsername());
        assertEquals(user.getPassword(), userSignInRequestDTO.getPassword());

        System.out.println("userSignInRequestDTO = " + userSignInRequestDTO);
    }

    @Test
    public void userSignInDtoToUser() {
        UserSignInRequestDTO userSignInRequestDTO = new UserSignInRequestDTO("morteza", "Taghaddomi");
        User user = mapper.convertSignUpRequestDto(userSignInRequestDTO);
        System.out.println("user = " + user);

        assertEquals(userSignInRequestDTO.getUsername(), user.getUsername());
        assertEquals(userSignInRequestDTO.getPassword(), user.getPassword());
    }

    @Test
    public void UserProfileDtoToUser() {
        UserProfileDTO userProfileDto = UserProfileDTO.builder()
                .firstName("morteza")
                .lastName("taghaddomi")
                .username("mortezatm")
                .email("morteza@gmail.com")
                .phoneNumber("09371234567")
                .birthday(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        User user = mapper.convertUserProfile(userProfileDto);
        assertEquals(user.getUsername(), userProfileDto.getUsername());
        assertEquals(user.getFirstName(), userProfileDto.getFirstName());
        assertEquals(user.getLastName(), userProfileDto.getLastName());
        assertEquals(user.getBirthday(), userProfileDto.getBirthday());
        assertEquals(user.getPhoneNumber(), userProfileDto.getPhoneNumber());
        assertEquals(user.getEmail(), userProfileDto.getEmail());
        System.out.println("user = " + user);
    }
}