package am.creative.example.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author David Karchikyan
 * */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "NOTIFICATION")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "COMMENT_ID", insertable = false, updatable = false)
    private Long commentId;

    @Column(name = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column(name = "DELIVERED")
    private Boolean delivered;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMMENT_ID", referencedColumnName = "ID")
    private CommentEntity commentEntity;
}