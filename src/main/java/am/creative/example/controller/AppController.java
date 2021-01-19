package am.creative.example.controller;

import am.creative.example.entity.CommentEntity;
import am.creative.example.entity.NotificationEntity;
import am.creative.example.service.AppService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author David Karchikyan
 * */

@RestController
@RequestMapping("/")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> saveComment (@RequestBody CommentEntity commentEntity) {
        appService.save(commentEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/comment", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getComments (@RequestParam (value = "pageCount", defaultValue = "1") Integer pageCount,
                                          @RequestParam (value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<CommentEntity> entityList = appService.getComments(pageCount, pageSize);
        return ResponseEntity.ok(entityList);
    }

    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getCommentById (@PathVariable("id") Long id) {
        CommentEntity entity = appService.getComment(id);
        return ResponseEntity.ok(entity);
    }

    @RequestMapping(value = "/notifications", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getNotifications (@RequestParam (value = "pageCount", defaultValue = "1") Integer pageCount,
                                               @RequestParam (value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<NotificationEntity> entityList = appService.getNotifications(pageCount, pageSize);
        return ResponseEntity.ok(entityList);
    }

    @RequestMapping(value = "/notification/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getNotificationsById (@PathVariable("id") Long id) {
        NotificationEntity entity = appService.getNotification(id);
        return ResponseEntity.ok(entity);
    }

    @RequestMapping(value = "/notification/comment/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getNotificationsByCommentId (@PathVariable("id") Long id) {
        NotificationEntity entity = appService.getNotificationByCommentId(id);
        return ResponseEntity.ok(entity);
    }
}