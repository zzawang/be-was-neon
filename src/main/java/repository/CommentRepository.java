package repository;

import java.util.Collection;
import model.Comment;

/**
 * 댓글 데이터베이스에 대한 작업을 정의한 인터페이스
 */
public interface CommentRepository {
    /**
     * 새로운 댓글을 추가한다.
     *
     * @param comment 추가할 댓글
     * @return 추가된 댓글
     */
    Comment addComment(Comment comment);

    /**
     * 주어진 게시글 ID에 해당하는 모든 댓글을 반환한다.
     *
     * @param aid 게시글 ID
     * @return 해당 게시글에 대한 모든 댓글의 컬렉션
     */
    Collection<Comment> findAllCommentsByAid(long aid);

    /**
     * 모든 댓글을 반환한다.
     *
     * @return 모든 댓글의 컬렉션
     */
    Collection<Comment> findAllComments();
}
