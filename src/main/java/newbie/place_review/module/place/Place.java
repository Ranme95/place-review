package newbie.place_review.module.place;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.common.BaseTime;
import newbie.place_review.module.review.Review;
import newbie.place_review.module.visit.Visit;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Place extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Embedded
    @Column(nullable = false)
    private Coordinates coordinates;

    @OneToMany(
            mappedBy = "place",
            cascade = CascadeType.ALL
    )
    private final List<Visit> visits = new ArrayList<>();

    @Builder
    public Place(@NonNull String address, @NonNull String placeName, @NonNull Coordinates coordinates) {
        this.address = address;
        this.placeName = placeName;
        this.coordinates = coordinates;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public void setPlaceName(@NonNull String placeName) {
        this.placeName = placeName;
    }

    public void setCoordinates(@NonNull Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
