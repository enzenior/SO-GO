package com.enzinior.sogo.comment.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.comment.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(source = "userUuid", target = "user.userUuid")
    @Mapping(source = "reviewUuid", target = "review.reviewUuid")
    @Mapping(source = "parentUuid", target = "parent")
    Comment commentPostToComment(CommentDto.Post requestBody);


    @Mapping(source = "user.nickname", target = "userNickname")
    @Mapping(source = "user.img", target = "userImg")
    @Mapping(source = "parent", target = "parentUuid")
    @Mapping(source = "review.reviewUuid", target = "reviewUuid")
    CommentDto.Response commentToCommentResponse(Comment comment);

    List<List<CommentDto.Response>> commentListToCommentsResponseList(List<List<Comment>> comments);

    List<CommentDto.Response> commentsToCommentsResponses(List<Comment> commentList);
}
