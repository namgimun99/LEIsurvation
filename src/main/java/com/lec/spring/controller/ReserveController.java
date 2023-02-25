package com.lec.spring.controller;


import com.lec.spring.domain.ReserveValidator;
import com.lec.spring.domain.ReserveWrite;
import com.lec.spring.service.ReserveService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/reserve")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;


    public ReserveController(){
        System.out.println("ReserveController() 생성");
    }
    @GetMapping("/write")
    public void write(Long id, Model model){
        model.addAttribute("list",reserveService.findLeis(id));


    }

    @PostMapping("/write")
    public String writeOk(@Valid ReserveWrite reserveWrite  //나중에 validation 을 하려면 앞에 @Valid 붙여
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes

    ){
        int num1=reserveWrite.getLeisure_id();

        if(result.hasErrors()){
            //redirect 시에 기존에 입력했던 값들은 보이게 하기
            redirectAttributes.addFlashAttribute("name", reserveWrite.getName());
            redirectAttributes.addFlashAttribute("phone", reserveWrite.getPhone());
            redirectAttributes.addFlashAttribute("date", reserveWrite.getDate());

            List<FieldError> errorList=result.getFieldErrors(); //에러의 list 를 리턴***
            for(FieldError err:errorList){
                redirectAttributes.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/reserve/write?id="+num1;

        }
        model.addAttribute("result",reserveService.write(reserveWrite));
        model.addAttribute("dto",reserveWrite);

        return "/reserve/writeOk";
    }


    @GetMapping("/detail")
    public void detail(Long id, Model model){
        model.addAttribute("list",reserveService.detail(id));  //수정 pk 인 id 는 그대로 받고 id 로 함! 3개 join 한 것을 다 빼버림
    }
    @PostMapping("/delete")
    public void delete(Long id, ReserveWrite reserveWrite, Model model){
                //id 는 PK 가져옴 write.html 에서 했던 것처럼 로그인되어있는 user_id 를 가져온다. update(Post)도 동일
        model.addAttribute("result", reserveService.deleteById(id));
        int num1=reserveWrite.getUser_id();
        model.addAttribute("dto", num1);

    }
    @GetMapping("/update")
    public void update(Long id,Model model){
        model.addAttribute("list", reserveService.selectById(id));
    }
    @PostMapping("/update")
    public String updateOk(ReserveWrite reserveWrite, Model model){

        model.addAttribute("result", reserveService.update(reserveWrite));
        int num1=reserveWrite.getUser_id();
        System.out.println(num1);
        model.addAttribute("dto", num1);
        return "reserve/updateOk";
    }

    @GetMapping("/member")
    public void list(Long user_id, Model model){
//        System.out.println(name);
        System.out.println(user_id);
        model.addAttribute("list",reserveService.list(user_id));
    }



    @GetMapping("/company")
    public void company(Long user_id, Model model){
        model.addAttribute("list",reserveService.listCompany(user_id));
        System.out.println(user_id);
        System.out.println(reserveService.listCompany(user_id)+"zzzzzzzzzzzzzzzzzzzzzzzzz");
    }


    @InitBinder
    public void initBinder(WebDataBinder binder){
        System.out.println("initBinder 호출");
        binder.setValidator(new ReserveValidator());
    }
}
