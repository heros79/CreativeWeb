package am.creative.example.service;

import am.creative.example.entity.CommentEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author David Karchikyan
 * Класс события при успешной работе записи комментария в БД
 * */

public class CommentSavedEvent extends ApplicationEvent {

    @Getter
    private final CommentEntity commentEntity;

    public CommentSavedEvent(Object source, CommentEntity commentEntity) {
        super(source);
        this.commentEntity = commentEntity;
    }
}