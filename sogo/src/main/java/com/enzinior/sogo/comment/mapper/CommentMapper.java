package com.enzinior.sogo.comment.mapper;

import java.util.List;

import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.review.entity.Review;
import com.enzinior.sogo.user.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    default Comment commentPostToComment(CommentDto.Post requestBody) {
        Comment comment = new Comment();

        Review review = new Review(requestBody.getReviewUuid());
        User user = User.builder().userUuid(requestBody.getUserUuid()).build();

        comment.setCommentUuid(requestBody.getParentUuid());
        comment.setReview(review);
        comment.setUser(user);
        comment.setContent(requestBody.getContent());
        return comment;
    }

    @Mapping(source = "user.nickname", target = "userNickname")
    @Mapping(source = "user.img", target = "userImg")
    @Mapping(source = "commentUuid", target = "parentUuid")
    @Mapping(source = "review.reviewUuid", target = "reviewUuid")
    CommentDto.Response commentToCommentResponse(Comment comment);

    List<CommentDto.Response> commentsToCommentsResponses(List<Comment> comments);
    List<List<CommentDto.Response>> commentListToCommentsResponseList(List<List<Comment>> comments);
}
