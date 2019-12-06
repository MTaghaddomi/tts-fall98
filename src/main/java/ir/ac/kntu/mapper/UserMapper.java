package ir.ac.kntu.mapper;

import ir.ac.kntu.domain.user.UserProfileDTO;
import ir.ac.kntu.domain.user.UserSignInRequestDTO;
import ir.ac.kntu.domain.user.UserSignInResponseDTO;
import ir.ac.kntu.domain.user.UserSignUpResponseDTO;
import ir.ac.kntu.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserSignInRequestDTO convertSignUpRequestDto(User user);

    User convertSignUpRequestDto(UserSignInRequestDTO userDto);

    UserSignUpResponseDTO convertUserSignUpResponse(User user);

    User convertUserSignUpResponse(UserSignUpResponseDTO userDto);

    UserSignInResponseDTO convertUserSignInResponseDto(User user);

    User convertUserSignInResponseDto(UserSignInResponseDTO userDto);

    UserSignInRequestDTO convertUserSignInRequestDTO(User user);

    User convertUserSignInRequestDTO(UserSignInRequestDTO userDto);

    UserProfileDTO convertUserProfile(User user);

    User convertUserProfile(UserProfileDTO userDto);


}
