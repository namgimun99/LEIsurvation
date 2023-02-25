package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReserveValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {

        boolean result = ReserveWrite.class.isAssignableFrom(clazz);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReserveWrite reserveWrite = (ReserveWrite)target;

        System.out.println("validate() 호출: "+reserveWrite);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","이름은 필수입니다.");

        Long date=reserveWrite.getDate();
        if(date==null||date.longValue()==8){
            errors.rejectValue("date","날짜는 8글자로 형식에 맞게 적어주세요");
        }
        Long phone=reserveWrite.getPhone();
        if(phone==null||phone.longValue()==11){
            errors.rejectValue("phone","전화번호는 11글자로 형식에 맞게 적어주세요");
        }
//        if(date.longValue()>8){
//            errors.rejectValue("date","8글자 이내로 형식에 맞게 적어주세요");
//        }
//        if(phone.longValue()>11){
//            errors.rejectValue("phone","11글자 이내로 적어주세요");
//        }

//
//
//        Pattern pattern = Pattern.compile("\\w{4}"+"-\\w{2}"+"-\\w{2}");
//        Matcher matcher = pattern.matcher(sno);
//        if(!(matcher.find())){
//            errors.rejectValue("cp_sno","####-##-## 형식으로 숫자와 문자(영어)만 입력해주세요");
//        }

    }
}
