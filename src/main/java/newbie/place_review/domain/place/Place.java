package newbie.place_review.domain.place;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.common.BaseTime;
import org.hibernate.annotations.Comment;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Table(name = "place")
@Data
@Entity
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
}
