package com.neu.me.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.neu.me.pojo.useraccount;


public class LoginValidator implements Validator {

    public boolean supports(Class aClass)
    {
        return aClass.equals(useraccount.class);
    }

    public void validate(Object obj, Errors errors)
    {
    	System.out.println("inside validator");
        useraccount userAccount = (useraccount) obj;
        System.out.println(userAccount.getUsername() + userAccount.getPassword());
     
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.invalid.user", "User Name Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
        
    }
}
