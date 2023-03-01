package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Review;
import com.lec.spring.domain.ReviewValidator;
import com.lec.spring.domain.User;
import com.lec.spring.service.ReviewService;
import com.lec.spring.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    public ReviewController(){
        System.out.println("ReviewController() 생성");
    }

    @GetMapping("/list")
    public void list(Model model) {
        model.addAttribute("reviewList", reviewService.list());
    }


    @GetMapping("/write")
    public void write(Long reservation_id, Model model) {
        List<Review> reviewList = reviewService.RLlist(reservation_id);
        User user = U.getLoggedUser();
        model.addAttribute("reservation_id", reservation_id);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("user_id", user);
    }

    @PostMapping("/write")
    public String writeOk(
            @RequestParam Map<String, MultipartFile> files
            , @Valid Review review
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttrs
    ) {
        // validation
        if(result.hasErrors()){
            redirectAttrs.addFlashAttribute("subject", review.getSubject());
            redirectAttrs.addFlashAttribute("content", review.getContent());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err: errList){
                redirectAttrs.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/review/write?reservation_id=" + review.getReservation_id();

        }

        model.addAttribute("result", reviewService.review(review, files)); //dml
        model.addAttribute("reviewDto", review); // id값

        return "review/writeOk";
    }

    @GetMapping("/detail")
    public void detail(Long id, Model model) {
        model.addAttribute("reviewList", reviewService.detail(id));
        model.addAttribute("conPath", U.getRequest().getContextPath());

    }

    @GetMapping("/update")
    public void update(Long id, Model model) {
        model.addAttribute("reviewList", reviewService.selectById(id));
    }

    @PostMapping("/update")
    public String updateOk( @RequestParam Map<String, MultipartFile> files // 새로 추가될 첨부파일들
                            ,Long[] delfile // 삭제될 파일들
                            ,@Valid Review review
                            ,BindingResult result
                            ,Model model
                            ,RedirectAttributes redirectAttrs) {
        // validation
        if(result.hasErrors()){
            redirectAttrs.addFlashAttribute("subject", review.getSubject());
            redirectAttrs.addFlashAttribute("content", review.getContent());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err: errList){
                redirectAttrs.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/review/update?id=" + review.getId();
        }

        model.addAttribute("result", reviewService.update(review, files, delfile));
        model.addAttribute("review", review);

        return "review/updateOk";
    }

    @PostMapping("/delete")
    public String deleteOk(Long id, Model model) {
        model.addAttribute("result", reviewService.deleteById(id));
        return "review/deleteOk";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        System.out.println("initBinder() 호출");
        binder.setValidator(new ReviewValidator());
    }


}
