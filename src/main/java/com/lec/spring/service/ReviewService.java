package com.lec.spring.service;

import com.lec.spring.domain.Review;
import com.lec.spring.domain.User;
import com.lec.spring.repository.ReviewFileRepository;
import com.lec.spring.repository.ReviewRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.U;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private ReviewFileRepository fileRepository;

    @Autowired
    public ReviewService(SqlSession sqlSession){
        reviewRepository = sqlSession.getMapper(ReviewRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        fileRepository = sqlSession.getMapper(ReviewFileRepository.class);
        System.out.println("ReviewService() 생성");
    }

    // 새글 작성
    public int review(Review review)
    {
        // 현재 로그인한 사용자
        User user_id = U.getLoggedUser();
        // session에 있는 정보, 일단 DB에서 다시 읽어온다.
        user_id = userRepository.findById(user_id.getId());
        review.setUser_id(user_id); // 글 작성자 세팅

        int cnt = reviewRepository.save(review);

        return cnt;
    }

    // 특정 id의 글 조회
    public List<Review> detail(Long id) {
        List<Review> reviewList = new ArrayList<>();
        Review r = reviewRepository.findById(id);
        if(r != null){
            reviewList.add(r);
        }

        return reviewList;
    }

    // 특정 글(id) 의 첨부파일(들) 추가
    private void addFiles(Map<String, MultipartFile> files,Long id){
        //TODO
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
    public int deleteById(Long id){
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
