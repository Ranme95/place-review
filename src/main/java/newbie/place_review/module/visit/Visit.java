package newbie.place_review.module.visit;

import jakarta.persistence.*;
import lombok.*;
import newbie.place_review.module.place.Place;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id", nullable = false, updatable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false, updatable = false)
    private Place place;


    @Column(nullable = false, unique = true, updatable = false)
    private LocalDate date;


    @Column(name = "visit_count", nullable = false)
    private Long visitCount;

    @Builder
    public Visit(Place place, Long visitCount, LocalDate date) {
        this.place = place;
        this.visitCount = visitCount;
        this.date = date;
    }

    public void setVisitCount(@NonNull Long visitCount) {
        this.visitCount = visitCount;
    }
}
