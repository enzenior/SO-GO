package com.enzinior.sogo.comment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.comment.repository.CommentRepository;
import com.enzinior.sogo.exception.BusinessLogicException;
import com.enzinior.sogo.exception.ExceptionCode;
import com.enzinior.sogo.report.entity.Report;
import com.enzinior.sogo.report.service.ReportService;
import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.review.service.ReviewService;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ReviewService reviewService;
    private final ReportService reportService;

   @Override
   @Transactional(readOnly = true)
   public List<List<Comment>> selectAllComment(String reviewUuid){

       Review review = reviewService.getReview(reviewUuid);

       List<Comment> Comments = verifiedByReviewUuid(reviewUuid);
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
        User user = userService.findUser(comment.getUser().getUserUuid());
        Review review = reviewService.getReview(comment.getReview().getReviewUuid());
        comment.setUser(user);
        comment.setReview(review);
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

    @Override
    public Report reportComment(String commentUuid, String userUuid, String content) {
        User user = userService.findUser(userUuid);
        Comment comment = verifiedByUuid(commentUuid);
        Report report = new Report();
        report.setUser(user);
        report.setTargetId(comment.getCommentId());
        report.setReportType(1);
        report.setContent(content);

        return reportService.postReport(report);
    }

    // 댓글 달았을 때 notification 호출

    private Comment verifiedByUuid(String commentUuid){
        Optional<Comment> comment = commentRepository.findByCommentUuid(commentUuid);
        return comment
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    private List<Comment> verifiedByReviewUuid(String reviewUuid){
        List<Comment> Comments = commentRepository.commentByReviewUuid(reviewUuid)
            .orElse(null);
        return Comments;
    }




}
