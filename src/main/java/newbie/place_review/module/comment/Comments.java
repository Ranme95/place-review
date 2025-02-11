package newbie.place_review.module.comment;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.common.BaseTime;
import newbie.place_review.module.member.Member;
import newbie.place_review.module.review.Review;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
public class Comments extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, updatable = false)
    @Comment(value = "비회원일 경우 null")
    private Member member;

    @Column(nullable = false, updatable = false)
    private String content;

    @Column(nullable = true, updatable = false)
    @Comment(value = "비회원일 경우 null")
    private String password;

    @Builder
    public Comments(Review review, Member member, String content, String password) {
        this.review = review;
        this.member = member;
        this.content = content;
        this.password = password;
    }
}
