package newbie.place_review.domain.comment.impl;

import newbie.place_review.domain.comment.CommentRepository;
import newbie.place_review.domain.comment.Comments;
import newbie.place_review.domain.review.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentModuleImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentModuleImpl commentModule;

    @Test
    void saveByNonmember() {
        //Given
        Review review = mock(Review.class);
        Comments comments = mock(Comments.class);

        //When
        when(commentRepository.save(any(Comments.class))).thenReturn(comments);

        //Then
        comments.setId(1L);
        assertEquals(comments, commentModule.saveByNonmember(review,"내용",null));

    }

    @Test
    void getById(){
        //Given
        Comments comments = mock(Comments.class);

        //When
        when(commentRepository.findById(1L)).thenReturn(Optional.ofNullable(comments));

        //Then
        assertEquals(comments,commentModule.getById(1L).orElse(null));

    }
}
