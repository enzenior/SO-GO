package com.enzinior.sogo.comment.service;

import com.enzinior.sogo.audit.Auditable;
import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.comment.repository.CommentRepository;
import com.enzinior.sogo.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ReviewService reviewService;

   @Override
   @Transactional(readOnly = true)
   public List<List<Comment>> selectAllComment(String reviewUuid){
       reviewService.getReview(reviewUuid);
       List<Comment> commentList = commentRepository.findWithoutParentByReviewUuid(reviewUuid);
       List<Comment> childrenList = commentRepository.findParentByReviewUuid(reviewUuid);
       childrenList.sort(Comparator.comparing(Auditable::getCreatedAt));
       List<List<Comment>> allCommentList = new ArrayList<>();
       for (int i = 0; i < commentList.size(); i++) {
           Comment comment = commentList.get(i);
           allCommentList.add(List.of(comment));
           List<Comment> list = childrenList.stream()
               .filter(c -> c.getParent().equals(comment.getCommentUuid()))
               .toList();
           for (Comment c : list) {
               allCommentList.get(i).add(c);
           }
       }

       return allCommentList;
   }



//        if(reviewRepository.verifiedByUuid(reviewUuid)) // 해당 리뷰가 있는지 확인. 추가 예정
//        return
//    }

    @Override
    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    @Override
    public int removeComment(String commentUuid){
       if(verifiedByUuid(commentUuid)!=null){ // 삭제시 차피 값이 없으면 오류코드 출력할테니 따로 처리 안해도 되는가
            commentRepository.delete(verifiedByUuid(commentUuid));
           return 1;
       }else{
           return 0;
       }
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

    private Comment verifiedByUuid(String commentUuid){
        Optional<Comment> comment = commentRepository.findByCommentUuid(commentUuid);
        return comment
                .orElseThrow(() -> new RuntimeException("No Comment found with uuid " + commentUuid));
    }

    //    @Transactional
//    @Override
//    public int alterComment(String commentUuid){
//        return commentsRepository.updateComment(comment);
//    }

}
