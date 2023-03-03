package com.lec.spring.controller;

import com.lec.spring.domain.Qna;
import com.lec.spring.domain.QnaValidator;
import com.lec.spring.service.QnaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lec.spring.util.U;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/qna")
public class QnaController {
    @Autowired
    private QnaService qnaService;

    public QnaController(){
        System.out.println("QnaController() 생성");
    }


    @GetMapping("/write")
    public void write(){
    }


    @PostMapping("/write")
    public String writeOk(@Valid Qna qna
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes
    ){

        if(result.hasErrors()){
            //redirect 시에 기존에 입력했던 값들은 보이게 하기
            redirectAttributes.addFlashAttribute("subject", qna.getSubject());
            redirectAttributes.addFlashAttribute("content", qna.getContent());

            List<FieldError> errorList=result.getFieldErrors(); //에러의 list 를 리턴***
            for(FieldError err:errorList){
                redirectAttributes.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/qna/write";

        }

        model.addAttribute("result", qnaService.write(qna));

        return "/qna/writeOk";
    }


    @PostMapping("/update")
    public String updateOk(@Valid Qna qna
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes
    ){


        if(result.hasErrors()){
            Long num1=qna.getId();
            redirectAttributes.addFlashAttribute("subject", qna.getSubject());
            redirectAttributes.addFlashAttribute("content", qna.getContent());

            List<FieldError> errorList=result.getFieldErrors();
            for(FieldError err:errorList){
                redirectAttributes.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/qna/update?id="+num1;


        }

        model.addAttribute("result", qnaService.update(qna));
        model.addAttribute("dto", qna);
        return "qna/updateOk";
    }
    @GetMapping("/detail")
    public void detail(long id, Model model){
        model.addAttribute("list",qnaService.detail(id));
        model.addAttribute("conPath",U.getRequest().getContextPath());  // 댓글때매 씀

    }

    @GetMapping("/update")
    public void update(Long id,Model model){
        model.addAttribute("list", qnaService.selectById(id));
    }


    @PostMapping("/delete")
    public void delete(Long id, Model model){
        model.addAttribute("result", qnaService.deleteById(id));

    }


    @InitBinder
    public void initBinder(WebDataBinder binder){
        System.out.println("initBinder 호출");
        binder.setValidator(new QnaValidator());
    }

    //페이징
    //pageRows 변경시 동작
    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows){
        U.getSession().setAttribute("pageRows", pageRows);
        return "redirect:/qna/list?page=" + page;
    }
    //페이징 사용
    @GetMapping("/list")
    public void list(Integer page,Model model){
        qnaService.list(page,model);
    }
}
