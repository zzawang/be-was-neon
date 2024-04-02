package repository;

import java.util.Collection;
import model.Comment;

public interface CommentRepository {
    Comment addComment(Comment comment);

    Collection<Comment> findAllCommentsByAid(long aid);

    Collection<Comment> findAllComments();
}
