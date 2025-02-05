package newbie.place_review.domain.place;

import java.util.Optional;

public interface PlaceModule {

    public Place save(String address, String placeName, Double latitude, Double longitude);

    public Optional<Place> getById(Long placeId);

    public void deleteById(Long placeId);

    public Optional<Place> update(Long placeId, String address, String placeName, Double latitude, Double longitude);
}
