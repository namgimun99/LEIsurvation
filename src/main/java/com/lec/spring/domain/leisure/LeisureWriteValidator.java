package com.lec.spring.domain.leisure;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LeisureWriteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {

        boolean result = LeisureWrite.class.isAssignableFrom(clazz);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        LeisureWrite leisureWrite = (LeisureWrite) target;

        String name = leisureWrite.getName();
        if(name == null || name.trim().isEmpty()){
            errors.rejectValue("name","사업자명을 입력해주세요");
        }

        Long price = leisureWrite.getPrice();
        if(price == null){
            errors.rejectValue("price","가격을 입력해주세요");
        }

        String address = leisureWrite.getAddress();
        if(address == null || address.trim().isEmpty()){
            errors.rejectValue("address","주소를 입력해주세요");
        }

        String content = leisureWrite.getContent();
        if(content == null || content.trim().isEmpty()){
            errors.rejectValue("content","레져 소개를 입력해주세요");
        }




    }
}
