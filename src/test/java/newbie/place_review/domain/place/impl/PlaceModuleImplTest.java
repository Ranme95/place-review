package newbie.place_review.domain.place.impl;

import newbie.place_review.domain.place.Coordinates;
import newbie.place_review.domain.place.Place;
import newbie.place_review.domain.place.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceModuleImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceModuleImpl placeModule;

    private Place place;

    @BeforeEach
    void prepare() {
        Coordinates coordinates = Coordinates.builder()
                                             .latitude(132.2)
                                             .longitude(152.3)
                                             .build();

        place = Place.builder()
                     .placeName("장소")
                     .address("주소")
                     .coordinates(coordinates)
                     .build();

    }

    @Test
    void save() {
        // When
        when(placeRepository.save(any(Place.class))).thenReturn(place);

        // Then
        place.setId(1L);
        assertEquals(place, placeModule.save("주소", "장소명", 121.3, 182.2));
    }

    @Test
    void getById() {
        // When
        when(placeRepository.findById(1L)).thenReturn(Optional.ofNullable(place));

        // Then
        place.setId(1L);
        assertEquals(place, placeModule.getById(1L).orElse(null));
    }

    @Test
    void update() {
        // When
        when(placeRepository.findById(1L)).thenReturn(Optional.of(place));

        // Then
        assertEquals(place, placeModule.update(1L, "주소", "장소명", 121.3, 182.2).orElse(null));
    }
}
