package com.lec.spring.service;

import com.lec.spring.domain.*;
import com.lec.spring.repository.RCommentRepository;
import com.lec.spring.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RCommentService {

    private RCommentRepository commentRepository;
    private UserRepository userRepository;

    @Autowired
    public RCommentService(SqlSession sqlSession){
        commentRepository = sqlSession.getMapper(RCommentRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);

        System.out.println("RCommentService() 생성");
    }

    public RQryCommentList list(Long id) {
        RQryCommentList list = new RQryCommentList();

        List<RComment> comments = commentRepository.findByReview(id);
        list.setCount(comments.size());
        list.setList(comments);
        list.setStatus("Ok");

        return list;

    }

    //특정 글에 특정 사용자가 댓글 작성
    public RQryResult write(Long review_id, Long user_id, String content) {
        User user = userRepository.findById(user_id);

        RComment review_comment = RComment.builder()
                .user_id(user)
                .content(content)
                .review_id(review_id)
                .build();

        commentRepository.save(review_comment);

        RQryResult result = RQryResult.builder()
                .count(1)
                .status("Ok")
                .build();

        return result;
    }

    //특정 댓글(id) 삭제
    public RQryResult delete(Long id) {

        int count = commentRepository.deleteById(id);
        String status = "FAIL";

        if(count>0) status="OK";

        RQryResult result = RQryResult.builder()
                .count(count)
                .status(status)
                .build();

        return result;
    }
}
