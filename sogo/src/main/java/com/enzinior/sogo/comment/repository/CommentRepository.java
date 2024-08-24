package com.enzinior.sogo.comment.repository;

import com.enzinior.sogo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

        Optional<Comment> findByCommentUuid(String commentUuid);

        @Query("SELECT c FROM Comment c JOIN FETCH c.review r WHERE r.reviewUuid = :reviewUuid ")
        Optional<List<Comment>> commentByReviewUuid(String reviewUuid);

        @Query("SELECT c FROM Comment c JOIN FETCH c.review r WHERE r.reviewUuid = :reviewUuid AND c.parent IS NULL ORDER BY c.createdAt DESC")
        List<Comment> findWithoutParentByReviewUuid(String reviewUuid);

        @Query("SELECT c FROM Comment c JOIN FETCH c.review r WHERE r.reviewUuid = :reviewUuid AND c.parent IS NOT NULL ORDER BY c.createdAt")
        List<Comment> findParentByReviewUuid(String reviewUuid);

}
