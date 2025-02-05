package newbie.place_review.domain.review;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.common.BaseTime;
import newbie.place_review.domain.member.Member;
import newbie.place_review.domain.place.Place;
import org.hibernate.annotations.Comment;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = true)
    @Comment("비회원일 경우 null입니다.")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer rate;

    @Column(nullable = true)
    @Comment("회원이 작성한 경우 null입니다.")
    private String password;

    @Builder
    public Review(Member member, Place place, String content, Integer rate, String password) {
        this.member = member;
        this.place = place;
        this.content = content;
        this.rate = rate;
        this.password = password;
    }
}
