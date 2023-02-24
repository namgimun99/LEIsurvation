package com.lec.spring.repository;

import com.lec.spring.domain.Review;

import java.util.List;

public interface ReviewRepository {

    // 새글 작성
    int save(Review review);

    // 특정 id글 내용 읽기
    Review findById(long id);

    // reservation_id로 레져이름, 예약날짜, 작성자 불러오기
    List<Review> findByRSId(long reservation_id);

    // 전체 글 목록 : 최신순
    List<Review> findAll();

    // 특정 id 글 삭제하기
    int delete(Review review);

    // 특정 id 글 수정
    int update(Review review);

}
