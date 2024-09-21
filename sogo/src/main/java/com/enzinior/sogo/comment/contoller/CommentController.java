package com.enzinior.sogo.comment.contoller;

import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.place.dto.PlaceDto;
import com.enzinior.sogo.utils.UriCreator;
import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.comment.mapper.CommentMapper;
import com.enzinior.sogo.comment.service.CommentService;
import com.enzinior.sogo.notification.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/{review-uuid}/comments")
public class CommentController {

    private final CommentService commentService;
    private final NotificationService notificationService;
    private final CommentMapper commentMapper;

    // 댓글 전체 조회
    @GetMapping
    @Operation(summary = "댓글 전체 조회")
    public ResponseEntity<?> list(@PathVariable("review-uuid") String reviewUuid) {
        List<List<Comment>> Comments = commentService.selectAllComment(reviewUuid);
        List<List<CommentDto.Response>> commentDtos = commentMapper.commentListToCommentsResponseList(Comments);
        return ResponseEntity.ok(commentDtos);
    }

    // 댓글 작성
    @PostMapping
    @Operation(summary = "댓글 작성")
    public ResponseEntity writeComment (@Valid @RequestBody CommentDto.Post requestBody) {

        Comment comment = commentMapper.commentPostToComment(requestBody);
        Comment createComment = commentService.createComment(comment);

        URI location = UriCreator.createUri("/comments", createComment.getCommentId());
        return ResponseEntity.created(location).build();

    }

    // 댓글 삭제
    @DeleteMapping("/{comment-uuid}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<String> delete(@PathVariable("comment-uuid") String commentUuid) {
        int isComplete = commentService.removeComment(commentUuid);
        if (isComplete>0)
            return ResponseEntity.ok().build();
        return ResponseEntity.noContent().build();
    }

    // 댓글 숨김
    @PatchMapping("/{comment-uuid}")
    @Operation(summary = "댓글 숨김")
    public ResponseEntity hide(@PathVariable("comment-uuid") String commentUuid){
        commentService.hideComment(commentUuid);
        return ResponseEntity.ok().build();
    }

    // 댓글 상세 조회
    @GetMapping("/{comment-uuid}")
    @Operation(summary = "댓글 상세 조회 for admin")
    public ResponseEntity detail(@PathVariable("comment-uuid") String commentUuid){
        Comment comment = commentService.readComment(commentUuid);
        CommentDto.Response commenResponse = commentMapper.commentToCommentResponse(comment);
        return ResponseEntity.ok(commenResponse);
    }
    // 댓글 신고 /{place-uuid}
    @PostMapping("/{comment-uuid}")
    @Operation(summary = "댓글 신고하기")
    public ResponseEntity reportComment(@PathVariable("comment-uuid") String commentUuid, @Valid @RequestBody CommentDto.reportPost requestBody){
        return ResponseEntity.ok(commentService.reportComment(commentUuid, requestBody.getUserUuid(), requestBody.getContent()));
    }

}
