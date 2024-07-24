package com.enzinior.sogo.comment.mapper;

import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "reviewUuid", target = "review.uuid")
    @Mapping(source = "userUuid", target = "user.uuid")
    Comment commentPostToComment(CommentDto.Post requestBody);

    @Mapping(source = "userNickname", target = "review.nickname")
    @Mapping(source = "userImg", target = "user.img")
    CommentDto.Response commentToCommentResponse(Comment comment);

    List<CommentDto.Response> commentsTocomments(List<Comment>);

}
