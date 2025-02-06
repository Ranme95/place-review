package newbie.place_review.module.comment;

import newbie.place_review.module.review.Review;
import java.util.Optional;


public interface CommentModule {

    public Comments saveByNonmember(Review review, String content, String password);

    public Optional<Comments> getById(Long commentId);

    public void deleteByNonmember(Long commentId,String password);

}
