package com.enzinior.sogo.comment.repository;

import com.enzinior.sogo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

        Optional<Comment> findByUuid(String commentUuid);

        @Query("SELECT c FROM Comment c JOIN FETCH c.review r WHERE r.reviewUuid = :reviewUuid")
        Optional<List<Comment>> findAllByReviewUuid(String reviewUuid);

}
