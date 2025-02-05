package newbie.place_review.domain.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import newbie.place_review.domain.member.Member;
import newbie.place_review.domain.review.Review;
import org.hibernate.annotations.Comment;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Review review;

    @ManyToOne
    @JoinColumn(nullable = true)
    @Comment(value = "비회원일 경우 null")
    private Member member;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    @Comment(value = "비회원일 경우 null")
    private String password;
}
