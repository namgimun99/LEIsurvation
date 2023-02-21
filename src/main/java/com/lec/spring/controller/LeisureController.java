package com.lec.spring.controller;

import com.lec.spring.domain.LeisureWrite;
import com.lec.spring.domain.LeisureWriteValidator;
import com.lec.spring.service.LeisureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/leisure")
public class LeisureController {

    @Autowired
    private LeisureService leisureService;

    @GetMapping("/write")
    public void write(){}

    @PostMapping("/write")
    public String writeOK(
            @RequestParam Map<String, MultipartFile> files,
            @Valid LeisureWrite leisureWrite,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("company_id", leisureWrite.getCompany_id());
            redirectAttributes.addFlashAttribute("name", leisureWrite.getName());
            redirectAttributes.addFlashAttribute("price", leisureWrite.getPrice());
            redirectAttributes.addFlashAttribute("address", leisureWrite.getAddress());
            redirectAttributes.addFlashAttribute("content", leisureWrite.getContent());


            List<FieldError> errList = result.getFieldErrors();

            for(FieldError err : errList){
                redirectAttributes.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/leisure/write";
        }
        model.addAttribute("result", leisureService.leisureWrite(leisureWrite));
        model.addAttribute("dto", leisureWrite);
        return "leisure/writeOK";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new LeisureWriteValidator());
    }

    @GetMapping("/detail")
    public void detail(long id, Model model){
        model.addAttribute("list", leisureService.leisureDetail(id));
    }

    @GetMapping("/liststar")
    public void liststar(Model model){
        model.addAttribute("liststar", leisureService.liststar());
    }

    @GetMapping("/listprice")
    public void listprice(Model model){
        model.addAttribute("listprice", leisureService.listprice());
    }
}



