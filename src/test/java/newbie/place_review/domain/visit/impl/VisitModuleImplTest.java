package newbie.place_review.domain.visit.impl;

import newbie.place_review.domain.place.Coordinates;
import newbie.place_review.domain.place.Place;
import newbie.place_review.domain.visit.Visit;
import newbie.place_review.domain.visit.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class VisitModuleImplTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitModuleImpl visitModule;

    @Test
    void save() {
        //Given
        Visit visit = mock(Visit.class);
        Place place = mock(Place.class);

        //When
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        //Then
        assertEquals(visit, visitModule.save(place, 1L, LocalDateTime.now()));

    }

    @Test
    void getById(){
        //Given
        Visit visit = mock(Visit.class);

        //When
        when(visitRepository.findById(1L)).thenReturn(Optional.ofNullable(visit));

        //Then
        assertEquals(visit,visitModule.getById(1L).orElse(null));

    }

    @Test
    void update(){
        //Given
        Visit visit = mock(Visit.class);
        Place place = mock(Place.class);

        //When
        when(visitRepository.findById(1L)).thenReturn(Optional.ofNullable(visit));
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        //Then
        assertEquals(visit,visitModule.update(1L,place,5L,LocalDateTime.now()));
    }
}
