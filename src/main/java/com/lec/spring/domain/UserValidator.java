package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println("supports(" + clazz.getName() + ")");
        boolean result = User.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        String username = user.getUsername();
        if(username == null || username.trim().isEmpty()) {
            errors.rejectValue("username", "username 은 필수입니다");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password 는 필수입니다");

        String password = user.getPassword();
        String p_regex = "^(?=.*[a-zA-z])(?=.*[0-9]).{8,}$";
        if(!Pattern.matches(p_regex, password)){
            errors.rejectValue("password", "비밀번호를 최소 8자리 이상, 영문/숫자 조합으로 입력해주세요.");
        }

    }
}
