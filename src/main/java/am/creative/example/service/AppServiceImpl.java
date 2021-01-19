package am.creative.example.service;

import am.creative.example.dao.CommentRepository;
import am.creative.example.dao.NotificationRepository;
import am.creative.example.entity.CommentEntity;
import am.creative.example.entity.NotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author David Karchikyan
 * Класс представляет собой реализацию интерфейса {@link AppService}
 * для добавления коментариев и уведомлений
 * */

@Service
public class AppServiceImpl implements AppService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    private void sleepAndRandomThrowRuntimeException(int seconds, int exceptionProbabilityProc) throws RuntimeException {
        try {
            Thread.sleep((long) ((long) seconds * 1000 * Math.random()));
        } catch (InterruptedException ignored) {
        }

        int randomProc = (int) (100 * Math.random());

        if (exceptionProbabilityProc > randomProc) {
            throw new RuntimeException();
        }
    }

    private void doSomeWorkOnNotification() {
        sleepAndRandomThrowRuntimeException(2, 10);
    }

    private void doSomeWorkOnCommentCreation() {
        sleepAndRandomThrowRuntimeException(1, 30);
    }

    /**
     * @param commentEntity
     * Метод сохраняет комментарий а БД,
     * при возникновении проблемы данный коментарий удаляется.
     * Метор таботает асинхронно
     */
    @Async
    @Transactional
    public void save(CommentEntity commentEntity) {

        CommentEntity entity;

        try {
            entity = commentRepository.save(commentEntity);
            try {
                doSomeWorkOnCommentCreation();
                publisher.publishEvent(new CommentSavedEvent(this, entity));
            } catch (Exception e) {
                commentRepository.delete(entity);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * @param pageCount
     * @param pageSize
     * @return {@link List<CommentEntity>}
     * Метод возвращает список комментариев с возможностью выбора количества
     */
    public List<CommentEntity> getComments(Integer pageCount, Integer pageSize) {
        return commentRepository.findAll(PageRequest.of(pageCount, pageSize)).getContent();
    }

    /**
     * @param id
     * @return {@link CommentEntity}
     * Метод возвращает Комментарий
     */
    public CommentEntity getComment(Long id) {
        Optional<CommentEntity> optionalEntity = commentRepository.findById(id);
        return optionalEntity.orElse(null);
    }

    /**
     * @param event
     * Метод олучает уведомление о событии сохранения комментария в БД,
     * после сохраняет уведомление, при неудачной работе логики флаг отпрвки
     * уведомление ставится false
     */
    @Async
    @EventListener
    @Transactional
    public void commentSaved(CommentSavedEvent event) {

        NotificationEntity entity = new NotificationEntity();
        entity.setTime(new Date());
        entity.setCommentId(event.getCommentEntity().getId());
        entity.setCommentEntity(event.getCommentEntity());
        entity.setDelivered(true);

        NotificationEntity savedEntity;

        try {
            savedEntity = notificationRepository.save(entity);
            try {
                doSomeWorkOnNotification();
            } catch (Exception e) {
                entity.setDelivered(false);
                notificationRepository.save(savedEntity);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * @param pageCount
     * @param pageSize
     * @return {@link List<NotificationEntity>}
     * Метод возвращает список Уведомлений
     */
    public List<NotificationEntity> getNotifications(Integer pageCount, Integer pageSize) {
        return notificationRepository.findAll(PageRequest.of(pageCount, pageSize)).getContent();
    }

    /**
     * @param id
     * @return {@link NotificationEntity}
     * Метод возвращает уведомление по id
     */
    public NotificationEntity getNotification(Long id) {
        return notificationRepository.getOne(id);
    }

    /**
     * @param commentId
     * @return {@link NotificationEntity}
     * Метод ыозвращает уведомление по id комментария
     */
    public NotificationEntity getNotificationByCommentId(Long commentId) {
        return notificationRepository.findByCommentId(commentId);
    }
}