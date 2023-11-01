package com.maple.volunteer.controller.comment;

import com.maple.volunteer.dto.comment.CommentListResponseDto;
import com.maple.volunteer.dto.comment.CommentRequestDto;
import com.maple.volunteer.dto.comment.CommentUpdateDto;
import com.maple.volunteer.dto.common.CommonResponseDto;
import com.maple.volunteer.dto.common.ResultDto;
import com.maple.volunteer.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/maple")
public class CommentController {

    private final CommentService commentService;

    //TODO user-> author 처리

    // 댓글 생성
    @PostMapping("/comment/poster/{posterId}/communityId/{communityId}")
    public ResponseEntity<ResultDto<Void>> commentCreate(@PathVariable Long posterId, @PathVariable Long communityId,
                                                         @RequestBody CommentRequestDto commentRequestDto) {

        CommonResponseDto<Object> commentCreate = commentService.commentCreate(communityId, posterId, commentRequestDto);
        ResultDto<Void> result = ResultDto.in(commentCreate.getStatus(), commentCreate.getMessage());

        return ResponseEntity.status(commentCreate.getHttpStatus())
                             .body(result);
    }

    // 댓글 조회
    @GetMapping("/comment/poster/{posterId}/communityId/{communityId}")
    public ResponseEntity<ResultDto<CommentListResponseDto>> allCommentInquiry(@PathVariable Long communityId,
                                                                               @PathVariable Long posterId,
                                                                               @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                               @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                                               @RequestParam(value = "sortBy", defaultValue = "createdAt", required = false) String sortBy) {
        CommonResponseDto<Object> allCommentInquiry = commentService.allCommentInquiry(communityId, posterId, page, size, sortBy);
        ResultDto<CommentListResponseDto> result = ResultDto.in(allCommentInquiry.getStatus(), allCommentInquiry.getMessage());
        result.setData((CommentListResponseDto) allCommentInquiry.getData());

        return ResponseEntity.status(allCommentInquiry.getHttpStatus())
                             .body(result);
    }

    //댓글 수정
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<ResultDto<Void>> commentUpdate(@PathVariable Long commentId,
                                                         @RequestBody CommentUpdateDto commentUpdateDto) {
        CommonResponseDto<Object> commentUpdate = commentService.commentUpdate(commentId, commentUpdateDto);
        ResultDto<Void> result = ResultDto.in(commentUpdate.getStatus(), commentUpdate.getMessage());

        return ResponseEntity.status(commentUpdate.getHttpStatus())
                             .body(result);
    }

    // commentId에 해당되는 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ResultDto<Void>> commentDeleteByCommentId(@PathVariable Long commentId) {

        CommonResponseDto<Object> commentDelete = commentService.commentDeleteByCommentId(commentId);
        ResultDto<Void> result = ResultDto.in(commentDelete.getStatus(), commentDelete.getMessage());

        return ResponseEntity.status(commentDelete.getHttpStatus())
                             .body(result);
    }


}
