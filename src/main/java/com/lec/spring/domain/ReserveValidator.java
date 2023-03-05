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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"date","날짜는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"phone","전화번호는 필수입니다.");

        String date=reserveWrite.getDate();
        if(date.length()!=8){
            errors.rejectValue("date","날짜는 8글자로 형식에 맞게 적어주세요");
        }
        String phone=reserveWrite.getPhone();
        if(phone.length()!=11||(!phone.startsWith("0"))){
            errors.rejectValue("phone","전화번호는 11글자로 형식에 맞게 적어주세요");
        }
        Pattern pattern1 = Pattern.compile("\\d{11}");
        Matcher matcher1 = pattern1.matcher(phone);
        if(!(matcher1.find())){
            errors.rejectValue("phone","숫자를 입력해주세요");
        }
        Pattern pattern2 = Pattern.compile("\\d{8}");
        Matcher matcher2 = pattern2.matcher(date);
        if(!(matcher2.find())) {
            errors.rejectValue("date", "숫자로 입력해주세요");
        }

    }
}
