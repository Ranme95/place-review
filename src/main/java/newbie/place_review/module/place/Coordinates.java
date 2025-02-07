package newbie.place_review.module.place;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.Comment;

@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {

    @Column(nullable = false)
    @Comment("위도")
    private Double latitude;

    @Column(nullable = false)
    @Comment("경도")
    private Double longitude;
}
