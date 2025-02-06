package newbie.place_review.domain.visit.impl;

import lombok.NonNull;
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
    public Visit save(@NonNull Place place,@NonNull Long visitCount,@NonNull LocalDateTime date) {
        Visit visit = Visit.builder()
                           .date(date)
                           .visitCount(visitCount)
                           .place(place)
                           .build();

        return visitRepository.save(visit);
    }

    @Override
    public Optional<Visit> getById(@NonNull Long visitId) {
        return visitRepository.findById(visitId);
    }

    @Override
    public Visit update(@NonNull Long visitId, @NonNull Long visitCount){
       return getById(visitId).map((visit)->{
            visit.setVisitCount(visitCount);
            return visit;
        }).orElseThrow(()->new DataRetrievalFailureException("찾는 방문이 없습니다."));
    }
}
