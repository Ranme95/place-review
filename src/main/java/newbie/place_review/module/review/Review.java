package newbie.place_review.module.review;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.common.BaseTime;
import newbie.place_review.module.comment.Comments;
import newbie.place_review.module.member.Member;
import newbie.place_review.module.place.Place;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true, updatable = false)
    @Comment("비회원일 경우 null입니다.")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false, updatable = false)
    private Place place;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer rate;

    @Column(nullable = true)
    @Comment("회원이 작성한 경우 null입니다.")
    private String password;

    @OneToMany(
            mappedBy = "review",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private final List<Comments> comments = new ArrayList<>();

    @OneToMany(
            mappedBy = "review",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private final List<ReviewImage> reviewImages = new ArrayList<>();

    @Builder
    public Review(Member member, Place place, String content, Integer rate, String password) {
        this.member = member;
        this.place = place;
        this.content = content;
        this.rate = rate;
        this.password = password;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public void setRate(@NonNull Integer rate) {
        this.rate = rate;
    }

    // 비밀번호를 재설정할 땐 Null 값을 넣을 수 없음.
    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
