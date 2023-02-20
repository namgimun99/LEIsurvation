package com.lec.spring.controller;

import com.lec.spring.domain.User;
import com.lec.spring.domain.UserValidator;
import com.lec.spring.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserController(){
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/login")
    public void login(){}

    @PostMapping("/loginError")
    public String loginError(){
        return "/user/login";
    }

    @RequestMapping("/rejectAuth")
    public String rejectAuth(){
        return "common/rejectAuth";
    }

    @GetMapping("/join")
    public void join(){}

    @PostMapping("/join")
    public String joinOk(@Valid User user
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttributes
    ){
        if(!result.hasFieldErrors("username") && userService.isExist(user.getUsername())){
            result.rejectValue("username", "이미 존재하는 아이디입니다.");
        }

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("username", user.getUsername());
            redirectAttributes.addFlashAttribute("password", user.getPassword());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError error : errList){
                redirectAttributes.addFlashAttribute("error", error.getCode());
                break;
            }

            return "redirect:/user/join";
        }
        // valid 통과되면 수행
        String page = "/user/joinOk";
        int cnt = userService.join(user);
        model.addAttribute("result", cnt);
        return page;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new UserValidator());
    }

}
