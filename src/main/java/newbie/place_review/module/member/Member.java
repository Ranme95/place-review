package newbie.place_review.module.member;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.module.comment.Comments;
import newbie.place_review.module.review.Review;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(
            mappedBy = "member",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(
            mappedBy = "member",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Comments> comments = new ArrayList<>();

    @Builder
    public Member(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
