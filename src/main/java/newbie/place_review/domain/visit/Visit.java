package newbie.place_review.domain.visit;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.domain.place.Place;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(name = "visit_count", nullable = false)
    private Long visitCount;

    @Column(nullable = false)
    private LocalDateTime date;
}
