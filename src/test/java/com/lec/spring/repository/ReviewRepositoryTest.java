package com.lec.spring.repository;

import com.lec.spring.domain.Review;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private SqlSession sqlSession;

    @Test
    void findById() {
        ReviewRepository reviewRepository = sqlSession.getMapper(ReviewRepository.class);
        System.out.println(reviewRepository.findById(52L));
        System.out.println("테스트 완료");
    }

    @Test
    void findAll() {
        ReviewRepository reviewRepository = sqlSession.getMapper(ReviewRepository.class);
        System.out.println(reviewRepository.findAll());
        System.out.println("테스트 완료");
    }

    @Test
    void update() {
        ReviewRepository reviewRepository = sqlSession.getMapper(ReviewRepository.class);
        System.out.println(reviewRepository.findById(57L));

        Review review = Review.builder()
                .id(57L)
                .subject("updateTest")
                .content("dd")
                .star(2)
                .build();

        reviewRepository.update(review);
        System.out.println(reviewRepository.findById(57L));
        System.out.println("테스트 완료");
    }

    @Test
    void delete() {
        ReviewRepository reviewRepository = sqlSession.getMapper(ReviewRepository.class);
        Review review = reviewRepository.findById(58L);
        reviewRepository.delete(review);

        System.out.println("테스트 완료");
    }
}