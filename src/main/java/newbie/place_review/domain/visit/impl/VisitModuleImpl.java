package newbie.place_review.domain.visit.impl;

import lombok.RequiredArgsConstructor;
import newbie.place_review.domain.comment.Comments;
import newbie.place_review.domain.place.Place;
import newbie.place_review.domain.visit.Visit;
import newbie.place_review.domain.visit.VisitModule;
import newbie.place_review.domain.visit.VisitRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitModuleImpl implements VisitModule {

    private final VisitRepository visitRepository;

    @Override
    public Visit save(Place place, Long visitCount, LocalDateTime date) {

        Visit visit = Visit.builder()
                           .date(date)
                           .visitCount(visitCount)
                           .place(place)
                           .build();

        return visitRepository.save(visit);
    }

    @Override
    public Optional<Visit> getById(Long visitId) {
        return visitRepository.findById(visitId);
    }

    @Override
    public void deleteById(Long visitId) {
        Optional<Visit> optionalVisit = visitRepository.findById(visitId);

        if (optionalVisit.isEmpty()) throw new DataRetrievalFailureException("방문이 없음");

        visitRepository.deleteById(visitId);
    }

    @Override
    public Visit update(Long visitId, Place place, Long visitCount, LocalDateTime date) {
        Optional<Visit> optionalVisit = visitRepository.findById(visitId);

        if (optionalVisit.isEmpty()) throw new DataRetrievalFailureException("방문이 없음");

        Visit visit = optionalVisit.get();

        visit.setVisitCount(visitCount);
        visit.setDate(date);
        visit.setPlace(place);

        return visitRepository.save(visit);
    }
}
