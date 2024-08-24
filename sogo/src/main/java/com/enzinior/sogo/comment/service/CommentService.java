package com.enzinior.sogo.comment.service;

import com.enzinior.sogo.comment.entity.Comment;

import java.util.List;


public interface CommentService{

    List<List<Comment>> selectAllComment(String reviewUuid);

    Comment createComment(Comment comment);
    int removeComment(String commentUuid);
    void hideComment(String commentUuid);
    Comment readComment(String commentUuid);

}
