package com.lec.spring.controller;


import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.CompanyWrite;
import com.lec.spring.domain.CompanyWriteValidator;
import com.lec.spring.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/write")
    public void write() {
    }

    @PostMapping("/write")
    public String writeOK(@Valid CompanyWrite companyWrite,
                          BindingResult result,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        Long userId = ((PrincipalDetails)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUser().getId();
        companyWrite.setUser_id(String.valueOf(userId));

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("name", companyWrite.getName());
            redirectAttributes.addFlashAttribute("address", companyWrite.getAddress());
            redirectAttributes.addFlashAttribute("companyname", companyWrite.getCompanyname());

            List<FieldError> errList = result.getFieldErrors();

            for(FieldError err : errList){
                redirectAttributes.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/company/write";
        }
        model.addAttribute("result", companyService.companyWrite(companyWrite));
        model.addAttribute("dto", companyWrite);
        return "company/writeOK";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new CompanyWriteValidator());
    }

    @GetMapping("/mypage")
    public void detail(long id, Model model){
        model.addAttribute("list", companyService.companyDetail(id));
    }
}