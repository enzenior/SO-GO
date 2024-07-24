package com.enzinior.sogo.comment.service;

import com.enzinior.sogo.comment.entity.Comment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.enzinior.sogo.comment.repository.

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentsRepository commentsRepository;

    @Override
    public List<Comment> searchComment(String reviewUuid){
        return commentsRepository.selectAllComment(reviewUuid);
    }

    @Transactional
    @Override
    public int createComment(Comment comment){
        return commentsRepository.save(comment);
    }

    @Transactional
    @Override
    public int removeComment(String commentUuid){
        return commentsRepository.delete(getComment(commentUuid));
    }

//    @Transactional
//    @Override
//    public int alterComment(String commentUuid){
//        return commentsRepository.updateComment(comment);
//    }

    @Transactional
    @Override
    public void hideComment(String commentUuid){
        return commentsRepository.update(getComment(commentUuid));
    }

    @Override
    public Comment readComment(String commentUuid){
        return getComment(commentUuid);
    }

    private Comment verifiedByUuid(String commentUuid){
        Optional<Comment> comment = commentsRepository.findByUuid(commentUuid);
        return comment.get();
    }

}
