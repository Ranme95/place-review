package newbie.place_review.domain.comment.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import newbie.place_review.domain.comment.CommentModule;
import newbie.place_review.domain.comment.CommentRepository;
import newbie.place_review.domain.comment.Comments;
import newbie.place_review.domain.review.Review;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CommentModuleImpl implements CommentModule {

    private final CommentRepository commentRepository;

    @Override
    public Comments saveByNonmember(Review review, String content, String password) {
        Comments comment = Comments.builder()
                                   .review(review)
                                   .content(content)
                                   .password(password)
                                   .build();
        return commentRepository.save(comment);
    }


    @Override
    public Optional<Comments> getById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public void deleteByNonmember(Long commentId, String password) {
        Optional<Comments> optionalComments = commentRepository.findById(commentId);

        if (optionalComments.isEmpty()) throw new DataRetrievalFailureException("댓글을 찾을 수 없음");

        Comments comment = optionalComments.get();

        //비회원일 경우 비밀번호가 일치하면
        if (comment.getPassword().equals(password)) {
            commentRepository.deleteById(commentId);
        }
        try {
            throw new PermissionDeniedDataAccessException("비밀번호가 일치하지 않음", new Throwable("비밀번호 일치하지 않음"));
        }
        catch (PermissionDeniedDataAccessException e){
            log.error("Error: " + e.getMessage());
        }

    }
}
