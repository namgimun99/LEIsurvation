
package com.lec.spring.controller;


import com.lec.spring.domain.*;
import com.lec.spring.service.LeisureService;
import com.lec.spring.util.U;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leisure")
public class LeisureController {

    @Autowired
    private LeisureService leisureService;

    @GetMapping("/write")
    public void write(Long company_id, Model model){
        User user = U.getLoggedUser();
        model.addAttribute("company_id", company_id);
    }

    @PostMapping("/write")
    public String writeOK(
            @RequestParam Map<String, MultipartFile> files,
            @Valid LeisureWrite leisureWrite,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) throws IOException {


        int num1 = leisureWrite.getCompany_id();
        if(result.hasErrors()){

            redirectAttributes.addFlashAttribute("name", leisureWrite.getName());
            redirectAttributes.addFlashAttribute("price", leisureWrite.getPrice());
            redirectAttributes.addFlashAttribute("address", leisureWrite.getAddress());
            redirectAttributes.addFlashAttribute("content", leisureWrite.getContent());


            List<FieldError> errList = result.getFieldErrors();

            for(FieldError err : errList){
                redirectAttributes.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/leisure/write?company_id="+num1;
        }
        model.addAttribute("result", leisureService.leisureWrite(leisureWrite,files));
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

        List<LeisureWrite> leisureWriteList = leisureService.liststar();

        List<LeisureWrite> leisureWriteList1 = new ArrayList<>();
        System.out.println("---------------------------------------");
        for(LeisureWrite leisureWrite: leisureWriteList ) {
            Long leisure_id = leisureWrite.getId();
            LeisureWrite leisureWrite1 = leisureService.selectById2(leisure_id);
            if( leisureWrite1.getFiles().size() > 0) {
                System.out.println(leisureWrite1.getFiles().get(0).getFile());
                leisureWrite1.setImageName( leisureWrite1.getFiles().get(0).getFile() );
            }
            leisureWriteList1.add(leisureWrite1);

        }

//        model.addAttribute("listprice", leisureWriteList);
        model.addAttribute("liststar", leisureWriteList1);
    }

    @GetMapping("/listprice")
    public void listprice(Model model){

//        model.addAttribute("leisure_file", "logo.jpeg");
        List<LeisureWrite> leisureWriteList = leisureService.listprice();

        List<LeisureWrite> leisureWriteList1 = new ArrayList<>();
        System.out.println("---------------------------------------");
        for(LeisureWrite leisureWrite: leisureWriteList ) {
            Long leisure_id = leisureWrite.getId();
            LeisureWrite leisureWrite1 = leisureService.selectById2(leisure_id);
            if( leisureWrite1.getFiles().size() > 0) {
                System.out.println(leisureWrite1.getFiles().get(0).getFile());
                leisureWrite1.setImageName( leisureWrite1.getFiles().get(0).getFile() );
            }
            leisureWriteList1.add(leisureWrite1);

        }

//        model.addAttribute("listprice", leisureWriteList);
        model.addAttribute("listprice", leisureWriteList1);
    }


    @GetMapping("/update")
    public void update(long id, Model model) {
        model.addAttribute("list", leisureService.leisureDetail(id));
    }

    @PostMapping("/update")
    public String updateOk(@RequestParam Map<String, MultipartFile> files,
                           Long[] delfile,
                           @Valid LeisureWrite leisurewrite,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttrs) {
        // validation
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("name", leisurewrite.getName());
            redirectAttrs.addFlashAttribute("price", leisurewrite.getPrice());
            redirectAttrs.addFlashAttribute("content", leisurewrite.getContent());
            redirectAttrs.addFlashAttribute("address", leisurewrite.getAddress());

            List<FieldError> errList = result.getFieldErrors();
            for (FieldError err : errList) {
                redirectAttrs.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/leisure/update?id=" + leisurewrite.getId();
        }

        model.addAttribute("result", leisureService.update(leisurewrite,files, delfile));
        model.addAttribute("dto", leisurewrite);

        return "leisure/updateOk";
    }
    @PostMapping("/delete")
    public String deleteOk(long id, Model model) {
        model.addAttribute("result", leisureService.deleteById(id));
        return "leisure/deleteOk";
    }

}

