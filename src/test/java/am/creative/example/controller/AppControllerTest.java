package am.creative.example.controller;

import am.creative.example.ExampleApplication;
import am.creative.example.entity.CommentEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExampleApplication.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
@EnableAsync
public class AppControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mock;

    @Before
    public void setup() {
        this.mock = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void saveCommentTest() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        for (int i = 1; i < 1001; i++) {

            CommentEntity entity = new CommentEntity();
            entity.setComment("OK");
            entity.setTime(new Date());
            entity.setId((long)i);

            String json = mapper.writeValueAsString(entity);

            mock.perform(post("/comment")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(json))
                    .andExpect(status().isOk());
        }

        Thread.sleep(4000);

        MvcResult result = mock.perform(get("/comments"))
                .andExpect(status().isOk()).andReturn();


        List<CommentEntity> commentEntity = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<CommentEntity>>() {});

        Thread.sleep(4000);
        int commentSise = commentEntity.size();
        System.out.println("Процент удачливых комментарий составляет " + ((double)commentSise / 10) + " процента");
        System.out.println("Процент неудачливых комментарий составляет " + ((double)(1000 - commentSise) / 10) + " процента");
    }
}