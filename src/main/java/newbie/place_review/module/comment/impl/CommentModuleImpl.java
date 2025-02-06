package newbie.place_review.module.comment.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import newbie.place_review.module.comment.CommentModule;
import newbie.place_review.module.comment.CommentRepository;
import newbie.place_review.module.comment.Comments;
import newbie.place_review.module.review.Review;
import org.springframework.dao.DataRetrievalFailureException;
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
    public Comments saveByNonmember(@NonNull Review review, @NonNull String content, @NonNull String password) {
        Comments comment = Comments.builder()
                                   .review(review)
                                   .content(content)
                                   .password(password)
                                   .build();

        return commentRepository.save(comment);
    }


    @Override
    public Optional<Comments> getById(@NonNull Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public void deleteByNonmember(@NonNull Long commentId, @NonNull String password) {
        getById(commentId).ifPresentOrElse(
                comments -> {
                    if (comments.getPassword().equals(password))
                        commentRepository.delete(comments);
                    else
                        throw new SecurityException("댓글의 비밀번호가 일치하지 않습니다.");
                },
                () -> {
                    throw new DataRetrievalFailureException("삭제할 댓글을 찾을 수 없습니다.");
                }
        );
    }
}
