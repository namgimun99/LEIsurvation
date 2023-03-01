package com.lec.spring.repository;

import com.lec.spring.domain.RComment;

import java.util.List;

public interface RCommentRepository {

    // 특정 글(review_id) 의 댓글 목록
    List<RComment> findByReview(Long review_id);

    // 댓글 작성 <-- RComment
    int save(RComment comment);

    // 특정 댓글 (id) 삭제
    int deleteById(Long id);
}
