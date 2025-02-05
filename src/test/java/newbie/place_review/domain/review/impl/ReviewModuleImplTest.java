package newbie.place_review.domain.review.impl;

import newbie.place_review.domain.place.Place;
import newbie.place_review.domain.review.Review;
import newbie.place_review.domain.review.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReviewModuleImpl 테스트")
class ReviewModuleImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewModuleImpl reviewModule;

    @Test
    @DisplayName("리뷰 조회")
    void Have_found_a_review() {
        // Given
        Review review = mock(Review.class);

        // When
        when(reviewRepository.findById(1L)).thenReturn(Optional.ofNullable(review));

        // Then
        assertSame(review, reviewModule.getById(1L).orElse(null));
    }

    @Test
    @DisplayName("비회원이 리뷰 수정")
    void Nonmember_updates_a_review() {
        // Given
        Place place = mock(Place.class);
        Review review = Review.builder()
                              .member(null)
                              .place(place)
                              .rate(5)
                              .content("테스트 내용")
                              .password("기존 비밀번호")
                              .build();

        review.setId(1L);

        
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        // When
        Optional<Review> optReview = reviewModule.updateByNonmember(1L, "기존 비밀번호", "수정된 테스트 내용", 5, "새로운 비밀번호");
        String changedContent = optReview.map(Review::getContent).orElse(null);

        // Then
        assertSame("수정된 테스트 내용", changedContent);
    }

    @Test
    @DisplayName("비회원이 잘못된 비밀번호로 리뷰 수정")
    void Nonmember_updates_a_review_with_an_wrong_password() {
        // Given
        Place place = mock(Place.class);
        Review review = Review.builder()
                              .member(null)
                              .place(place)
                              .rate(5)
                              .content("기존 테스트 내용")
                              .password("기존 비밀번호")
                              .build();

        review.setId(1L);


        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        // When
        Optional<Review> optReview = reviewModule.updateByNonmember(1L, "잘못된 비밀번호", "수정된 테스트 내용", 5, "새로운 비밀번호");
        String changedContent = optReview.map(Review::getContent).orElse(null);

        // Then
        assertEquals("기존 테스트 내용", changedContent);
    }
}