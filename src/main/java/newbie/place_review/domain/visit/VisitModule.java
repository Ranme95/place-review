package newbie.place_review.domain.visit;

import newbie.place_review.domain.place.Place;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VisitModule {

    public Visit save(Place place, Long visitCount, LocalDateTime date);

    public Optional<Visit> getById(Long visitId);

    public void deleteById(Long visitId);

    public Visit update(Long visitId, Place place, Long visitCount, LocalDateTime date);
}
