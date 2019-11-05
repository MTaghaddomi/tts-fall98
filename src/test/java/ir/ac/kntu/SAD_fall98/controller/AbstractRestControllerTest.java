package ir.ac.kntu.SAD_fall98.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.ac.kntu.SAD_fall98.SadFall98Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(SadFall98Application.class)
public abstract class AbstractRestControllerTest {

//    @Autowired
//    private WebApplicationContext applicationContext;

    @Autowired
    protected MockMvc mvc;

    protected void setup() {
//        this.mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
//        if(mvc ==    null ){
//            System.out.println(":null");
//        }
    }

    protected String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);

    }

}

