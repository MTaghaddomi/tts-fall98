package ir.ac.kntu.controller;

import ir.ac.kntu.domain.classroom.ClassroomGeneralInfoDTO;
import ir.ac.kntu.domain.user.*;
import ir.ac.kntu.model.Classroom;
import ir.ac.kntu.service.UserService;
import ir.ac.kntu.util.UserTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenUtil tokenUtil;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserSignUpResponseDTO signUp(@RequestBody UserSignUpRequestDTO userDTO){
        return userService.signUp(userDTO);
    }

    @PostMapping("/login")
    public UserSignInResponseDTO signIn(@RequestBody UserSignInRequestDTO userDTO) {
        return userService.signIn(userDTO);
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Access-Control-Allow-Origin", "http://localhost:8080");
//        return ResponseEntity.ok()
//                .headers(responseHeaders)
//                .body(userDTO);
    }

    @GetMapping("/{username}")
    public UserProfileDTO getProfile(@PathVariable String username){
        return userService.profile(username);
    }

    @PutMapping("/{username}")
    public UserProfileDTO editProfile(@PathVariable String username,
                                      @RequestBody UserEditableProfileDTO profileDTO){
        System.out.println("----> Req body put: " + profileDTO);
        return userService.editProfile(username, profileDTO);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus deleteAccount(@PathVariable String username){
        return userService.deleteUser(username);
    }

    @GetMapping("/myClasses")
    public List<ClassroomGeneralInfoDTO> getMyClasses(){
        String requesterUsername = tokenUtil.token2Username();
        List<Classroom> myClasses = userService.getUserClasses(requesterUsername);
        List<ClassroomGeneralInfoDTO> myClassesDTO = new ArrayList<>();

        //TODO: use mapper instead
        if(myClasses == null){
            return myClassesDTO;
        }
        for(Classroom classroom : myClasses){
            String name = classroom.getName();
            String lesson = classroom.getLesson().getName();
            String teacher = classroom.getTeacher().getLastName();
            myClassesDTO.add(new ClassroomGeneralInfoDTO(name, lesson, teacher));
        }
        //

        return myClassesDTO;
    }

    //TODO: ask front
    @PostMapping("/shouldChange")
    public void uploadProfilePicture(){}

    @GetMapping("/shouldChange")
    public void downloadProfilePicture(){}
}
