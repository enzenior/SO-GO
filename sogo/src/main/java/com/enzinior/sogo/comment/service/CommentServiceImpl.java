package com.enzinior.sogo.comment.service;

import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
//    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> searchComment(String reviewUuid){
//        if(reviewRepository.verifiedByUuid(reviewUuid)) // 해당 리뷰가 있는지 확인. 추가 예정
        return commentRepository.findAllByReviewUuid(reviewUuid);
    }

    @Override
    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public int removeComment(String commentUuid){
//        if(verifiedByUuid(commentUuid)!=null){ // 삭제시 차피 값이 없으면 오류코드 출력할테니 따로 처리 안해도 되는가
            commentRepository.delete(verifiedByUuid(commentUuid));
//            return 1;
//        }else{
//            return 0;
//        }
    }

    @Override
    public void hideComment(String commentUuid){
        Comment comment = verifiedByUuid(commentUuid);
        comment.setSecret(!comment.isSecret());
    }

    @Override
    @Transactional(readOnly = true)
    public Comment readComment(String commentUuid){
        return verifiedByUuid(commentUuid);
    }

    @Transactional(readOnly = true)
    private Comment verifiedByUuid(String commentUuid){
        Optional<Comment> comment = commentsRepository.findByUuid(commentUuid);
        return comment
                .orElseThrow(() -> new RuntimeException("No Comment found with uuid " + commentUuid));
    }

    //    @Transactional
//    @Override
//    public int alterComment(String commentUuid){
//        return commentsRepository.updateComment(comment);
//    }

}
