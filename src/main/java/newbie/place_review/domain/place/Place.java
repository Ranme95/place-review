package newbie.place_review.domain.place;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.common.BaseTime;
import org.hibernate.annotations.Comment;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Place extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(nullable = false)
    @Comment("주소")
    private String address;

    @Column(name = "place_name", nullable = false)
    @Comment("장소명")
    private String placeName;

    @Embedded
    @Column(nullable = false)
    @Comment("좌표")
    private Coordinates coordinates;

    @Builder
    public Place(String address, String placeName, Coordinates coordinates) {
        this.address = address;
        this.placeName = placeName;
        this.coordinates = coordinates;
    }
}
