package com.enzinior.sogo.comment.service;

import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

//    @Override
//    @Transactional(readOnly = true) // 미완성입니당
//    public List<List<Comment>> selectAllComment(String reviewUuid){
//        List<Comment> allCommentList = commentRepository.findParentByReviewUuid(reviewUuid);
//        List<Comment> cocommentList = commentRepository.findWithoutParentByReviewUuid(reviewUuid);
//        List<Comment> parentList = commentRepository.findParentByReviewUuid(reviewUuid);
//        List<List<Comment>> commentList = new ArrayList<>();
////        for(int i = 0; i<parentList.size(); i++){
////            commentList.get(i).add(new ArrayList<>());
////            for(int j = 0; j<cocommentList.size(); j++);
////                if(cocommentList.get(j).getParent().equals(parentList.get(i).getParent()))
////                    commentList.add(new Comment(cocommentList.get(j));
////
////            }
////        return commentList;
//        return commentListList;
//    }



//        if(reviewRepository.verifiedByUuid(reviewUuid)) // 해당 리뷰가 있는지 확인. 추가 예정
//        return
//    }

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
