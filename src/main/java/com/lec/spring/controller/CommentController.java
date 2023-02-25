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

@RestController //data 를 response 한다. View 리턴하는게 아님
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    public CommentController(){
        System.out.println(getClass().getName()+"생성");
    }

    @GetMapping("/test1")
    public QryCommentList test1(Long id){

        QryCommentList list = new QryCommentList();

        list.setCount(1);
        list.setStatus("Ok");
        Qna_comment qmt = Qna_comment.builder()
                .user(User.builder().username("user1").id(31L).regdate(LocalDateTime.now()).build())
                .content("어떤가요?")
                .regdate(LocalDateTime.now())
                .build();
        List<Qna_comment> qmtList = new ArrayList<>();
        qmtList.add(qmt);
        list.setList(qmtList);

        return list;
    }

    @GetMapping("/test2")
    public List<Integer> test2(){
        List<Integer> list = Arrays.asList(10,20,30);
        return list;
    }

    @GetMapping("/test3")
    public Map<Integer, String> test3(){
        Map<Integer,String> myMap = new HashMap<>(){{
            put(100,"백이다");
            put(200,"이백이다");
        }};
        return myMap;
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
