package newbie.place_review.module.place.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import newbie.place_review.module.place.Coordinates;
import newbie.place_review.module.place.Place;
import newbie.place_review.module.place.PlaceModule;
import newbie.place_review.module.place.PlaceRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceModuleImpl implements PlaceModule {

    private final PlaceRepository placeRepository;

    @Override
    public Place save(
            @NonNull String address,
            @NonNull String placeName,
            @NonNull Double latitude,
            @NonNull Double longitude
    ) {

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
        placeRepository.findById(placeId).ifPresentOrElse(
                placeRepository::delete,
                () -> {
                    throw new DataRetrievalFailureException("삭제 할 장소를 찾지 못하였습니다.");
                }
        );
    }

    @Override
    public Place update(
            @NonNull Long placeId,
            @NonNull String address,
            @NonNull String placeName,
            @NonNull Double latitude,
            @NonNull Double longitude
    ) {

        return placeRepository.findById(placeId).map(place -> {

            Coordinates coordinates = Coordinates.builder()
                                                 .latitude(latitude)
                                                 .longitude(longitude)
                                                 .build();

            place.setAddress(address);
            place.setPlaceName(placeName);
            place.setCoordinates(coordinates);


            return place;
        }).orElseThrow(() -> new DataRetrievalFailureException("수정 할 장소를 찾지 못하였습니다."));
    }
}
