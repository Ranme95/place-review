package newbie.place_review.domain.review.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import newbie.place_review.domain.place.Place;
import newbie.place_review.domain.review.Review;
import newbie.place_review.domain.review.ReviewModule;
import newbie.place_review.domain.review.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewModuleImpl implements ReviewModule {

    private final ReviewRepository reviewRepository;

    @Override
    public Review saveByNonmember(@NonNull Place place, @NonNull String content, @NonNull Integer rate, @NonNull String password) {

        Review review = Review.builder()
                              .place(place)
                              .password(password)
                              .content(content)
                              .rate(limitRate(rate))
                              .build();

        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> getById(@NonNull Long reviewId) {
        return reviewRepository.findById(reviewId);
    }


    @Override
    public Optional<Review> updateByNonmember(@NonNull Long reviewId, @NonNull String currentPassword, @NonNull String content, @NonNull Integer rate, @NonNull String newPassword) {
        return reviewRepository.findById(reviewId)
                               .map(review -> {

                                   if (review.getPassword().equals(currentPassword)) {
                                       review.setContent(content);
                                       review.setRate(limitRate(rate));
                                       review.setPassword(newPassword);
                                   }

                                   return review;
                               });
    }

    @Override
    public void deleteByNonmember(@NonNull Long reviewId, @NonNull String password) {
        getById(reviewId).ifPresent(review -> {
            if (review.getPassword().equals(password))
                reviewRepository.delete(review);
        });
    }

    /**
     * 평점을 받아서 범위를 제한 시킨다.
     *
     * @param rate 평점
     * @return 제한된 범위의 평점
     */
    private Integer limitRate(@NonNull Integer rate) {
        if (rate > 5)
            return 5;
        else if (rate < 1)
            return 1;
        else
            return rate;
    }
}
