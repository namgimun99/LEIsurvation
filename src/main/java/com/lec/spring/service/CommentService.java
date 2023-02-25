package com.lec.spring.service;


import com.lec.spring.domain.Qna_comment;
import com.lec.spring.domain.QryCommentList;
import com.lec.spring.domain.QryResult;
import com.lec.spring.domain.User;
import com.lec.spring.repository.CommentRepository;
import com.lec.spring.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentService(SqlSession sqlSession){
        commentRepository = sqlSession.getMapper(CommentRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);

        System.out.println("CommentService() 생성");
    }

    public QryCommentList list(Long id) {
        QryCommentList list = new QryCommentList();

        List<Qna_comment> comments = commentRepository.findByWrite(id);
        list.setCount(comments.size());
        list.setList(comments);
        list.setStatus("Ok");

        return list;

    }


    //특정 글에 특정 사용자가 댓글 작성
    public QryResult write(Long qnaId, Long userId, String content) {
        User user = userRepository.findById(userId);

        Qna_comment qna_comment=Qna_comment.builder()
                .user(user)
                .content(content)
                .qna_id(qnaId)
                .build();
        commentRepository.save(qna_comment);

        QryResult result = QryResult.builder()
                .count(1)
                .status("Ok")
                .build();

        return result;
    }


    //특정 댓글(id) 삭제
    public QryResult delete(Long id) {

        int count = commentRepository.deleteById(id);
        String status = "FAIL";

        if(count>0) status="OK";

        QryResult result = QryResult.builder()
                .count(count)
                .status(status)
                .build();

        return result;
    }
}












