package com.lec.spring.controller.company;


import com.lec.spring.domain.company.CompanyWrite;
import com.lec.spring.domain.company.CompanyWriteValidator;
import com.lec.spring.service.company.CompanyService;
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

        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("user_id", companyWrite.getUser_id());
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
}