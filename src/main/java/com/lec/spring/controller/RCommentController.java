package com.lec.spring.controller;

import com.lec.spring.domain.RQryCommentList;
import com.lec.spring.domain.RQryResult;
import com.lec.spring.service.RCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rcomment")
public class RCommentController {

    @Autowired
    private RCommentService commentService;
    public RCommentController(){
        System.out.println(getClass().getName()+"생성");
    }

    @GetMapping("/list")
    public RQryCommentList list(Long id){
        return commentService.list(id);
    }

    @PostMapping("/write")
    public RQryResult write(
            @RequestParam("review_id") Long review_id,
            @RequestParam("user_id") Long user_id,
            String content) {
        return commentService.write(review_id,user_id,content);
    }

    @PostMapping("/delete")
    public RQryResult delete(Long id){
        return commentService.delete(id);
    }

}
