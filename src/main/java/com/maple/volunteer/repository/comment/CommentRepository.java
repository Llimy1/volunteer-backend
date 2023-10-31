package com.maple.volunteer.repository.comment;

import com.maple.volunteer.domain.comment.Comment;
import com.maple.volunteer.dto.comment.CommentResponseDto;
import com.maple.volunteer.dto.poster.PosterResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 존재 여부 확인
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END "
            + "FROM Comment cm "
            + "LEFT JOIN cm.poster p "
            + "WHERE p.id = :posterId")
    Optional<Boolean> existsByPosterId(@Param("posterId") Long posterId);

    // 댓글 조회
    @Query("SELECT NEW com.maple.volunteer.dto.comment.CommentResponseDto(" +
            "cm.id AS commentId, "+
            "cm.content AS commentContent,"+
            "cm.author AS commentAuthor) "+
            "FROM Comment cm "+
            "LEFT JOIN cm.poster p " +
            "WHERE p.id = :posterId ")
    Page<CommentResponseDto> findAllCommentList(@Param("posterId") Long posterId, PageRequest pageable);

    // 댓글 수정
    @Query("UPDATE Comment cm "
            + " SET cm.content = :content "
            + " WHERE cm.id = :commentId")
    @Modifying(clearAutomatically = true)
    void updateCommentContent(@Param("commentId") Long commentId, @Param("content") String content);

    // 게시글이 삭제 되었을 때 게시글에 해당되는 댓글 전체 삭제


    // commentId에 해당되는 댓글만 삭제
    @Query("UPDATE Comment cm "
            + " SET cm.isDelete = true "
            + " WHERE cm.id = :commentId")
    @Modifying(clearAutomatically = true)
    void commentDeleteByCommentId(@Param("commentId") Long commentId);

}
