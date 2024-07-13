package com.enzinior.sogo.comment.service;

import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.comment.mapper.CommentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentService{

    List<Comment> searchComment(String reviewUuid);
    int createComment(Comment comment);
    int removeComment(String commentUuid);
//    int alterComment(String commentUuid);
    void hideComment(String commentUuid);
    Comment readComment(String commentUuid);


}
