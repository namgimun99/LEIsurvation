package com.lec.spring.repository;


import com.lec.spring.domain.Qna_comment;

import java.util.List;

public interface CommentRepository {
    //qna_id 특정 글의 댓글 목록
    List<Qna_comment> findByWrite(Long qna_id);
    //댓글 작성 Qna_comment
    int save(Qna_comment qna_comment);

    // 특정 댓글(id) 삭제
    int deleteById(Long id);
}
