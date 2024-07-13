package com.enzinior.sogo.comment.contoller;

import com.enzinior.sogo.comment.dto.CommentDto;
import com.enzinior.sogo.comment.entity.Comment;
import com.enzinior.sogo.comment.mapper.CommentMapper;
import com.enzinior.sogo.comment.service.CommentService;
import com.enzinior.sogo.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
//@Validated
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final NotificationService notificationService;
    private final CommentMapper commentMapper;

    private final String FAIL = "FAIL";
    private final String SUCCESS = "SUCCESS";

    public CommentController(CommentService commentService, CommentMapper commentMapper){
        this.commentMapper = commentMapper;
        this.commentService = commentService;
    }

    // 댓글 전체 조회
    @GetMapping("/{review-uuid}/comments")
    public ResponseEntity<List<CommentDto.Response>> list(@PathVariable("review-uuid") String reviewUuid) {
        List<Comment> entitylist = commentService.searchComment(reviewUuid);

        List<CommentDto.Response> list = entityList.stream()
                .map(commentMapper::commentToCommentResponse)
                .collect(Collectors.toList());

        if (list == null || list.size() == 0)
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<List<CommentDto.Response>>(list, HttpStatus.OK);
    }

    // 댓글 작성
    @PostMapping("/{review-uuid}/comments")
    public ResponseEntity<?> write(@RequestBody CommentDto.Post requestBody) {

        Comment comment = commentMapper.commentPostToComment(requestBody);
        int isComplete = commentService.createComment(comment);
        if (isComplete>0)
            notificationService.createNotification(comment.getUser().getUserId(), 2, 0);
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
    }

    // 댓글 삭제
    @DeleteMapping("/{review-uuid}/comments/{comment-uuid}")
    public ResponseEntity<String> delete(@PathVariable("comment-uuid") String commentUuid) {
        int isComplete = commentService.removeComment(commentUuid);
        if (isComplete>0)
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
    }

    // 댓글 숨김
    @PatchMapping("/{reiew-uuid}/comments/{comment-uuid}/blind")
    public ResponseEntity hide(@PathVariable("comment-uuid") String commentUuid){
        return commentService.hideComment(commentUuid);
    }

    // 댓글 상세 조회
    @GetMapping("/{reiew-uuid}/comments/{comment-uuid}")
    public ResponseEntity detail(@PathVariable("comment-uuid") String commentUuid){
        return commentService.readComment(commentUuid);
    }


    // 댓글 신고
//    @PostMapping("/{review-uuid}/comments/{comment-uuid}")
//    public ResponseEntity<String> write(@PathVariable("comment-uuid") String commentUUID) {
//        int isComplete = commentService.alterComment(commentUUID);
//        // 관리자한테 알림 전송
//        int sends = reportService.sendreport(commentUUID);
//        if(sends>0){
//            return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
//        }
//        return new ResponseEntity<String>(FAIL, HttpStatus.NOT_FOUND);
//    }

}
