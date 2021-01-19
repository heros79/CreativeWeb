package am.creative.example.config;

import am.creative.example.dao.CommentRepository;
import am.creative.example.dao.NotificationRepository;

/**
 * @author David Karchikyan
 * */

public class RepositoriesBeen {

    private final CommentRepository commentRepository;
    private final NotificationRepository notificationRepository;

    public RepositoriesBeen(CommentRepository commentRepository, NotificationRepository notificationRepository) {
        this.commentRepository = commentRepository;
        this.notificationRepository = notificationRepository;
    }
}