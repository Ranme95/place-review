package newbie.place_review.module.review.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import newbie.place_review.module.place.Place;
import newbie.place_review.module.review.Review;
import newbie.place_review.module.review.ReviewModule;
import newbie.place_review.module.review.ReviewRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewModuleImpl implements ReviewModule {

    private final ReviewRepository reviewRepository;

    @Override
    public Review saveByNonmember(
            @NonNull Place place,
            @NonNull String content,
            @NonNull Integer rate,
            @NonNull String password
    ) {

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


    /**
     * @param reviewId        리뷰 아이디
     * @param currentPassword 기존 비밀번호
     * @param content         새로운 내용
     * @param rate            새로운 평점
     * @param newPassword     새로운 비밀번호
     * @throws SecurityException 리뷰 비밀번호가 일치하지 않을 때
     * @throws DataRetrievalFailureException 수정 할 리뷰를 찾지 못 했을 때
     */
    @Override
    public Review updateByNonmember(
            @NonNull Long reviewId,
            @NonNull String currentPassword,
            @NonNull String content,
            @NonNull Integer rate,
            @NonNull String newPassword) throws SecurityException, DataRetrievalFailureException {

        return getById(reviewId)
                .map(review -> {

                    if (review.getPassword().equals(currentPassword)) {
                        review.setContent(content);
                        review.setRate(limitRate(rate));
                        review.setPassword(newPassword);
                    } else
                        throw new SecurityException("리뷰 비밀번호가 일치하지 않습니다.");

                    return review;
                })
                .orElseThrow(() -> new DataRetrievalFailureException("수정 할 리뷰를 찾을 수 없습니다."));
    }

    /**
     * @param reviewId 리뷰 아이디
     * @param password 리뷰 작성 시 사용된 비밀번호
     * @throws SecurityException             리뷰 비밀번호가 일치하지 않을 때
     * @throws DataRetrievalFailureException 삭제 할 리뷰를 찾지 못 했을 때
     */
    @Override
    public void deleteByNonmember(
            @NonNull Long reviewId,
            @NonNull String password) throws SecurityException, DataRetrievalFailureException {
        getById(reviewId).ifPresentOrElse(review -> {
            if (review.getPassword().equals(password))
                reviewRepository.delete(review);
            else
                throw new SecurityException("리뷰 비밀번호가 일치하지 않습니다.");
        }, () -> {
            throw new DataRetrievalFailureException("삭제 할 리뷰를 찾을 수 없습니다.");
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
