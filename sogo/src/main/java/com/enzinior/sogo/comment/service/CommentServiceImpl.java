package com.enzinior.sogo.comment.service;

import com.enzinior.sogo.audit.Auditable;
import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.comment.repository.CommentRepository;
import com.enzinior.sogo.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
       List<Comment> Comments = commentRepository.commentByReviewUuid(reviewUuid)
           .orElseThrow();
       List<List<Comment>> commentlist = new ArrayList<>();
       HashMap<String, Integer> parentMap = new HashMap<>();
       int idx = 0;
       for(Comment comment : Comments) {
           if(comment.getParent()==null){
               parentMap.put(comment.getCommentUuid(), idx);
               idx++;
               commentlist.add(new ArrayList<>());
               commentlist.get(idx-1).add(comment);
           }else{
               commentlist.get(parentMap.get(comment.getParent())).add(comment);
           }

       }

       Collections.reverse(commentlist); // 부모댓글은 최신 댓글이 위쪽으로, 자식 댓글은 아래쪽으로.

       return commentlist;
   }

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


}
