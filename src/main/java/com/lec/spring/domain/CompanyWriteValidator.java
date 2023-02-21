package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CompanyWriteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {

        boolean result = CompanyWrite.class.isAssignableFrom(clazz);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CompanyWrite companyWrite = (CompanyWrite) target;

        String name = companyWrite.getName();
        if(name == null || name.trim().isEmpty()){
            errors.rejectValue("name","사업자명을 입력해주세요");
        }

        String address = companyWrite.getAddress();
        if(address == null || address.trim().isEmpty()){
            errors.rejectValue("address","회사주소를 입력해주세요");
        }

        String companyname = companyWrite.getCompanyname();
        if(companyname == null || companyname.trim().isEmpty()){
            errors.rejectValue("companyname","회사이름을 입력해주세요");
        }
    }
}
