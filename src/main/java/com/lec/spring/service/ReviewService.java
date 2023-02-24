package com.lec.spring.service;

import com.lec.spring.domain.Review;
import com.lec.spring.repository.ReviewRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(SqlSession sqlSession){
        reviewRepository = sqlSession.getMapper(ReviewRepository.class);
        System.out.println("ReviewService() 생성");
    }

    // 새글 작성
    public int review(Review review){
        return reviewRepository.save(review);
    }

    // 특정 id의 글 조회
    public List<Review> detail(long id) {
        List<Review> reviewList = new ArrayList<>();
        Review r = reviewRepository.findById(id);
        if(r != null){
            reviewList.add(r);
        }
        return reviewList;
    }

    // 모든 글 조회
    public List<Review> list(){
        return reviewRepository.findAll();
    }

    // 특정 글 수정
    public int update(Review review){
        return reviewRepository.update(review);
    }

    // 특정 글 삭제
    public int deleteById(long id){
        int result = 0;

        Review review = reviewRepository.findById(id);
        if(review != null){
            result = reviewRepository.delete(review);
        }

        return result;
    }

    // leisure, reserve 정보 조회
    public List<Review> RLlist(Long reservation_id) {
        return reviewRepository.findByRSId(reservation_id);
    }


}
