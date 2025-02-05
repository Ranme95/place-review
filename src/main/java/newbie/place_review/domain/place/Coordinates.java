package newbie.place_review.domain.place;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Embeddable
@Data
@Builder
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
