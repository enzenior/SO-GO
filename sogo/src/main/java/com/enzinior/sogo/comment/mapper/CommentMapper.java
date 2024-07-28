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
    @Mapping(source = "parentUuid", target = "comment.uuid")
    Comment commentPostToComment(CommentDto.Post requestBody);

    @Mapping(source = "userNickname", target = "review.nickname")
    @Mapping(source = "userImg", target = "user.img")
    @Mapping(source = "parentUuid", target = "comment.uuid")
    @Mapping(source = "reviewUuid", target = "review.uuid")
    CommentDto.Response commentToCommentResponse(Comment comment);

    List<CommentDto.Response> commentsTocommentsResponses(List<Comment>);

}
