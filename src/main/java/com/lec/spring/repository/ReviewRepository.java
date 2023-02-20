package com.lec.spring.repository;

import com.lec.spring.domain.Review;

import java.util.List;

public interface ReviewRepository {

    // 새글 작성
    int save(Review review);

    // 특정 id글 내용 읽기
    Review findById(long id);

    // 전체 글 목록 : 최신순
    List<Review> findAll();

    // 특정 id 글 삭제하기
    int delete(Review review);

    // 특정 id 글 수정
    int update(Review review);

    // 리뷰가 있는 예약
    String reserve(String reservation_id);

    // 예약된 레져 목록
    String leisure(String leisure_id);

    // TODO : 평점 입력
}
