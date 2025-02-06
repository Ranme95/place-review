package newbie.place_review.module.place;

import java.util.Optional;

public interface PlaceModule {

    public Place save(String address, String placeName, Double latitude, Double longitude);

    public Optional<Place> getById(Long placeId);

    public void deleteById(Long placeId);

    public Place update(Long placeId, String address, String placeName, Double latitude, Double longitude);
}
