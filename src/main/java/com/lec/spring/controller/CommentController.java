package com.lec.spring.controller;

import com.lec.spring.domain.Qna_comment;
import com.lec.spring.domain.QryCommentList;
import com.lec.spring.domain.QryResult;
import com.lec.spring.domain.User;
import com.lec.spring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    public CommentController(){
        System.out.println(getClass().getName()+"생성");
    }

    @GetMapping("/list")
    public QryCommentList list(Long id){
        return commentService.list(id);
    }

    @PostMapping("/write")
    public QryResult write(
           @RequestParam("qna_id") Long qnaId,
           @RequestParam("user_id") Long userId,
            String content) {
        return commentService.write(qnaId,userId,content);
    }

    @PostMapping("/delete")
    public QryResult delete(Long id){
        return commentService.delete(id);
    }

}
