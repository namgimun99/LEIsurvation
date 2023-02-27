package com.lec.spring.repository;

import com.lec.spring.domain.ReviewFileDTO;

import java.util.List;
import java.util.Map;

public interface ReviewFileRepository {

    int insert(List<Map<String, Object>> list, Long review_id);

    int save(ReviewFileDTO file);

    // 특정 글(review_id)의 첨부파일들
    List<ReviewFileDTO> findByReview(Long review_id);

    // 특정 첨부파일(id) 한개 select
    ReviewFileDTO findById(Long id);

    // 선택된 첨부파일들 SELECT
    // 글 수정에서 사용
    List<ReviewFileDTO> findByIds(Long [] ids);

    // 선택된 첨부파일들 DELETE
    // 글 수정에서 사용
    int deleteByIds(Long [] ids);

    // 특정 글의 첨부 파일(들) DB 삭제
    int delete(ReviewFileDTO file);
}
