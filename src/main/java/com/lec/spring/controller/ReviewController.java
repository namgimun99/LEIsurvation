package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.Review;
import com.lec.spring.domain.ReviewValidator;
import com.lec.spring.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        model.addAttribute("reservation_id", reservation_id);
        model.addAttribute("reviewList", reviewList);
    }

    @PostMapping("/write")
    public String writeOk(@Valid Review review,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttrs) {

        Long userId = ((PrincipalDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUser().getId();
        review.setUser_id(String.valueOf(userId));

        // validation
        if(result.hasErrors()){
            redirectAttrs.addFlashAttribute("reservation_id", review.getReservation_id());
            redirectAttrs.addFlashAttribute("subject", review.getSubject());
            redirectAttrs.addFlashAttribute("content", review.getContent());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err: errList){
                redirectAttrs.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/review/write?reservation_id=" + review.getReservation_id();

        }

        model.addAttribute("result", reviewService.review(review)); //dml
        model.addAttribute("reviewDto", review); // id값

        return "review/writeOk";
    }

    @GetMapping("/detail")
    public void detail(long id, Model model) {
        model.addAttribute("reviewList", reviewService.detail(id));

    }

    @GetMapping("/update")
    public void update(long id, Model model) {
        model.addAttribute("reviewList", reviewService.detail(id));
    }

    @PostMapping("/update")
    public String updateOk(@Valid Review review,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttrs) {
        // validation
        if(result.hasErrors()){
            redirectAttrs.addFlashAttribute("reservation_id", review.getReservation_id());
            redirectAttrs.addFlashAttribute("user_id", review.getUser_id());
            redirectAttrs.addFlashAttribute("subject", review.getSubject());
            redirectAttrs.addFlashAttribute("content", review.getContent());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err: errList){
                redirectAttrs.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/review/update?id=" + review.getId();
        }

        model.addAttribute("result", reviewService.update(review));
        model.addAttribute("review", review);

        return "review/updateOk";
    }

    @PostMapping("/delete")
    public String deleteOk(long id, Model model) {
        model.addAttribute("result", reviewService.deleteById(id));
        return "review/deleteOk";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        System.out.println("initBinder() 호출");
        binder.setValidator(new ReviewValidator());
    }
}
