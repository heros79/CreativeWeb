package am.creative.example.dao;

import am.creative.example.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author David Karchikyan
 * */

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Override
    Page<CommentEntity> findAll(Pageable pageable);
}