package com.enzinior.sogo.comment.service;

import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
//    private final ReviewRepository reviewRepository;

    @Override
    public List<Comment> searchComment(String reviewUuid){
//        if(reviewRepository.verifiedByUuid(reviewUuid)) // 해당 리뷰가 있는지 확인.
        return commentRepository.findAllByReviewUuid(reviewUuid);
    }

    @Transactional
    @Override
    public int createComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public int removeComment(String commentUuid){
        return commentRepository.delete(verifiedByUuid(commentUuid));
    }

//    @Transactional
//    @Override
//    public int alterComment(String commentUuid){
//        return commentsRepository.updateComment(comment);
//    }

//    @Transactional
//    @Override
//    public void hideComment(String commentUuid){
//        return commentsRepository.update(verifiedByUuid(commentUuid)); // 숨기는 로직 아직 고민중..
//    }

    @Override
    public Comment readComment(String commentUuid){
        return verifiedByUuid(commentUuid);
    }

    private Comment verifiedByUuid(String commentUuid){
        Optional<Comment> comment = commentsRepository.findByUuid(commentUuid);
        return comment
                .orElseThrow(() -> new RuntimeException("No Comment found with uuid " + commentUuid));
    }

}
