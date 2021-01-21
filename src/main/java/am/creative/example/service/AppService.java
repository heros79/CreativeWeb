package am.creative.example.service;

import am.creative.example.entity.CommentEntity;
import am.creative.example.entity.NotificationEntity;

import java.util.List;

public interface AppService {

    void save(CommentEntity commentEntity);
    List<CommentEntity> getComments(Integer pageCount, Integer pageSize);
    CommentEntity getComment(Long id);
    void commentSaved(CommentSavedEvent event);
    List<NotificationEntity> getNotifications(Integer pageCount, Integer pageSize);
    NotificationEntity getNotification(Long id);
    NotificationEntity getNotificationByCommentId(Long commentId);
    List<CommentEntity> getAllComments();
}