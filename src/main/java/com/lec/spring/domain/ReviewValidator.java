package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ReviewValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        boolean result = Review.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Review review = (Review)target;

        String subject = review.getSubject();
        if(subject == null || subject.isEmpty()){
            errors.rejectValue("subject", "제목은 필수입니다");
        }

        String content = review.getContent();
        if(content == null || content.isEmpty()){
            errors.rejectValue("content", "내용은 필수입니다");
        }
    }
}
