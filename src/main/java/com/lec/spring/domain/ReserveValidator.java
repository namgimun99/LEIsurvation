package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReserveValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println("supports("+clazz.getName()+")");

        boolean result = ReserveWrite.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReserveWrite reserveWrite = (ReserveWrite)target;

        System.out.println("validate() 호출: "+reserveWrite);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","이름은 필수입니다.");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"phone","핸드폰 번호는 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"date","날짜입력은 필수입니다.");


        int phone=reserveWrite.getPhone();

        if(phone==0){
            errors.rejectValue("phone","핸드폰 번호는 필수입니다.");
        }


//        String kind=write.getCp_kind();
//        if(!(kind.trim().equals("할인권")||kind.trim().equals("정기권"))){
//            errors.rejectValue("cp_kind","쿠폰 종류에는 '정기권' 이나 '할인권' 둘 중 하나를 입력해주세요");
//        }
//
//
//
//        String sno=write.getCp_sno();
//        if(sno.length()>15){
//            errors.rejectValue("cp_sno","글자 수는 쿠폰 형식대로 해주세요");
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
