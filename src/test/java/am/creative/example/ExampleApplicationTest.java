package am.creative.example;

import am.creative.example.entity.CommentEntity;
import am.creative.example.entity.NotificationEntity;
import am.creative.example.service.AppService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExampleApplication.class})
@ActiveProfiles("test")
@EnableAsync
class ExampleApplicationTest {

    @Autowired
    private AppService appService;

    @Test
    void saveCommentTest() {

        long a = System.currentTimeMillis();
        CommentEntity entity = new CommentEntity();
        entity.setComment("OK");
        entity.setTime(new Date());

        for (int i = 1; i < 1001; i++) {
            entity.setId((long)i);
            appService.save(entity);
        }

        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }

        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }

        List<CommentEntity> list = appService.getAllComments();



        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }

        System.out.println("Время работы в секундах для 1000 комметариев составляет " + (((System.currentTimeMillis() - a) / 1000) - 4));

        System.out.println("Процент удачливых комментарий составляет " + ((double)list.size() / 10) + " процента");
        System.out.println("Процент неудачливых комментарий составляет " + ((double)(1000 - list.size()) / 10) + " процента");
    }
}
