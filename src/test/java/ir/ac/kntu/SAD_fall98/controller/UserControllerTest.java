package ir.ac.kntu.SAD_fall98.controller;

import ir.ac.kntu.SAD_fall98.model.User;
import ir.ac.kntu.SAD_fall98.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

class UserControllerTest extends AbstractRestControllerTest {
    @MockBean
    private UserService userService;

    @Override
    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void getUsersList() throws Exception {
        String uri = "/api/users";
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(uri);


        List<User> all = userService.findAll();


//        MvcResult mvcResult = mvc.perform(
//                mockHttpServletRequestBuilder.accept(MediaType.APPLICATION_JSON_VALUE))
//                .andReturn();
//
//        int status = mvcResult.getResponse().getStatus();
//        assertEquals(200, status);
//
//        String contentAsString = mvcResult.getResponse().getContentAsString();
//        UserLoginDto[] userDtos = super.mapFromJson(contentAsString, UserLoginDto[].class);
//        assertTrue(userDtos.length > 0);
    }
}