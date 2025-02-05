package newbie.place_review.domain.review;

import newbie.place_review.domain.place.Place;

import java.util.Optional;

public interface ReviewModule {

    public Review saveByNonmember(Place place, String content, Integer rate, String password);

    public Optional<Review> getById(Long reviewId);

    public void deleteByNonmember(Long reviewId, String password);

    public Optional<Review> updateByNonmember(Long reviewId, String currentPassword, String content, Integer rate, String newPassword);
}
