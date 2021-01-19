package am.creative.example.dao;

import am.creative.example.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author David Karchikyan
 * */

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Override
    Page<NotificationEntity> findAll(Pageable pageable);

    NotificationEntity findByCommentId (Long id);
}