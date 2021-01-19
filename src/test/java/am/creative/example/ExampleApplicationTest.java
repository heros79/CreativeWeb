package am.creative.example;

import am.creative.example.entity.CommentEntity;
import am.creative.example.entity.NotificationEntity;
import am.creative.example.service.AppService;
import am.creative.example.service.AppServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExampleApplication.class})
@ActiveProfiles("test")
class ExampleApplicationTest {

    @Autowired
    private AppService appService;

    @Test
    void saveCommentTest() {

        int rightCommentCount = 0;
        int falseCommentCount = 0;
        long a = System.currentTimeMillis();
        System.out.println();
        CommentEntity entity = new CommentEntity();
        entity.setComment("OK");
        entity.setTime(new Date());

        for (int i = 0; i < 1000; i++) {
            entity.setId((long)i + 1);
            appService.save(entity);
            CommentEntity commentEntity = appService.getComment((long)1);
            if (commentEntity == null) {
                falseCommentCount++;
                NotificationEntity notificationEntity = appService.getNotificationByCommentId((long)i + 1);
                if (notificationEntity != null) {
                    throw new RuntimeException();
                }
            } else {
                rightCommentCount ++;
            }
        }

        try {
            Thread.sleep(4000);
            System.out.println("Время работы в секундах для 1000 комметариев составляет " + (((System.currentTimeMillis() - a) / 1000) - 4));

            System.out.println("Процент удачливых комментарий составляет " + ((double)rightCommentCount / 10) + " процента");
            System.out.println("Процент неудачливых комментарий составляет " + ((double)falseCommentCount / 10) + " процента");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
