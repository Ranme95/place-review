package newbie.place_review.domain.review;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.common.BaseTime;
import newbie.place_review.domain.place.Place;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewImage extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;
}
