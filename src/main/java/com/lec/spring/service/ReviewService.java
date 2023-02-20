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

    // TODO 특정 id의 reserve 조회
//    public List<String> reserve(long id){
//        List <String> reserve = new ArrayList<>();
//        Review r = reviewRepository.findById(id);
//        String reservation_id = r.getReservation_id();
//        String rs = reviewRepository.reserve(reservation_id);
//        if(rs != null){
//            reserve.add(rs);
//        }
//        return reserve;
//    }


    // TODO getLeisure_id() 필요 reservedto 필요
//    // 특정 id의 leisure 조회
//    public List<String> leisure(long id){
//        List <String> leisure = new ArrayList<>();
//        Review r = reviewRepository.findById(id);
//        String reservation_id = r.getReservation_id();
//        String rs = reviewRepository.reserve(reservation_id);
//        if(rs != null){
//            String leisure_id = rs.getLeisure_id();
//            if(leisure_id != null) {
//                String l = reviewRepository.leisure(leisure_id);
//                leisure.add(l);
//            }
//        }
//        return leisure;
//    }
//
//    // leisure 목록
//    public List<String> leisureList() {
//        List<String> leisure = new ArrayList<>();
//        List<String> reserve = new ArrayList<>();
//
//        // 모든 리뷰
//        List<Review> reviews = reviewRepository.findAll();
//        // 리뷰가 있는 예약 목록
//        if(reviews != null) {
//            for (Review i : reviews) {
//                String r = reviewRepository.reserve(i.getReservation_id());
//                reserve.add(r);
//            }
//            if(reserve != null) {
//                // 예약된 레저 목록
//                for (String j : reserve) {
//                  String l = reviewRepository.leisure(j.getLeisure_id());
//                  leisure.add(l);
//                }
//            }
//        }
//        return leisure;
//    }
}
