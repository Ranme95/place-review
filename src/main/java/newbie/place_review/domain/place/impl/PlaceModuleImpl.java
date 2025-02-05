package newbie.place_review.domain.place.impl;

import lombok.RequiredArgsConstructor;
import newbie.place_review.domain.place.Coordinates;
import newbie.place_review.domain.place.Place;
import newbie.place_review.domain.place.PlaceModule;
import newbie.place_review.domain.place.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceModuleImpl implements PlaceModule {

    private final PlaceRepository placeRepository;

    @Override
    public Place save(String address, String placeName, Double latitude, Double longitude) {

        Coordinates coordinates = Coordinates.builder()
                                             .longitude(longitude)
                                             .latitude(latitude)
                                             .build();

        Place place = Place.builder()
                           .coordinates(coordinates)
                           .placeName(placeName)
                           .address(address)
                           .build();

        return placeRepository.save(place);
    }


    @Override
    public Optional<Place> getById(Long placeId) {
        return placeRepository.findById(placeId);
    }

    @Override
    public void deleteById(Long placeId) {
    }

    @Override
    public Optional<Place> update(Long placeId, String address, String placeName, Double latitude, Double longitude) {

        return placeRepository.findById(placeId).map(place -> {
            place.setAddress(address);
            place.setPlaceName(placeName);

            Coordinates coordinates = place.getCoordinates();

            coordinates.setLatitude(latitude);
            coordinates.setLongitude(longitude);

            return place;
        });
    }
}
