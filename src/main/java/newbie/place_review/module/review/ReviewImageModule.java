package newbie.place_review.module.review;

import newbie.place_review.module.place.Place;

import java.util.Optional;

public interface ReviewImageModule {

    public Optional<ReviewImage> findById(Long reviewImageId);

    public ReviewImage save(String name, Place place, Review review);

    public void deleteById(Long reviewImageId);
}
