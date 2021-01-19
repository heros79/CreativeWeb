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
@Table(name = "COMMENTS")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}