package ir.ac.kntu.controller;

import ir.ac.kntu.model.User;
import ir.ac.kntu.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
            throws Exception {
        //given
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setEmail("newUser@c.d");
        newUser.setPassword("zxcvbnm");
        newUser.setFirstName("newUser_first");
        newUser.setLastName("newUser_last");

        //when?
        given(userService.findByUsername(newUser.getUsername()))
                .willReturn(Optional.of(newUser));

        //then
        mvc.perform(get("")//TODO
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
