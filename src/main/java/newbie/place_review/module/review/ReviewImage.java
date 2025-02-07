package newbie.place_review.module.review;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.common.BaseTime;
import newbie.place_review.module.place.Place;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewImage extends BaseTime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id", nullable = false, updatable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false, updatable = false)
    private Place place;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false, updatable = false)
    private Review review;


    @Column(nullable = false, updatable = false)
    private String name;


    @Builder
    public ReviewImage(String name, Place place, Review review) {
        this.name = name;
        this.place = place;
        this.review = review;
    }
}
